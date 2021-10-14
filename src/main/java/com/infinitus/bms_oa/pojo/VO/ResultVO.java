package com.infinitus.bms_oa.pojo.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/*http请求返回的最外层对象*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//用于返回实体时，不返回值为null的属性
public class ResultVO<T> {
    /*错误码*/
    private String success;

    /*提示信息*/
    private String message;

    /*返回的具体内容*/
    private T data;



}
