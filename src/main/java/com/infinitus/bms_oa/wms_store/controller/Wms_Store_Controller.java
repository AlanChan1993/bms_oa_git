package com.infinitus.bms_oa.wms_store.controller;

import com.infinitus.bms_oa.wms_store.empty.W_STORE_SKUS;
import com.infinitus.bms_oa.wms_store.service.W_STORE_COMMODITIES_Service;
import com.infinitus.bms_oa.wms_store.service.W_STORE_SKUS_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/W_STORE_SKUS_Controller")
public class Wms_Store_Controller {
    @Autowired
    private W_STORE_SKUS_Service store_skus_service;
    @Autowired
    private W_STORE_COMMODITIES_Service store_commodities_service;

    @PostMapping("insert")
    @ResponseBody
    public Object updateW_STORE_SKUS(W_STORE_SKUS w_store_skus){
        Object a = store_skus_service.updateW_STORE_SKUS(w_store_skus);

        return a;
    }

}
