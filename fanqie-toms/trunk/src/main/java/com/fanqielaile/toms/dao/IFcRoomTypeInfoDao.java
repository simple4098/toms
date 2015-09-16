package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.FcHotelInfoDto;
import com.fanqielaile.toms.dto.FcRoomTypeInfoDto;
import com.fanqielaile.toms.model.fc.FcRoomTypeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC : 房仓酒店房型Dao
 * @author : 番茄木-ZLin
 * @data : 2015/9/11
 * @version: v2.2.0
 */
public interface IFcRoomTypeInfoDao {

    List<FcRoomTypeInfo> selectFcRoomTypeByHotelId(@Param("hotelId")String hotelId);

    /**
     * 新建酒店基本房型信息
     *
     * @param fcHotelInfoDto
     */
    void insertFcRoomTypeInfo(FcHotelInfoDto fcHotelInfoDto);

    /**
     * 根据酒店ID和房型ID查询酒店房型信息
     *
     * @param hotelId
     * @param roomTypeId
     * @return
     */
    FcRoomTypeInfoDto selectFcRoomTypeByHotelIdAndRoomTypeId(@Param("hotelId") String hotelId, @Param("roomTypeId") String roomTypeId);

    /**
     * 新增酒店房型
     *
     * @param fcRoomTypeInfo
     */
    void insertRoomTypeInfo(FcRoomTypeInfo fcRoomTypeInfo);

    /**
     * 更新酒店房型信息
     *
     * @param fcRoomTypeInfo
     */
    void updateFcRoomTypeInfo(FcRoomTypeInfo fcRoomTypeInfo);
}
