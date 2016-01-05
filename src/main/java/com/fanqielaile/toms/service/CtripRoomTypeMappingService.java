package com.fanqielaile.toms.service;

import java.util.List;

import javax.xml.bind.JAXBException;

import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.exception.RequestCtripException;





public interface CtripRoomTypeMappingService {
  
	
	/**
	 *  查询酒店房型的绑定信息
	 * @param companyId 公司ID 
	 * @param childHotelId 携程 子酒店ID
	 * @return
	 */
	List<CtripRoomTypeMapping> findRoomTypeMapping(String companyId,String childHotelId,String innId);
	
	
	/**
	 *  修改房型Mapping的房价代码
	 * @param ratePlanCode 新的房价代码
	 * @param mappingId 以前的mappingId
	 * @throws JAXBException 
	 * @throws RequestCtripException 
	 */
	void  updateMappingPlanCode(String companyId, String ratePlanCode,String planCodeName, String mappingId) throws JAXBException, RequestCtripException;
	
	
	
}
