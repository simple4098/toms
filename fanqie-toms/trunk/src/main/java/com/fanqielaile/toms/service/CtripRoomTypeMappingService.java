package com.fanqielaile.toms.service;

import java.util.List;

import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;





public interface CtripRoomTypeMappingService {
  
	
	/**
	 *  查询酒店房型的绑定信息
	 * @param companyId 公司ID 
	 * @param childHotelId 携程 子酒店ID
	 * @return
	 */
	List<CtripRoomTypeMapping> findRoomTypeMapping(String companyId,String childHotelId);
	
}
