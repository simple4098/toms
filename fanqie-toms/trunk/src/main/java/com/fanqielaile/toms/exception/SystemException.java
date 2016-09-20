package com.fanqielaile.toms.exception;

import com.fanqielaile.toms.enums.ResultCode;

/**
 * Created by LZQ on 2016/9/2.
 */
public class SystemException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
    private String code;
    
    public SystemException(ResultCode resultCode) {
    	this.msg = resultCode.getMessage();
    	this.code = resultCode.getCode();
    }
    public SystemException( String code,String msg) {
        this.msg = msg;
        this.code = code;
    }

    public SystemException() {
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
