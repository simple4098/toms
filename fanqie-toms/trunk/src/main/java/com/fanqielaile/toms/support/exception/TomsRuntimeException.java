package com.fanqielaile.toms.support.exception;

/**
 * Created by wangdayin on 2015/5/12.
 */
public class TomsRuntimeException extends RuntimeException {
    public TomsRuntimeException() {
        super();
    }

    public TomsRuntimeException(String message) {
        super(message);
    }

    public TomsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TomsRuntimeException(Throwable cause) {
        super(cause);
    }
}
