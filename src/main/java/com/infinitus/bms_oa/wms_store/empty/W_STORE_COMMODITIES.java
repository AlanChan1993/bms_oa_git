package com.infinitus.bms_oa.wms_store.empty;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class W_STORE_COMMODITIES {
    //private Integer COMMODITIES_id;//
    private String  no;//编号
    private String  fullName;//全称
    private String  shortName;//简称
    private String  englishName;//英文名称
    private String  companyNo;//公司编码
    private String  brandNo;//品牌编码
    private String  type;//类型
    private String status;//状态
    private String  originalCommodityNo;//原始货品代码

    private String  unitMeasure;//单品单位
    private BigDecimal packageQuantity;//装箱数量
    private String packageSpec;//装箱规格
    private BigDecimal packageLength;//装箱长度
    private BigDecimal packageWidth;//装箱宽度
    private BigDecimal packageHeight;//装箱高度
    private BigDecimal packageVolume;//装箱体积
    private BigDecimal packageWeight;//装箱重量
    private BigDecimal unitWeight;//单品重量
    private String unitSpec;//单品规格

    private String pricingGroup;//物料定价组
    private String accountSettingGroup;//科目设置组
    private String taxCategory;//全球购税务分类
    private String taxClassification;//非全球购税务分类

    private String materialGroup;//物料组
    private String materialType;//物料类型
    private boolean envRelated;//环境相关，过期日期或生产日期
    private BigDecimal dumpPrice;//转储价
    private BigDecimal benchmarkPrice;//基准价
    private boolean directSelling;//是否直销
    private boolean employeeOnly;//是否员工专享
    private String barCode;//条形码
    private String originCountry;//原产地国家
    private String validityUnit;//有效期时间单位
    private Integer validityPeriod;//有效期
    private Integer minimumValidityPeriod;//最短有效期（短龄期）
    private Integer extendedWarrantyDuration;//延长保修时长，虚拟服务类专有字段
    private boolean fxhAvailable;//是否纷享荟可用
    private String listingDate;//上市日期
    private String syn_date;//同步日期

}
