package com.fanqielaile.toms.exception;

/**
 * Create by jame
 * Date: 2016/9/6 10:51
 * Version: 1.0
 * Description: 携程民宿对接异常
 */
public class CtripHomeStayConnException extends RuntimeException {
    private static final String MSG = "携程民宿对接异常，";


    public CtripHomeStayConnException() {
        super();
    }

    public CtripHomeStayConnException(String message) {
        super(MSG + message);
    }

    public CtripHomeStayConnException(String message, Exception e) {
        super(MSG + message, e);
    }
}
