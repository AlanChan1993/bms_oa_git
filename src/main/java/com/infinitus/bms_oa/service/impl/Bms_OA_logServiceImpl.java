package com.infinitus.bms_oa.service.impl;

import com.infinitus.bms_oa.mapper.Bms_OA_logMapper;
import com.infinitus.bms_oa.pojo.Bms_OA_log;
import com.infinitus.bms_oa.service.Bms_OA_logService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class Bms_OA_logServiceImpl implements Bms_OA_logService {
    @Autowired
    private Bms_OA_logMapper mapper;

    @Override
    public List<Bms_OA_log> getBmsOaLog() {
        return mapper.getBmsOaLog();
    }

    @Override
    public boolean updateOaFlag(Integer oa_flag,String code) {
        return mapper.updateOaFlag(oa_flag,code);
    }

    @Override
    public boolean delBmsOALog(String code) {
        return mapper.delBmsOALog(code);
    }

    @Override
    public List<Bms_OA_log> selectBmsOaLogAll() {
        return mapper.selectBmsOaLogAll();
    }

    @Override
    public boolean modifyLogStatus(String code, Date approval_dt) {
        return mapper.modifyLogStatus(code,approval_dt);
    }

    @Override
    public List<String> getBmsOaLogByCreateId(String create_id) {
        return mapper.getBmsOaLogByCreateId( create_id);
    }

    @Override
    public boolean createBmsOaLog(String code, String bill_code, String creator) {
        return mapper.createBmsOaLog(code,bill_code,creator);
    }

    @Override
    public boolean updateBillLogCode(String code,List<String> stringList) {
        boolean a = false;
        try {
            mapper.updateBillLogCode(code,stringList);
            mapper.updateBillLogCodeEx(code,stringList);
            mapper.updateBillLogCodeExEmpt(code,stringList);
            a = true;
        } catch (Exception ex) {
            log.info("【updateBillLogCode】修改log_code失败，ex:{}", ex);
        }
        return a;
    }

    @Override
    public Bms_OA_log getBmsOaLogByCode(String code) {
        return mapper.getBmsOaLogByCode(code);
    }

    @Override
    public List<Bms_OA_log> getBillCodeByStatus(String status) {
        return mapper.getBillCodeByStatus(status);
    }

    @Override
    public boolean updateLogStatus(String status, List<String> list) {
        return mapper.updateLogStatus(status, list);
    }


}
