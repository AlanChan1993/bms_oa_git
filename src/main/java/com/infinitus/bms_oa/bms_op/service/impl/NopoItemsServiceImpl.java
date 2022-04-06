package com.infinitus.bms_oa.bms_op.service.impl;

import com.infinitus.bms_oa.bms_op.empty.OP_ThirdBody_NopoItems;
import com.infinitus.bms_oa.bms_op.mapper.NopoItemsMapper;
import com.infinitus.bms_oa.bms_op.service.NopoItemsService;
import org.hibernate.annotations.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NopoItemsServiceImpl implements NopoItemsService {
    @Autowired
    private NopoItemsMapper nopoItemsMapper;

    @Override
    public List<OP_ThirdBody_NopoItems> getAllNopoItemsServiceByInvoice_no(String Invoice_no) {
        return nopoItemsMapper.getAllNopoItemsServiceByInvoice_no(Invoice_no);
    }

    @Override
    public boolean updateOpFlag(String opFlag,String Invoice_no) {
        return nopoItemsMapper.updateOpFlag(opFlag, Invoice_no);
    }
}
