package com.infinitus.bms_oa.wms_store.empty;

import lombok.Data;

import java.util.Date;

@Data
public class W_STORE_SKUS {
    //private Integer store_id;
    private String  store_no;//sku编码
    private String store_fullName;//sku全称
    private String store_shortName;//sku简称
    private String store_spuNo;//所属spu的编码
    private String store_url;//
    private String store_type;//
    private Integer store_number;//页码
    private Integer store_size;//大小
    private Integer store_totalPages;//总页数
    private Integer store_totalElements;//总元素
    private String syn_date;//同步时间

}
