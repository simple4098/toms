package com.fanqielaile.toms.enums;


/**
 * Created by LZQ on 2016/9/2.
 */
public enum  ResultCode {
    SUCCESS("1","成功"),
    OTHER_EXCEPTION("101","其他错误"),
    PARAM_ERROR("102","缺少必要参数"),
    PARAM_DISABLED("103","参数无效"),
    SYSTEM_EXCEPTION("104","系统内部异常"),
    TIME_OUT("105","请求超时"),
    SIFNATURE_ERROR("106","签名有误"),
    COMMEN_BUSINESS_EXCEPTION("104","系统内部异常");
	
	public final static String CODE_412_MESSAGE ="请求超时";
	public final static String CODE_413_MESSAGE ="签名有误";

    private String code;
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
