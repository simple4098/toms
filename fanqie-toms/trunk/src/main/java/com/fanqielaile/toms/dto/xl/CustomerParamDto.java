package com.fanqielaile.toms.dto.xl;

import com.fanqie.core.dto.ParamDto;
/**
 * 客户资料分析属性
 * Created by xkj on 2015/6/4.
 */
public class CustomerParamDto extends ParamDto {
	//城市分布列表页面选择
	private Integer cityPage;
	//城市所属省份
	private String province;
	//快捷日期数据
	private String quickTime;
	
	public Integer getCityPage() {
		return cityPage;
	}
	public void setCityPage(Integer cityPage) {
		this.cityPage = cityPage;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getQuickTime() {
		return quickTime;
	}
	public void setQuickTime(String quickTime) {
		this.quickTime = quickTime;
	}
	
	
}
