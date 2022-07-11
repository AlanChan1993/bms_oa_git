package com.infinitus.bms_oa.wms_store.service;

import com.infinitus.bms_oa.wms_store.empty.W_STORE_COMMODITIES;

import java.util.List;

public interface W_STORE_COMMODITIES_Service {
    boolean updateW_STORE_COMMODITIES(W_STORE_COMMODITIES w_store_commodities);

    List<W_STORE_COMMODITIES> queryW_STORE_COMMODITIES();
}
