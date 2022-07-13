package com.infinitus.bms_oa.wms_store.empty;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Data
public class W_STORE_SKUS {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //private Integer store_id;
    private String  no;//sku编码
    private String fullName;//sku全称
    private String shortName;//sku简称
    private String spuNo;//所属spu的编码
    //private String store_url;//
    //private String store_type;//
    //private String mainResources;
    private Integer number;//页码
    private Integer size;//大小
    private Integer totalPages;//总页数
    private Integer totalElements;//总元素
    private String syn_date ;//同步时间
    private String commodityNo;
    private String status;//sku状态。"SALEABLE": "可销售"; "DEVELOPABLE":"可入库"; "TO_BE_PREPARE": "可筹备"; "ARCHIVED":"已归档"; "DELISTED": "已退市";
    //private String prices;
}
