package com.infinitus.bms_oa.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BmsBillAdjust {
    private String id;//
    private String adj_no;//调整单号
    private String adj_type;//调整单类型
    private Date adj_dt;//
    private String owner_key;//货主
    private String owner_name;
    private String settle_wh_code;//结算仓
    private String vendor_no;//供应商
    private String finance_account_no;//财务科目
    private String fee_item_code;//费用类型
    private String adj_reason;//调整原因
    private Double adj_amount;//调整金额
    private Date settle_year_month;//结算年月
    private String do_no;//运单号
    private String biz_type;//业务类型
    private String ship_method;//运输方式
    private Double org_fee;//原单费用
    private String comments;//备注
    private String attachment;//附件
    private String settle_status;//结算状态
    private String settle_no;//结算单号
    private String status;//状态
    private String submit_id;
    private Date submit_dt;
    private String approval_id;
    private Date approval_dt;
    private String create_id;
    private Date create_dt;
    private String update_id;
    private Date update_dt;
    private String delete_id;
    private Date delete_dt;
    private String is_freeze;
    private String is_kpi;

}
