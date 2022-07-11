package com.infinitus.bms_oa.wms_store.service;

import com.infinitus.bms_oa.wms_store.empty.W_STORE_SKUS;

import java.util.List;

public interface W_STORE_SKUS_Service {
    boolean updateW_STORE_SKUS(W_STORE_SKUS w_store_skus);

    List<W_STORE_SKUS> queryW_STORE_SKUS();

}
