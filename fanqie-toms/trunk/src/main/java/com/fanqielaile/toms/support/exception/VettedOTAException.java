package com.fanqielaile.toms.support.exception;

/**
 * DESC : 验证渠道的 appKey 是否有效
 * @author : 番茄木-ZLin
 * @data : 2015/11/23
 * @version: v1.0.0
 */
public class VettedOTAException extends Exception {

    public VettedOTAException() {
    }

    public VettedOTAException(String message) {
        super(message);
    }
}
