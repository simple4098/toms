package com.fanqielaile.toms.model.fc;

import com.fanqie.core.Domain;

/**
 * DESC : 房仓酒店房型实体
 * @author : 番茄木-ZLin
 * @data : 2015/9/11
 * @version: v2.2.0
 */
public class FcRoomTypeInfo extends Domain {

    //fc酒店id
    private String hotelId;
    //fc 酒店房型id
    private String roomTypeId;
    //fc 酒店房型名称
    private String  roomTypeName;
    //fc 床型
    private String bedType;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
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

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }
}
