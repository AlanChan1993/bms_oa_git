package com.infinitus.bms_oa.wms_store.mapper;

import com.infinitus.bms_oa.wms_store.empty.W_STORE_COMMODITIES;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface W_STORE_COMMODITIES_Mapper {
    boolean updateW_STORE_COMMODITIES(W_STORE_COMMODITIES w_store_commodities);

    List<W_STORE_COMMODITIES> queryW_STORE_COMMODITIES();

    boolean deleteW_STORE_COMMODITIES();

    boolean insertW_STORE_COMMODITIESList(@Param("list") List<W_STORE_COMMODITIES> commodities);
}
