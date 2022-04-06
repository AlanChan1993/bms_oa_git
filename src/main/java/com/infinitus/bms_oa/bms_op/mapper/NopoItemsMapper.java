package com.infinitus.bms_oa.bms_op.mapper;

import com.infinitus.bms_oa.bms_op.empty.OP_ThirdBody_NopoItems;

import java.util.List;

public interface NopoItemsMapper {

    List<OP_ThirdBody_NopoItems> getAllNopoItemsServiceByInvoice_no(String Invoice_no);

    boolean updateOpFlag(String opFlag,String Invoice_no);

}
