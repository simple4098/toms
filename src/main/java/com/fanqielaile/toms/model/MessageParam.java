package com.fanqielaile.toms.model;

import org.hibernate.validator.constraints.NotEmpty;

public class MessageParam {
	//查询消息开始时间
	@NotEmpty(message="开始时间不能为空")
	private String from;
	//查询消息结束时间
	@NotEmpty(message="结束时间不能为空")
	private String to;
	//消息所属公司的id
	private String companyId;
	//消息状态，1为已读，0为未读
	private Boolean status;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
