package com.infinitus.bms_oa.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
@Slf4j
public class TestTask {
    private  static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 每30s处理一次   1000 * 1 * 30
     */
    @Scheduled(fixedRate = 1000 * 1 * 30)
    public void TestTask() throws ParseException {
        String keyValue = simpleDateFormat.format(new Date());

        log.info("···【TestTask】···，keyValue:{}",keyValue);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTimeApplicant = format.parse("2022-5-26 00:00:00");
        long a =  dateTimeApplicant.getTime();
        log.info("···【TestTask】···，a:{}",a);
    }
}
