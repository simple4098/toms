package com.fanqielaile.toms.util;

public class Constants {

	/**
	 * 时间格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 页面分页信息
	 */
	public static final Integer ROWS_DEFAULT = 10;		//默认分页行数
	public static final Integer PAGE_DEFAULT = 1;		//默认分页页数

	public final static int HTTP_SUCCESS = 200; //所有逻辑都正确
	public final static int HTTP_ERROR = 400; //访问正常但逻辑有误,app端要显示message信息
	public final static int HTTP_800 = 800;//订单重复 800
	public final static int HTTP_401 = 401;
	public final static int HTTP_404 = 404;
	public final static int HTTP_500 = 500;
	public final static int HTTP_411 = 411;//请求参数有误
	public final static int HTTP_412 = 412;//请求超时
	public final static int HTTP_413 = 413;//签名有误
	
	/** 返回key */
	public final static String STATUS = "status"; //状态
	public final static String MESSAGE = "message"; //状态说明
	public final static String TYPE = "type"; //状态类型
	public final static String CODE ="code";//返回状态码标志
	public final static String RESULT = "result";
	public final static String ERROR_CODE = "errorCode";
	public final static String DATA = "data";
	public final static String RESPONSE_FIELD_FLAG = "flag";
	/** 返回结果 */
	public final static String SUCCESS = "success";
	public final static String FAILURE = "fail";
	public final static String ORDER_REPEAT = "ORDER_REPEAT";
	public final static String ERROR_TIP_MSG = "请稍后再试";
	
}
