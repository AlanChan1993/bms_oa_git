package com.infinitus.bms_oa.task;

import com.infinitus.bms_oa.utils.DateUtil;
import com.infinitus.bms_oa.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BMS_OP_TASKS {
    MD5Util md5Util = new MD5Util();

    /**
     * 每30s处理一次   1000 * 1 * 30
     * 执行定时任务，用于将bms_oa_log中所需同步数据封装同步
     */
    @Scheduled(fixedRate = 1000 * 1 * 30)
    public void BMS_OP_TASKS() {
        log.info("·································"+ new DateUtil().getNowDate());

    }


}
