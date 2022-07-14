package com.infinitus.bms_oa.wms_store.service.impl;

import com.infinitus.bms_oa.wms_store.empty.W_STORE_COMMODITIES;
import com.infinitus.bms_oa.wms_store.mapper.W_STORE_COMMODITIES_Mapper;
import com.infinitus.bms_oa.wms_store.service.W_STORE_COMMODITIES_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class W_STORE_COMMODITIES_ServiceImpl implements W_STORE_COMMODITIES_Service {
    @Autowired
    private W_STORE_COMMODITIES_Mapper mapper;

    @Override
    public boolean updateW_STORE_COMMODITIES(W_STORE_COMMODITIES w_store_commodities) {
        return false;
    }

    @Override
    public List<W_STORE_COMMODITIES> queryW_STORE_COMMODITIES() {
        return null;
    }

    @Override
    public boolean deleteW_STORE_COMMODITIES() {
        return mapper.deleteW_STORE_COMMODITIES();
    }

    @Override
    public boolean insertW_STORE_COMMODITIESList(List<W_STORE_COMMODITIES> commodities) {
        return mapper.insertW_STORE_COMMODITIESList(commodities);
    }

}
