package com.fanqielaile.toms.dto.ctrip;

import com.fanqie.core.Domain;

import java.util.Date;

public class CtripRoomTypeMapping extends  Domain{
	
	 private String   innId ; // 客栈id
     private String   ctripChildHotelId ; // 房仓酒店id
	 private String   companyId ; // 公司id
	 private String   tomRoomTypeId ; // 番茄房型id
	 private String   ctripChildRoomTypeId ; // 携程房型id
	 private Double   roomArea ;
	 private String   tomRoomTypeName ; // 番茄房型名称
	 private String   ctripRoomTypeName ; // 房仓房型名称
	 private String    ratePlanCode; // 价格计划id
	 private  Integer   sj ; // 1 上架 0 下架 -1 没有上架
	 private String   bedNum ;
	 private String   bedLen ;
	 private String   bedWid ;
	 private String	  ratePlanCodeName;
	 
	public void setId(Long id) {
		super.setId(id.toString());
	}

	public CtripRoomTypeMapping(Integer sj, String innId, String companyId) {
		this.sj = sj;
		this.innId = innId;
		this.companyId = companyId;
	}
	public CtripRoomTypeMapping() {
	}

	public String getInnId() {
		return innId;
	}
	public void setInnId(String innId) {
		this.innId = innId;
	}
	public String getCtripChildHotelId() {
		return ctripChildHotelId;
	}
	public void setCtripChildHotelId(String ctripChildHotelId) {
		this.ctripChildHotelId = ctripChildHotelId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getTomRoomTypeId() {
		return tomRoomTypeId;
	}
	public void setTomRoomTypeId(String tomRoomTypeId) {
		this.tomRoomTypeId = tomRoomTypeId;
	}
	public String getCtripChildRoomTypeId() {
		return ctripChildRoomTypeId;
	}
	public void setCtripChildRoomTypeId(String ctripChildRoomTypeId) {
		this.ctripChildRoomTypeId = ctripChildRoomTypeId;
	}
	public Double getRoomArea() {
		return roomArea;
	}
	public void setRoomArea(Double roomArea) {
		this.roomArea = roomArea;
	}
	public String getTomRoomTypeName() {
		return tomRoomTypeName;
	}
	public void setTomRoomTypeName(String tomRoomTypeName) {
		this.tomRoomTypeName = tomRoomTypeName;
	}
	public String getCtripRoomTypeName() {
		return ctripRoomTypeName;
	}
	public void setCtripRoomTypeName(String ctripRoomTypeName) {
		this.ctripRoomTypeName = ctripRoomTypeName;
	}
	public String getRatePlanCode() {
		return ratePlanCode;
	}
	public void setRatePlanCode(String ratePlanCode) {
		this.ratePlanCode = ratePlanCode;
	}

	public Integer getSj() {
		return sj;
	}

	public void setSj(Integer sj) {
		this.sj = sj;
	}

	public String getBedNum() {
		return bedNum;
	}
	public void setBedNum(String bedNum) {
		this.bedNum = bedNum;
	}
	public String getBedLen() {
		return bedLen;
	}
	public void setBedLen(String bedLen) {
		this.bedLen = bedLen;
	}
	public String getBedWid() {
		return bedWid;
	}
	public void setBedWid(String bedWid) {
		this.bedWid = bedWid;
	}
	public String getRatePlanCodeName() {
		return ratePlanCodeName;
	}
	public void setRatePlanCodeName(String ratePlanCodeName) {
		this.ratePlanCodeName = ratePlanCodeName;
	}

}
