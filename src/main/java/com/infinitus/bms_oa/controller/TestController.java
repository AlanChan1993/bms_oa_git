package com.infinitus.bms_oa.controller;

import com.infinitus.bms_oa.bms_op.empty.Bms_po_api_return;
import com.infinitus.bms_oa.bms_op.service.Bms_po_api_returnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller("/")
public class TestController {

    @Autowired
    private static Bms_po_api_returnService service;

    public static void main(String[] args) {
        Bms_po_api_return bmsPoApiReturn = new Bms_po_api_return();
        bmsPoApiReturn.setDocno("111111111111");
        bmsPoApiReturn.setMsg_Type("1");
        bmsPoApiReturn.setMsg_Log("111111");
        bmsPoApiReturn.setInvoiceNO("1111111111111");
        bmsPoApiReturn.setOP_ID(Long.valueOf("702020001040"));
        int i = service.createBms_po_api_return(bmsPoApiReturn);
        log.info("i=", i);

    }
}
