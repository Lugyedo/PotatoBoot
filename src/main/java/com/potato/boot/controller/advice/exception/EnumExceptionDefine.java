package com.potato.boot.controller.advice.exception;

/**
 * @author baodehua
 * 异常定义
 */
public enum EnumExceptionDefine {
    UNKNOW(-1, "请求处理异常，请联系管理员处理"),
    CUSTOMIZE_ERROR(-100, "自定义异常"),
    PAGE_PARAMETER_ERROR(-101, "查询配置信息异常"),
    REQ_ARGUMENT_ERROR(102, "通用请求参数异常");

    private int errorCode;
    private String errorMsg;

    EnumExceptionDefine(int code, String msg) {
        this.errorCode = code;
        this.errorMsg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
