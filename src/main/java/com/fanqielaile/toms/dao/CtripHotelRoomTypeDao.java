package com.fanqielaile.toms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fanqielaile.toms.dto.ctrip.CtripHotelRoomType;





public interface CtripHotelRoomTypeDao {
	
	public  List<CtripHotelRoomType> findByCtripParentHotelId(@Param("ctripParentHotelId") String ctripParentHotelId);
  
}
