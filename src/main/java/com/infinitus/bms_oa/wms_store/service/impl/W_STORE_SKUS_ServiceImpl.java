package com.infinitus.bms_oa.wms_store.service.impl;

import com.infinitus.bms_oa.wms_store.empty.W_STORE_SKUS;
import com.infinitus.bms_oa.wms_store.mapper.W_STORE_SKUS_Mapper;
import com.infinitus.bms_oa.wms_store.service.W_STORE_SKUS_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class W_STORE_SKUS_ServiceImpl implements W_STORE_SKUS_Service {

    @Autowired
    private W_STORE_SKUS_Mapper mapper;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public boolean updateW_STORE_SKUS(W_STORE_SKUS w_store_skus) {
        w_store_skus.setSyn_date(simpleDateFormat.format(new Date()));
        boolean a = mapper.insertW_STORE_SKUS(w_store_skus);
        log.info("insertW_STORE_SKUS==============a:{}",a);
        return a;
    }

    @Override
    public List<W_STORE_SKUS> queryW_STORE_SKUS() {
        List<W_STORE_SKUS> list = mapper.queryW_STORE_SKUS();
        return list;
    }

    @Override
    public boolean deleteW_STORE_SKUS() {
        return mapper.deleteW_STORE_SKUS();
    }

    @Override
    public boolean insertSkusList(List<W_STORE_SKUS> list) {
        return mapper.insertSkusList(list);
    }
}
