package com.infinitus.bms_oa.controller;

import com.infinitus.bms_oa.pojo.Bms_OA_log;
import com.infinitus.bms_oa.service.Bms_OA_logService;
import com.infinitus.bms_oa.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("bmsOAlogController")
@Slf4j
public class Bms_OA_logController {
    @Autowired
    private Bms_OA_logService service;

    @RequestMapping("getBmsOaLog")
    @ResponseBody
    public List<Bms_OA_log> getBmsOaLog() {
        log.info("【Bms_OA_logController】,KeyUtil.genUniqueKey():{}" + KeyUtil.genUniqueKey());
        return service.getBmsOaLog();
    }

    @RequestMapping("updateOaFlag")
    public boolean updateOaFlag(Integer oa_flag, String code) {
        return service.updateOaFlag(oa_flag, code);
    }

    @RequestMapping("delBmsOALog")
    public boolean delBmsOALog(String code) {
        return service.delBmsOALog(code);
    }

    @RequestMapping("selectBmsOaLogAll")
    public List<Bms_OA_log> selectBmsOaLogAll(){
        return service.selectBmsOaLogAll();
    }
}
