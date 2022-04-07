package com.infinitus.bms_oa.bms_op.mapper;

import com.infinitus.bms_oa.bms_op.empty.OP_ScondBody_PaymentApplication;

import java.util.List;

public interface PaymentApplicationVOMapper {

    List<OP_ScondBody_PaymentApplication> getAllPaymentApplicationVO();

    boolean updateOpFlag(String opFlag,String Invoice_no);
}
