package com.fanqielaile.toms.dto;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/11/2
 * @version: v1.0.0
 */
public class AddFangPrice {

    private Integer roomTypeId;

    private String startDateStr;

    private String endDateStr;

    private Double priceChange;

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(Double priceChange) {
        this.priceChange = priceChange;
    }
}
