package com.fanqielaile.toms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class InnDto {
	private Integer accountId;
	private Integer id; /**/
	private Integer omsUserId; /**/
	private String brandName; /* 客栈品牌 */
	private String innName; /* 客栈名称 */
	private String innType; /* 客栈类型 */
	private String frontPhone; /* 前台电话 */
	private String serName; /* 客服昵称 */
	private String serPhone; /* 客服电话 */
	private String serQq; /* 客服QQ */
	private String serWechat; /* 客服微信 */
	private String pubWechat; /* 公众微信号 */
	private String microblog; /* 官方微博 */
	private String province; /* 省 */
	private String city; /* 市 */
	private String county; /* 县 */
	private String addr; /* 详细地址 */
	private Double baiduLon; /* 百度经度 */
	private Double baiduLat; /* 百度纬度 */
	private Double txLon; /* 腾讯经度 */
	private Double txLat; /* 腾讯纬度 */
	private Integer roomNum; /* 房间数 */
	private Integer recNum; /* 可接待人数 */
	private String innInfo; /* 客栈简介 */
	private String facilities; /* 客栈信息 */
	private String innId;//客栈id
	@JsonIgnore
	private Date createAt; /**/
	@JsonIgnore
	private Date updateAt; /**/

	private List<OmsImg> imgList;/* 客栈图片 */

	private List<FacilitiesVo> facilitiesMap;

	public String getInnId() {
		return innId;
	}

	public void setInnId(String innId) {
		this.innId = innId;
	}

	public List<FacilitiesVo> getFacilitiesMap() {
		return facilitiesMap;
	}

	public void setFacilitiesMap(List<FacilitiesVo> facilitiesMap) {
		this.facilitiesMap = facilitiesMap;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOmsUserId() {
		return omsUserId;
	}

	public void setOmsUserId(Integer omsUserId) {
		this.omsUserId = omsUserId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getInnName() {
		return innName;
	}

	public void setInnName(String innName) {
		this.innName = innName;
	}

	public String getInnType() {
		return innType;
	}

	public void setInnType(String innType) {
		this.innType = innType;
	}

	public String getFrontPhone() {
		return frontPhone;
	}

	public void setFrontPhone(String frontPhone) {
		this.frontPhone = frontPhone;
	}

	public String getSerName() {
		return serName;
	}

	public void setSerName(String serName) {
		this.serName = serName;
	}

	public String getSerPhone() {
		return serPhone;
	}

	public void setSerPhone(String serPhone) {
		this.serPhone = serPhone;
	}

	public String getSerQq() {
		return serQq;
	}

	public void setSerQq(String serQq) {
		this.serQq = serQq;
	}

	public String getSerWechat() {
		return serWechat;
	}

	public void setSerWechat(String serWechat) {
		this.serWechat = serWechat;
	}

	public String getPubWechat() {
		return pubWechat;
	}

	public void setPubWechat(String pubWechat) {
		this.pubWechat = pubWechat;
	}

	public String getMicroblog() {
		return microblog;
	}

	public void setMicroblog(String microblog) {
		this.microblog = microblog;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Double getBaiduLon() {
		return baiduLon;
	}

	public void setBaiduLon(Double baiduLon) {
		this.baiduLon = baiduLon;
	}

	public Double getBaiduLat() {
		return baiduLat;
	}

	public void setBaiduLat(Double baiduLat) {
		this.baiduLat = baiduLat;
	}

	public Double getTxLon() {
		return txLon;
	}

	public void setTxLon(Double txLon) {
		this.txLon = txLon;
	}

	public Double getTxLat() {
		return txLat;
	}

	public void setTxLat(Double txLat) {
		this.txLat = txLat;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getRecNum() {
		return recNum;
	}

	public void setRecNum(Integer recNum) {
		this.recNum = recNum;
	}

	public String getInnInfo() {
		return innInfo;
	}

	public void setInnInfo(String innInfo) {
		this.innInfo = innInfo;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public List<OmsImg> getImgList() {
		return imgList;
	}

	public void setImgList(List<OmsImg> imgList) {
		this.imgList = imgList;
	}

}