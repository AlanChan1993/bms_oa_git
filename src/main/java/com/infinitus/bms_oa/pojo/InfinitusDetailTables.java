package com.infinitus.bms_oa.pojo;

import lombok.Data;

import java.util.List;

@Data
public class InfinitusDetailTables<T> {

    private Integer index;
    private List<T> rows;

}
