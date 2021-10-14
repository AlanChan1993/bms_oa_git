package com.infinitus.bms_oa.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Infinitus<T> {

    private String workcode;//工号
    private String workflowId;//流程Id：367
    private String requestName;//流程名称
    private T mainTable;//主表申请人
    private List<T> detailTables;//明细表

    private String dh;//单号
    private String jsny;//结算年月
    private BigDecimal ysycdzje;//运输异常调整金额
    private BigDecimal ccycdzje;//仓储异常调整金额
    private Double zje;
    private String sqbm;
}
