package com.fanqielaile.toms.exception;


public class RequestCtripException  extends Exception{


	private static final long serialVersionUID = 1L;
	
	public RequestCtripException(String msg) {
		super(msg);
	}
	
	public RequestCtripException() {
		super("请求携程出错");
	}

}
