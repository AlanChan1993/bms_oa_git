package com.infinitus.bms_oa.mapper;

import com.infinitus.bms_oa.pojo.Bms_OA_log;

import java.util.Date;
import java.util.List;

public interface Bms_OA_logMapper {

    List<Bms_OA_log> getBmsOaLog();

    boolean updateOaFlag(Integer oa_flag,String code);

    boolean delBmsOALog(String code);

    List<Bms_OA_log> selectBmsOaLogAll();

    boolean modifyLogStatus(String code, Date approval_dt);

}
