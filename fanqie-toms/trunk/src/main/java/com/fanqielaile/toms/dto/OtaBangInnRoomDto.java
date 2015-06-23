package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.OtaBangInnRoom;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaBangInnRoomDto extends OtaBangInnRoom {

    public static OtaBangInnRoomDto toDto(String innId,Integer roomTypeId,String roomName,String companyId,String priceModelId,String otaWgId,Long rid){
        OtaBangInnRoomDto otaBangInnRoomDto = new OtaBangInnRoomDto();
        otaBangInnRoomDto.setCompanyId(companyId);
        otaBangInnRoomDto.setInnId(Integer.valueOf(innId));
        otaBangInnRoomDto.setRoomTypeName(roomName);
        otaBangInnRoomDto.setPriceModelId(priceModelId);
        otaBangInnRoomDto.setRoomTypeId(Integer.valueOf(roomTypeId));
        otaBangInnRoomDto.setOtaWgId(otaWgId);
        otaBangInnRoomDto.setrId(String.valueOf(rid));
        return otaBangInnRoomDto;
    }
}
