package com.fanqielaile.toms.bo.ctrip.homestay;

import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/9/2.
 */
public class FetchRoomBo extends RequestBean{
	@ApiModelProperty(value="房源Id",required=false)
	private String roomId;
	@ApiModelProperty(value="城市",required=true)
    private String city;
	@ApiModelProperty(value="最低价",required=false)
    private String minPrice;
	@ApiModelProperty(value="最高价",required=false)
    private String maxPrice;
	@ApiModelProperty(value="修改时间",required=false)
    private String updateTime;
    @ApiModelProperty(value="分页页码，第一页为0",required=true)
    private String pageIndex;
    @ApiModelProperty(value="分页条数",required=true)
    private String pageSize;

    public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

}
