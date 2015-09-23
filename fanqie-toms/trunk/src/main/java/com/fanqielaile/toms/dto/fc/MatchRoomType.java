package com.fanqielaile.toms.dto.fc;

/**
 * DESC :房型匹配接受参数
 * @author : 番茄木-ZLin
 * @data : 2015/9/22
 * @version: v1.0.0
 */
public class MatchRoomType {

    private String roomTypeId;
    //番茄房型面积
    private Double roomArea;
    //番茄房型名称
    private String roomTypeName;
    //房仓房型id
    private String fcRoomTypeId;
    //房仓房型名称
    private String fcRoomTypeName;

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getFcRoomTypeId() {
        return fcRoomTypeId;
    }

    public void setFcRoomTypeId(String fcRoomTypeId) {
        this.fcRoomTypeId = fcRoomTypeId;
    }

    public Double getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(Double roomArea) {
        this.roomArea = roomArea;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getFcRoomTypeName() {
        return fcRoomTypeName;
    }

    public void setFcRoomTypeName(String fcRoomTypeName) {
        this.fcRoomTypeName = fcRoomTypeName;
    }
}
