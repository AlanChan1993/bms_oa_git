package com.infinitus.bms_oa.enums;

public enum BmsOaLogStatusEnum implements CodeEnum {
    APPROVAL(2, "oa已审批，流程表待更新"),
    APPROVALED(6,"oa已审批，流程表已更新"),

    ;
    private Integer code;

    private String msg;

    BmsOaLogStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public String getCodeString(){
        return code+"";
    }
}
