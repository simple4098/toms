package com.fanqielaile.toms.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestParam;

public class PmsCancelOrderParam {
	/* oms订单号 */
	@NotNull(message = "oms订单号不能为空！")
	private String omsOrderCode;
	/* 扣款状态 true为扣款，false不扣款 */
	private boolean refundStatus = true;
	/*toms订单id*/
	private String id;
	/*toms操作用户id*/
	private String userId;
	/*toms公司id*/
	private String companyId;

	public String getOmsOrderCode() {
		return omsOrderCode;
	}

	public void setOmsOrderCode(String omsOrderCode) {
		this.omsOrderCode = omsOrderCode;
	}

	public boolean isRefundStatus() {
		return refundStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRefundStatus(boolean refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
