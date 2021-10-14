package com.infinitus.bms_oa.enums;


public enum OaFlagEnum implements CodeEnum{
    SUCCESS(2, "同步成功"),
    FALSE(4,"同步失败"),

    ;
    private Integer code;

    private String msg;

    OaFlagEnum(Integer code, String msg) {
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
