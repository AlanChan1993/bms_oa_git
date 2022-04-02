package com.infinitus.bms_oa.bms_op.empty;

import lombok.Data;

@Data
public class OP_ScondBody_PaymentApplicationVO {

    private final String applicant="{\"maskedBankAccountCode\":\"\"}";
    private String applicantDate;//app_date
    private final String cloudSupplierId = "0";
    private String commentNote;//comment
    private String companyCode;//company_Code
    private String companyName;//company_Name
    private String expenseCategoryCode;//bms_op_order_detail.exp_cate_code
    private String expenseCategoryName;//bms_op_order_detail.exp_cate_name
    private String flowInitiator;//bms_op_order_detail.flow_Initiator
    private String flowInitiatorName;//bms_op_order_detail.flow_InitiatorName
    private String invoiceNO;//invoice_NO
    private String isCloudPay;//is_cloud_pay
    private String[] items = null;
    private final String markAsHasPo = "0";
    private String noBillPost;//no_bill_post
    private String noPoItems;//明细 OP_ThirdBody_NopoItems
    private final String[] noPoprepayIterms;
    private String payMethodCode;//pay_meth_code
    private String payMethodName;//pay_meth_name
    private String paymentDateLimit;//paymentDateLimit精确到毫秒级
    private final String[] prepayIterms;
    private final String subType = "2";
    private String supplierId;//supplier_Id
    private String supplierName;//supplier_Name
    private String title;//title
    private final String validFlag="Y";
}
