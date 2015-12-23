package com.fanqielaile.toms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fanqielaile.toms.dto.ctrip.CtripHotelRoomType;





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
}
