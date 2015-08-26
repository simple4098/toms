package com.fanqielaile.toms.dto;

import java.util.Date;
import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/4
 * @version: v1.0.0
 */
public class RoomTypeInfoDto {

    public List<RoomTypeInfo> list;
    public List<String> roomDates;
    public String roomTypeId;
    public String roomTypeName;
    public Integer maxRoomNum;

    public Integer getMaxRoomNum() {
        return maxRoomNum;
    }

    public void setMaxRoomNum(Integer maxRoomNum) {
        this.maxRoomNum = maxRoomNum;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public List<RoomTypeInfo> getList() {
        return list;
    }

    public void setList(List<RoomTypeInfo> list) {
        this.list = list;
    }

    public List<String> getRoomDates() {
        return roomDates;
    }

    public void setRoomDates(List<String> roomDates) {
        this.roomDates = roomDates;
    }
}
