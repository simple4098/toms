package com.fanqielaile.toms.model.homestay.bo;

/**
 * Created by Administrator on 2016/9/2.
 */
public class FetchRoomBo {
    private String roomTd;
    private String city;
    private Integer minPrice;
    private Integer maxPrice;
    private String updateTime;
    private Integer pageIndex;
    private Integer pageSize;

    public String getRoomTd() {
        return roomTd;
    }

    public void setRoomTd(String roomTd) {
        this.roomTd = roomTd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
