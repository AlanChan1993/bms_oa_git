package com.infinitus.bms_oa.mapper;


import com.infinitus.bms_oa.pojo.BmsBillAdjust;
import com.infinitus.bms_oa.pojo.Infinitus;
import com.infinitus.bms_oa.pojo.PlatCode;
import org.apache.ibatis.annotations.Mapper;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface BmsBillAdujestMapper {
    BmsBillAdjust selectAdujestById(Integer id);

    Infinitus getInfinitusBill(Integer id);

    String selectLoginNameById(String id);

    List<Map<Object,String>> selectIdByBillFlag();

    List<BmsBillAdjust> selectBillByBillFlag();

    List<BmsBillAdjust> selectExceptionByBillFlag();

    Boolean updateOA_flag(String oa_flag,String id);

    Boolean updateOA_flag_ex(String oa_flag,String id);

    Boolean updateBillStatusDate(String adj_no, Date approval_dt);

    Boolean updateExceptionStatusDate( String adj_no, Date approval_dt);

    String getOwnerNameByKey(String owner_key);

    String getPlatCodeName(String code);

    PlatCode getPlatCode(String code);

    String getJscName(String wh_code);

    String getGysName(String vendor_code);

    String getCwkmName(String account_code);

    List<BmsBillAdjust> getBillListDetail(List<String> adj_noArray);

    boolean updateOaFlag_all(Integer oa_flag,List<String> noList);

    boolean updateOaFlagEx_all(Integer oa_flag,List<String> noList);
}
