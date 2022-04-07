package com.infinitus.bms_oa.bms_op.empty;

import lombok.Data;

@Data
public class Bms_po_api_return {
    private String invoiceNO;
    private Integer OP_ID;
    private String Msg_Type;
    private String Msg_Log;
    private String Docno;
    private final String opstatus="0";
}
