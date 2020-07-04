package com.potato.boot.pojo;

import java.io.Serializable;

public class APIResponse<T> implements Serializable {
    private static final long serialVersionUID = 5241526151768786394L;
    private final String ver = "1.0";
    private boolean ret;
    private String errmsg;
    private int errcode;
    private T data;

    public APIResponse() {
    }

    private APIResponse(T t) {
        this.ret = true;
        this.data = t;
        this.errcode = 0;
    }

    private APIResponse(String errmsg, T t) {
        this.ret = false;
        this.errmsg = errmsg;
        this.data = t;
        this.errcode = -1;
    }

    private APIResponse(int errcode, String errmsg, T t) {
        this.ret = false;
        this.errmsg = errmsg;
        this.errcode = errcode;
        this.data = t;
    }

    public static <T> APIResponse<T> returnSuccess() {
        return new APIResponse((Object) null);
    }

    public static <T> APIResponse<T> returnSuccess(T t) {
        return new APIResponse(t);
    }

    public static <T> APIResponse<T> returnFail(String errmsg) {
        return new APIResponse(errmsg, (Object) null);
    }

    public static <T> APIResponse<T> returnFail(String errmsg, T t) {
        return new APIResponse(errmsg, t);
    }

    public static <T> APIResponse<T> returnFail(int errcode, String errmsg) {
        return new APIResponse(errcode, errmsg, (Object) null);
    }

    public static <T> APIResponse<T> returnFail(int errcode, String errmsg, T t) {
        return new APIResponse(errcode, errmsg, t);
    }

    public String getVer() {
        return "1.0";
    }

    public boolean isRet() {
        return this.ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrcode() {
        return this.errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }
}
