package com.fanqielaile.toms.model.homestay;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by LZQ on 2016/9/2.
 */
public class Address {
	@ApiModelProperty(value="房源所在城市",required=true)
    private String city;
	@ApiModelProperty(value="房源详细地址",required=true)
    private String addr;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
