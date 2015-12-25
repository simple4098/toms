package com.fanqielaile.toms.service;


import java.util.List;
import java.util.Map;

import com.fanqie.bean.response.CtripHotelInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import javax.xml.bind.JAXBException;


public interface CtripHotelInfoService {

  
	 void  insert(CtripHotelInfo ctripHotelInfo);
	
	 List<CtripHotelInfo> findCtripHotelByName(String name);
	 
	 List<CtripHotelInfo> findCtripHotelByPage(String name, PageBounds pageBounds);
	 
	 CtripHotelInfo findCtripHotelInfoByParentHotelId(String parentHotelId);


	Map<String, Object> getHotelInfo() throws Exception;
}
