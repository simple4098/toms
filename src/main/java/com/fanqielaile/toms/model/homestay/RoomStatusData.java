package com.fanqielaile.toms.model.homestay;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by LZQ on 2016/9/2.
 */
public class RoomStatusData {
	@ApiModelProperty(value="价格日期,格式:yyyy-mm-dd,如:2015-10-21",required=true)
    private String date;
	@ApiModelProperty(value="房源单价,单位：分；")
	private Integer price;
	@ApiModelProperty(value="房源原价,单位：分；")
    private Integer originPrice;
	@ApiModelProperty(value="可预定数")
    private Integer stock;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

	public Integer getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(Integer originPrice) {
		this.originPrice = originPrice;
	}
    
}
