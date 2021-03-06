package com.infinitus.bms_oa.wms_store.task;

import com.alibaba.fastjson.JSONObject;
import com.infinitus.bms_oa.utils.HttpUtil;
import com.infinitus.bms_oa.wms_store.empty.PagePojo;
import com.infinitus.bms_oa.wms_store.empty.W_STORE_COMMODITIES;
import com.infinitus.bms_oa.wms_store.empty.W_STORE_SKUS;
import com.infinitus.bms_oa.wms_store.mapper.W_STORE_COMMODITIES_Mapper;
import com.infinitus.bms_oa.wms_store.service.W_STORE_SKUS_Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.time.ZoneOffset.UTC;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

@Slf4j
@Component
public class WmsStoreTasks {

    @Value("${wms.secret.value}")
    private String secret;

    @Value("${wms.keyId.value}")
    private String keyId;

    @Value("${wms.url.value}")
    private String url;

    @Value("${wms.listskus.value}")
    private String listskus;

    @Value("${wms.ListCommodities.value}")
    private String listCommodities;

    @Autowired
    private W_STORE_SKUS_Service store_skus_service;

    @Autowired
    private W_STORE_COMMODITIES_Mapper commoditiesMapper;


    private static final DateTimeFormatter RFC_7231_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss O").withLocale(Locale.ENGLISH);

    /**
     * 定时任务 生产一小时执行一次
     * 波哥后面说明是每天全量同步一次
     */
    @Scheduled(fixedRate = 24000 * 60 * 60)
    public void getStoreDetailTask() throws UnsupportedEncodingException {
        getSkus();
        getCommodities();
    }

    /**
     * 批量获取商品中心 skus表
     * */
    public void  getSkus() throws UnsupportedEncodingException {
        //1、商品中心给出的demo中的加密与打印请求头
        String method = "GET";
        //String uri = "/product-api/skus";
        String date = RFC_7231_FORMATTER.format(ZonedDateTime.now(UTC));// 时间
        // 时间 + 请求类型 + 请求uri 的加密
        String signature =encodeBase64String(new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secret).hmac("date: " + date + "\n" + method + " " + listskus + " HTTP/1.1"));
        // HMAC 授权
        String authorization = "hmac username=\"" + keyId + "\", algorithm=\"hmac-sha256\", headers=\"date request-line\", signature=\"" + signature + "\"";
        // 打印请求头
        log.info("Authorization: {}" , authorization);
        log.info("Date: {}", date);
        //2、拼接第一次请求的url并请求接口
        String urlAll = url + listskus+"?nos=&page=0&size=200";
        String result = HttpUtil.httpGet(urlAll, authorization, date);
        JSONObject jsonResult = JSONObject.parseObject(result);
        //log.info("jsonResult=:{}", jsonResult);

        //3、将第一页获取的200条数据封装 并批量插入数据
        List<W_STORE_SKUS> skusList = (List<W_STORE_SKUS>) jsonResult.get("skus");
        //批量插入
        if(skusList!=null){
            store_skus_service.deleteW_STORE_SKUS();//批量更新前先清理掉之前的数据
            store_skus_service.insertSkusList(skusList);
            log.info("skusList插入成功=:{}", skusList);
        }

        //4、获取总的页数，进行分页获取计算，每次获取后批量插入200条
        PagePojo pagePojo = JSONObject.toJavaObject(JSONObject.parseObject(JSONObject.toJSONString(jsonResult.get("page"))),PagePojo.class);
        if (pagePojo == null) {
            return ;
        }
        for (int i = 1; i < pagePojo.getTotalPages(); i++) {
            urlAll = url + listskus + "?nos=&page=" + i + "&size=200";
            String result2 = HttpUtil.httpGet(urlAll, authorization, date);
            JSONObject jsonResult2 = JSONObject.parseObject(result2);
            //log.info("i=:{}", i);//用于检查循环数
            List<W_STORE_SKUS> skusList2 = (List<W_STORE_SKUS>) jsonResult2.get("skus");
            if(skusList2!=null) {
                store_skus_service.insertSkusList(skusList2);
                log.info("skusList" + i + "插入成功=:{}", skusList2);
            }
        }

    }

    /**
     * 批量获取商品中心Commodities的接口数据
     */
    public void getCommodities() throws UnsupportedEncodingException {
//1、商品中心给出的demo中的加密与打印请求头
        String method = "GET";
        //String uri = "/product-api/commodities";
        String date = RFC_7231_FORMATTER.format(ZonedDateTime.now(UTC));// 时间
        // 时间 + 请求类型 + 请求uri 的加密
        String signature =encodeBase64String(new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secret).hmac("date: " + date + "\n" + method + " " + listCommodities + " HTTP/1.1"));
        // HMAC 授权
        String authorization = "hmac username=\"" + keyId + "\", algorithm=\"hmac-sha256\", headers=\"date request-line\", signature=\"" + signature + "\"";
        // 打印请求头
        log.info("Authorization: {}" , authorization);
        log.info("Date: {}", date);
        //2、拼接第一次请求的url并请求接口
        String urlAll = url + listCommodities+"?nos=&page=0&size=200";
        String result = HttpUtil.httpGet(urlAll, authorization, date);
        JSONObject jsonResult = JSONObject.parseObject(result);
        log.info("jsonResult=:{}", jsonResult);

        //3、将第一页获取的200条数据封装 并批量插入数据
        List<W_STORE_COMMODITIES> commoditiesList = (List<W_STORE_COMMODITIES>) jsonResult.get("commodities");
        //批量插入
        if(commoditiesList!=null){
            commoditiesMapper.deleteW_STORE_COMMODITIES();//批量更新前先清理掉之前的数据
            commoditiesMapper.insertW_STORE_COMMODITIESList(commoditiesList);
            log.info("commoditiesList插入成功=:{}", commoditiesList);
        }

        //4、获取总的页数，进行分页获取计算，每次获取后批量插入200条
        PagePojo pagePojo = JSONObject.toJavaObject(JSONObject.parseObject(JSONObject.toJSONString(jsonResult.get("page"))),PagePojo.class);
        if (pagePojo == null) {
            return ;
        }
        for (int i = 1; i < pagePojo.getTotalPages(); i++) {
            urlAll = url + listCommodities + "?nos=&page=" + i + "&size=200";
            String result2 = HttpUtil.httpGet(urlAll, authorization, date);
            JSONObject jsonResult2 = JSONObject.parseObject(result2);
            //log.info("i=:{}", i);//用于检查循环数
            List<W_STORE_COMMODITIES> commoditiesList2 = (List<W_STORE_COMMODITIES>) jsonResult2.get("commodities");
            if(commoditiesList2!=null) {
                commoditiesMapper.insertW_STORE_COMMODITIESList(commoditiesList2);
                log.info("commoditiesList" + i + "插入成功=:{}", commoditiesList2);
            }
        }
    }


}
