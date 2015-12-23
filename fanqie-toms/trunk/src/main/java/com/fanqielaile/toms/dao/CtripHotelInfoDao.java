package com.fanqielaile.toms.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fanqielaile.toms.dto.ctrip.CtripHotelInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public interface CtripHotelInfoDao {
	
    int insert(CtripHotelInfo  ctripHotelInfo);
    
    List<CtripHotelInfo> findByHotelName(@Param("hotelName")String  hotelName);
    
    List<CtripHotelInfo> findHotelInfoByPage(@Param("hotelName") String hotelName, PageBounds pageBounds);
    
    CtripHotelInfo findByParentHotelId(@Param("parentHotelId") String parentHotelId);

  
}
