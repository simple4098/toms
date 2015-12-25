package com.fanqielaile.toms.service;


import com.fanqie.bean.response.CtripHotelRoomType;

import java.util.List;



public interface CtripHotelRoomTypeService {

  
	 List<CtripHotelRoomType> findByCtripParentHotelId(String ctripParentHotelId);
	 
	 
	  void  updateRoomBypeRelation(String companyId, String json,String innId,String fcHotelId);
		 

}
