package com.infinitus.bms_oa.bms_op.empty;

import lombok.Data;

@Data
public class OP_First_Body {
    private final String documentInfoVo="{\"id\":0,\"systemId\":0}";
    private String personVo;
    private String paymentApplicationVo;
}
