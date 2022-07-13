package com.infinitus.bms_oa.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Slf4j
public class HttpUtil {
    private static final int SUCCESS_STATUS = 200;

    public static JSONObject doPostJson(String url, String json, String header, String token) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json, "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json;charset=utf-8");
            httpPost.setHeader(header, token);
            httpPost.setEntity(s);
            HttpResponse res = client.execute(httpPost);
            if (SUCCESS_STATUS == res.getStatusLine().getStatusCode()) {
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
            } else {
                String result = EntityUtils.toString(res.getEntity());
                response = JSONObject.parseObject(result);
                log.error("【doPostJson】返回异常结果,response:{}", response.toString());
                return response;
            }
        } catch (Exception e) {
            log.error("【doPostJson】异常,e:{}", e);
        }
        return response;
    }

    public static String httpGet(String url, String authorization, String date) throws UnsupportedEncodingException {
        // 获取连接客户端工具
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse=null;
        String finalString = null;
        HttpGet httpGet = new HttpGet(url);
        /**公共参数添加至httpGet*/
        if (authorization != null && !"".equals(authorization)) {
            httpGet.setHeader("Authorization", authorization);
        }
        if (date != null && !"".equals(date)) {
            httpGet.setHeader("Date", date);
        }
        try {
            httpResponse = httpClient.execute(httpGet);
            //log.info("httpResponse====================:{}",httpResponse);
            HttpEntity entity = httpResponse.getEntity();
            finalString= EntityUtils.toString(entity, "UTF-8");
            try {
                httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalString;
    }


}
