package com.fanqielaile.toms.dto.xl;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ChangePriceMessageDto {
	private String id;
	private Integer innId;//客栈id
	private String innName;//客栈名称
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date createDate;//创建记录的时间
	private Boolean status;//是否已读，false为未读，true为已读
	private String context;//消息内容
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getInnId() {
		return innId;
	}
	public void setInnId(Integer innId) {
		this.innId = innId;
	}
	public String getInnName() {
		return innName;
	}
	public void setInnName(String innName) {
		this.innName = innName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
}
