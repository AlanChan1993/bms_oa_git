package com.infinitus.bms_oa.service;

import com.infinitus.bms_oa.pojo.BmsBillAdjust;
import com.infinitus.bms_oa.pojo.PlatCode;
import java.util.Date;
import java.util.List;

public interface BmsBillAdujestService {
    BmsBillAdjust selectAdujestById(Integer id);

    String selectLoginNameById(String id);

    List<String> selectIdByBillFlag();

    List<BmsBillAdjust> selectBillByBillFlag();



    boolean updateOA_flag(String oa_flag,String id,String no);

    boolean updateStatusAndDate( String adj_no, Date approval_dt);

    String getOwnerNameByKey(String owner_key);

    String getPlatCodeName(String code);

    PlatCode getPlatCode(String code);

    String getJscName(String wh_code);

    String getGysName(String vendor_code);

    String getCwkmName(String account_code);

    List<BmsBillAdjust> getBillListDetail(String code);

    List<BmsBillAdjust> getBillListDetailView(String code,String submit_id);

    String getBillListDetail_JSNY(String code,String submit_id);

    boolean updateOA_flag(Integer oa_flag,String code);

    boolean updateLogCode(String code, String create_id);

    boolean updateStatusAndApeDate(String Code,Date approval_dt,String status);
}
