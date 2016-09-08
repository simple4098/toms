/**
 * 
 */
package com.fanqielaile.toms.model.homestay.bo;

/**
 * 接口请求头
 * 
 * @author xi
 *
 */
public class RequestBean {
	private String timestamp; //当前时间毫秒
	private String signature; //加密签名
	private String otaId; //渠道ID
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getOtaId() {
		return otaId;
	}
	public void setOtaId(String otaId) {
		this.otaId = otaId;
	}

	
}
