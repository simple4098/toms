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
    public List<Date> roomDates;

    public List<RoomTypeInfo> getList() {
        return list;
    }

    public void setList(List<RoomTypeInfo> list) {
        this.list = list;
    }

    public List<Date> getRoomDates() {
        return roomDates;
    }

    public void setRoomDates(List<Date> roomDates) {
        this.roomDates = roomDates;
    }
}
