package com.infinitus.bms_oa.bms_op.empty;

import lombok.Data;

@Data
public class Bms_po_api_return {
    private String Invoice_No;
    private int OP_ID;
    private String Msg_Type;
    private String Msg_Log;
    private String Docno;
    private String opstatus;
}
