package com.infinitus.bms_oa.bms_op.service.impl;

import com.infinitus.bms_oa.bms_op.empty.OP_ScondBody_PaymentApplication;
import com.infinitus.bms_oa.bms_op.mapper.PaymentApplicationVOMapper;
import com.infinitus.bms_oa.bms_op.service.PaymentApplicationVO_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentApplicationVO_ServiceImpl implements PaymentApplicationVO_Service {
    @Autowired
    private PaymentApplicationVOMapper mapper;

    @Override
    public List<OP_ScondBody_PaymentApplication> getAllPaymentApplicationVO() {
        return mapper.getAllPaymentApplicationVO();
    }

    @Override
    public boolean updateOpFlag(String opFlag,String Invoice_no) {
        return mapper.updateOpFlag(opFlag, Invoice_no);
    }
}
