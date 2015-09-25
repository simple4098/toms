package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.FcHotelInfoDto;
import com.fanqielaile.toms.dto.FcRoomTypeInfoDto;
import com.fanqielaile.toms.model.fc.FcRoomTypeInfo;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/15
 * @version: v1.0.0
 */
public interface IFcHotelInfoService {

    /**
     * 查询房仓酒店
     * @param innName 客栈名称
     */
    List<FcHotelInfoDto> findFcHotel(String innName);

    /**
     * 根据房仓酒店id查询酒店基础信息
     * @param hotelId 房仓酒店id
     */
    FcHotelInfoDto findFcHotelByHotelId(String hotelId);

    /**
     *  匹配酒店id
     * @param innId 客栈id
     * @param fcHotelId 房仓酒店id
     */
    void updateMatchInn(String companyId,String innId, String fcHotelId)throws Exception;

    /**
     * 根据酒店id 查询 房仓酒店房型信息
     * @param fcHotelId 房仓酒店id
     */
    List<FcRoomTypeInfo> finFcRoomTypeByHotelId(String fcHotelId);

}
