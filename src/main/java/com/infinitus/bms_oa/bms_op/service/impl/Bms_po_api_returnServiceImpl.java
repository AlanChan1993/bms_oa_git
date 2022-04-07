package com.infinitus.bms_oa.bms_op.service.impl;

import com.infinitus.bms_oa.bms_op.empty.Bms_po_api_return;
import com.infinitus.bms_oa.bms_op.mapper.Bms_po_api_returnMapper;
import com.infinitus.bms_oa.bms_op.service.Bms_po_api_returnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Bms_po_api_returnServiceImpl implements Bms_po_api_returnService {
    @Autowired
    private Bms_po_api_returnMapper mapper;

    @Override
    public int createBms_po_api_return(Bms_po_api_return b) {
        return mapper.createBms_po_api_return(b);
    }
}
