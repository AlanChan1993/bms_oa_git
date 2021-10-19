package com.infinitus.bms_oa.enums;

public enum ResultEnum implements CodeEnum{
    SUCCESS(2, "success"),
    FALSE(4,"fail"),
    NODATA(6,"noData"),

    ;
    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
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
