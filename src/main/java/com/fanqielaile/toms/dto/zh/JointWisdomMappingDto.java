package com.fanqielaile.toms.dto.zh;

import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/1/25
 * @version: v1.0.0
 */
public class JointWisdomMappingDto extends JointWisdomInnRoomMappingDto {

    private RoomTypeInfo roomTypeInfo;
    private OtaRoomPriceDto priceDto;

    public OtaRoomPriceDto getPriceDto() {
        return priceDto;
    }

    public void setPriceDto(OtaRoomPriceDto priceDto) {
        this.priceDto = priceDto;
    }

    public RoomTypeInfo getRoomTypeInfo() {
        return roomTypeInfo;
    }

    public void setRoomTypeInfo(RoomTypeInfo roomTypeInfo) {
        this.roomTypeInfo = roomTypeInfo;
    }
}
