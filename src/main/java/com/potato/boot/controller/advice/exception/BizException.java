package com.potato.boot.controller.advice.exception;

/**
 * 统一业务异常定义
 */
public class BizException extends RuntimeException {
    private int errorCode;

    public BizException(int errorCode, String errMsg) {
        super(errMsg);
        this.errorCode = errorCode;
    }

    public BizException(EnumExceptionDefine exceptionDefine) {
        super(exceptionDefine.getErrorMsg());
        this.errorCode = exceptionDefine.getErrorCode();
    }

    public int getErrorCode() {
        return errorCode;
    }
}
