package com.infinitus.bms_oa.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InfinitusMainTable<T> {
    private T sqr;
    private String dh;//单号
    private String jsny;//结算年月
    private Double ysycdzje;//运输异常调整金额
    private Double ccycdzje;//仓储异常调整金额
    private Double yskkje;//运输扣款金额
    private Double cckkdzje;//仓储扣款常调整金额
    private Double ysfydzje;//运输费用调整金额
    private Double ccfydzje;//仓储费用调整金额
    private T sqbm;
    private Double zje;
    //private String recipeId;
    //private String txtTitle;
}
