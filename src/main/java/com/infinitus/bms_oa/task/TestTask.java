package com.infinitus.bms_oa.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class TestTask {
    private  static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 每30s处理一次   1000 * 1 * 30
     */
    //@Scheduled(fixedRate = 1000 * 1 * 30)
    public void TestTask(){
        String keyValue = simpleDateFormat.format(new Date());
        log.info("···【TestTask】···，keyValue:{}",keyValue);
    }
}
