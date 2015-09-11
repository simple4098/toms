package com.fanqielaile.toms.dao;

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


}
