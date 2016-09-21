package com.fanqielaile.toms.model.homestay.bo;

/**
 * Created by Administrator on 2016/9/2.
 */
public class FetchRoomBo extends RequestBean{
    private String roomId;
    private String city;
    private String minPrice;
    private String maxPrice;
    private String updateTime;
    private String pageIndex;
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
