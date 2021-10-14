package com.infinitus.bms_oa.pojo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class InfinitusDetailTablesRow {
    private String dh;//单号
    private String sqtjrq;//申请提交日期
    private String jsny;//结算年月
    private String sqbm;//申请部门
    private String sqr;//申请人
    private String dznr;//调整内容
    private String jsc;//结算仓
    private String gys;//供应商
    private String hzdm;//货主代码
    private String hzmc;//货主名称
    private String cwkm;//财务科目
    private String dzyy;//调整原因
    private String kpijs;//KPI计算
    private Double dzje;//调整金额
    private String dzyysm;//调整原因说明
    private String ly;//来源
    private String fjmxpz;//附件明细
}
