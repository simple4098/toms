package com.fanqielaile.toms.dao;

import java.util.List;

import com.fanqie.bean.response.CtripHotelInfo;
import com.fanqie.bean.response.CtripHotelRoomType;
import org.apache.ibatis.annotations.Param;






public interface CtripHotelRoomTypeDao {
	
	public  List<CtripHotelRoomType> findByCtripParentHotelId(@Param("ctripParentHotelId") String ctripParentHotelId);

	/**
	 * 保存携程母酒店对应的母房型
	 * @param ctripHotelRoomType
	 */
	void saveCtripHotelRoomType(CtripHotelRoomType ctripHotelRoomType);

	/**
	 *
	 * @param parentHotelId 母酒店id
	 * @param roomTypeId 母房型id
	 */
	CtripHotelRoomType findByCtripParentHotelIdAndRoomTypeId(@Param("parentHotelId")String parentHotelId, @Param("roomTypeId")String roomTypeId);

	/**
	 * 根据携程酒店信息批量插入酒店房型信息
	 *
	 * @param ctripHotelInfo
	 */
	void insertCtripHotelRoomTypeByCtripHotel(@Param("ctripHotelInfo") CtripHotelInfo ctripHotelInfo);
}
