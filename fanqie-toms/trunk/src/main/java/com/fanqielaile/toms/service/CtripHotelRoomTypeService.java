package com.fanqielaile.toms.service;


import java.util.List;

import com.fanqielaile.toms.dto.ctrip.CtripHotelRoomType;


public interface CtripHotelRoomTypeService {

  
	 List<CtripHotelRoomType> findByCtripParentHotelId(String ctripParentHotelId);
	 
	 
	  void  updateRoomBypeRelation(String companyId, String json,String innId,String fcHotelId);
		 

}
