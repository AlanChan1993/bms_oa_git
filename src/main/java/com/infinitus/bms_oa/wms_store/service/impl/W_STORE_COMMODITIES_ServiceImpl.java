package com.infinitus.bms_oa.wms_store.service.impl;

import com.infinitus.bms_oa.wms_store.empty.W_STORE_COMMODITIES;
import com.infinitus.bms_oa.wms_store.service.W_STORE_COMMODITIES_Service;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class W_STORE_COMMODITIES_ServiceImpl implements W_STORE_COMMODITIES_Service {
    @Override
    public boolean updateW_STORE_COMMODITIES(W_STORE_COMMODITIES w_store_commodities) {
        return false;
    }

    @Override
    public List<W_STORE_COMMODITIES> queryW_STORE_COMMODITIES() {
        return null;
    }
}
