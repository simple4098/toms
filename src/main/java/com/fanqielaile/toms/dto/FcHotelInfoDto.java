package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.fc.FcHotelInfo;
import com.fanqielaile.toms.model.fc.FcRoomTypeInfo;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/9/11
 * @version: v1.0.0
 */
public class FcHotelInfoDto extends FcHotelInfo {
    //房型
    private List<FcRoomTypeInfo> fcRoomTypeInfos;

    public FcHotelInfoDto() {
    }

    public FcHotelInfoDto(String hotelName) {
        super.setHotelName(hotelName);
    }

    public List<FcRoomTypeInfo> getFcRoomTypeInfos() {
        return fcRoomTypeInfos;
    }

    public void setFcRoomTypeInfos(List<FcRoomTypeInfo> fcRoomTypeInfos) {
        this.fcRoomTypeInfos = fcRoomTypeInfos;
    }
}
