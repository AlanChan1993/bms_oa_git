package com.infinitus.bms_oa.bms_op.empty;

import lombok.Data;

import java.util.List;

@Data
public class OP_ScondBody_PaymentApplication {
    private String orgCode;//app_org_code
    private String userRealName;//app_user_real_name
    private String username;//app_user_name

    //private MaskedBankAccountCode applicant;
    private String applicantDate;//app_date
    //private final String cloudSupplierId = "0";
    private String commentNote;//comment
    private String companyCode;//company_Code
    private String companyName;//company_Name
    private String expenseCategoryCode;//bms_op_order_detail.exp_cate_code
    private String expenseCategoryName;//bms_op_order_detail.exp_cate_name
    private String flowInitiator;//bms_op_order_detail.flow_Initiator
    private String flowInitiatorName;//bms_op_order_detail.flow_InitiatorName
    private String invoiceNO;//invoice_NO
    private String isCloudPay;//is_cloud_pay
    //private  String items ;
    //private final String markAsHasPo = "0";
    private String noBillPost;//no_bill_post
    //private List<OP_ThirdBody_NopoItems> noPoItems;//明细 OP_ThirdBody_NopoItems
    //private  String noPoprepayIterms;
    private String payMethodCode;//pay_meth_code
    private String payMethodName;//pay_meth_name
    private String paymentDateLimit;//paymentDateLimit精确到毫秒级
    //private  String prepayIterms;
    //private final String subType = "2";
    private String supplierId;//supplier_Id
    private String supplierName;//supplier_Name
    private String title;//title
    //private final String validFlag="Y";


}
