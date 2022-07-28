package com.infinitus.bms_oa.bms_free.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infinitus.bms_oa.bms_free.empty.CouponOrderReport;
import com.infinitus.bms_oa.bms_free.empty.PlatformType;
import com.infinitus.bms_oa.bms_free.service.CouponOrderReportService;
import com.infinitus.bms_oa.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class CouponOrderReportTask {
    @Value("${CouponOrderReport.url.value}")
    private String url;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private CouponOrderReportService service;

    /**
     * 定时任务 生产一天执行一次
     */
    @Scheduled(fixedRate = 24000 * 60 * 60)
    public void getSomeThingByday(){
        getCouponOrderReport();
    }

    private void getCouponOrderReport(){
        String nowDate = simpleDateFormat.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        PlatformType platformType = new PlatformType();
        platformType.setUseTimeStart(df.format(date)+" 00:00:00");
        //platformType.setUseTimeStart("2022-07-22 00:00:00");
        platformType.setUseTimeEnd(df.format(date)+" 23:59:59");
        log.info("platformType=:{}" + platformType);

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(platformType);
        String postData = JSONObject.toJSONString(jsonObject);
        JSONObject resultJson = HttpUtil.doPostJson(url, postData, "", "");
        log.info("resultJson=:{}" + resultJson);

        String a = resultJson.get("state").toString();
        if ("success".equals(a)) {
            List<CouponOrderReport> couponOrderReportList = (List<CouponOrderReport>) resultJson.get("data");
            if (couponOrderReportList != null) {
                for (int i = 0; i < couponOrderReportList.size(); i++) {
                    log.info("couponOrderReportList.get(i)=:{}", couponOrderReportList.get(i));
                    CouponOrderReport couponOrderReport = (CouponOrderReport) JSON.toJavaObject(couponOrderReportList.get(i), CouponOrderReport.class);
                    service.createCouponOrderReport(couponOrderReport);
                }
            }
        }else{
            log.info("获取数据失败··········a=:{}", a);
        }


    }

}
