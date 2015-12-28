package com.fanqielaile.toms.exception;

public class CtripDataException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public CtripDataException() {
		super("携程返回数据错误");
	}
	
	public CtripDataException(String msg) {
		super(msg);
	}
}
