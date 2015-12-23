package com.fanqielaile.toms.service;


import java.util.List;

import com.fanqielaile.toms.dto.ctrip.CtripHotelInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public interface CtripHotelInfoService {

  
	 void  insert(CtripHotelInfo ctripHotelInfo);
	
	 List<CtripHotelInfo> findCtripHotelByName(String name);
	 
	 List<CtripHotelInfo> findCtripHotelByPage(String name, PageBounds pageBounds);
	 
	 CtripHotelInfo findCtripHotelInfoByParentHotelId(String parentHotelId);
		 
	
}
