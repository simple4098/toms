package com.fanqielaile.toms.dto.xl;

public class CustomerAnalysisDto {
	//省份
	private String province;
	//省份客户数量
	private Integer provinceGuestCount;
	//城市
	private String city;
	//城市客户数量
	private Integer cityGuestCount;
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Integer getProvinceGuestCount() {
		return provinceGuestCount;
	}
	public void setProvinceGuestCount(Integer provinceGuestCount) {
		this.provinceGuestCount = provinceGuestCount;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getCityGuestCount() {
		return cityGuestCount;
	}
	public void setCityGuestCount(Integer cityGuestCount) {
		this.cityGuestCount = cityGuestCount;
	}
}
