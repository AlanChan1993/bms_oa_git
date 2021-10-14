package com.infinitus.bms_oa.utils;

import com.infinitus.bms_oa.pojo.BmsBillAdjust;
import com.infinitus.bms_oa.pojo.InfinitusDetailTablesRow;

import java.text.SimpleDateFormat;

public class InfinitusUtil {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public InfinitusDetailTablesRow setInfinitusDetailTablesRow(BmsBillAdjust billAdjust) {
        InfinitusDetailTablesRow infinitusDetailTablesRow = new InfinitusDetailTablesRow();
        infinitusDetailTablesRow.setSqr(billAdjust.getCreate_id());
        infinitusDetailTablesRow.setDh(billAdjust.getAdj_no());
        infinitusDetailTablesRow.setSqtjrq(simpleDateFormat.format(billAdjust.getAdj_dt()));//申请提交日期
        infinitusDetailTablesRow.setJsny(simpleDateFormat.format(billAdjust.getSettle_year_month()));
        infinitusDetailTablesRow.setJsc(billAdjust.getSettle_wh_code());
        infinitusDetailTablesRow.setGys(billAdjust.getVendor_no());
        infinitusDetailTablesRow.setCwkm(billAdjust.getFinance_account_no());
        infinitusDetailTablesRow.setDzje(billAdjust.getAdj_amount());
        String tznr=billAdjust.getAdj_type();
        if ("01".equals(tznr)) {
            tznr = "运费费用调整";
        } else if("02".equals(tznr)){
            tznr = "仓储费用调整";
        }
        infinitusDetailTablesRow.setDznr(tznr);
        infinitusDetailTablesRow.setDzyy(billAdjust.getAdj_reason());
        infinitusDetailTablesRow.setDzyysm(billAdjust.getComments());
        infinitusDetailTablesRow.setHzdm(billAdjust.getOwner_key());
        infinitusDetailTablesRow.setHzmc(billAdjust.getOwner_name());
        return infinitusDetailTablesRow;
    }
}
