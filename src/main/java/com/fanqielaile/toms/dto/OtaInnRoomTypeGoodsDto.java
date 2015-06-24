package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.OtaInnRoomTypeGoods;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaInnRoomTypeGoodsDto extends OtaInnRoomTypeGoods {

    public static OtaInnRoomTypeGoodsDto toDto(String innId,Integer roomTypeId,Long rpid,Long gid,String companyId,String otaWgId){
        OtaInnRoomTypeGoodsDto goodsDto = new OtaInnRoomTypeGoodsDto();
        goodsDto.setCompanyId(companyId);
        goodsDto.setGid(String.valueOf(gid));
        goodsDto.setInnId(Integer.valueOf(innId));
        goodsDto.setOtaWgId(otaWgId);
        goodsDto.setRpid(String.valueOf(rpid));
        goodsDto.setRoomTypeId(Integer.valueOf(roomTypeId));
        return goodsDto;
    }
}
