package com.infinitus.bms_oa.wms_store.mapper;

import com.infinitus.bms_oa.wms_store.empty.W_STORE_SKUS;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface W_STORE_SKUS_Mapper {
    boolean insertW_STORE_SKUS(W_STORE_SKUS w_store_skus);

    boolean deleteW_STORE_SKUS();

    List<W_STORE_SKUS> queryW_STORE_SKUS();
}
