package com.infinitus.bms_oa.service.impl;

import com.infinitus.bms_oa.mapper.Bms_OA_logMapper;
import com.infinitus.bms_oa.pojo.Bms_OA_log;
import com.infinitus.bms_oa.service.Bms_OA_logService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
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
}
