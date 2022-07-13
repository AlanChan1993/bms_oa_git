package com.infinitus.bms_oa.wms_store.empty;

import lombok.Data;

@Data
public class PagePojo {
    private Integer number;
    private Integer size;
    private Integer totalPages;
    private Integer totalElements;

}
