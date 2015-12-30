package com.fanqielaile.toms.service;


import java.util.List;

import javax.xml.bind.JAXBException;

import com.fanqie.bean.response.CtripHotelRoomType;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.dto.fc.MatchRoomType;
import com.fanqielaile.toms.exception.CtripDataException;
import com.fanqielaile.toms.exception.RequestCtripException;


public interface CtripHotelRoomTypeService {

	
	/**
	 *  查询酒店的所有房型
	 * @param ctripParentHotelId 母酒店Id
	 * @return
	 */
	 List<CtripHotelRoomType> findByCtripParentHotelId(String ctripParentHotelId);
	 
	 /**
	  *  更新房型绑定关系
	  * @param companyId 公司ID
	  * @param json 新的关系
	  * @param innId 客栈ID
	  * @param ctripMasterHotelId 携程酒店Id
	  */
	  List<CtripRoomTypeMapping>  updateRoomBypeRelation(String companyId, String json,String innId,String ctripMasterHotelId)  throws RequestCtripException, JAXBException ,CtripDataException ;
	  
	  /**
	   *  删除之前的绑定的关系，并且删除携程关系
	   * @param childHotelId 子房型ID
	   * @param innId  自己的酒店Id
	   * @param companyId 公司ID
	   */
	  void  deletePreviousRoomMapping(String childHotelId,String innId,String companyId) throws RequestCtripException, JAXBException ;
	  
	  
	  
	  /**
	   *	删除携程关系
	   * @param childHotelId 子房型ID
	   * @param companyId 公司Id
	   */
	  void  deleteCtripRoomMapping(String childHotelId,String companyId) throws RequestCtripException, JAXBException ;
	  
	  /**
	   *  新增房型关系
	   * @param innId 客栈ID
	   * @param ctripMasterHotelId 携程母酒店ID
	   * @param matchRoomType 新的关系对象
	   * @param companyId 公司Id
	   * @throws RequestCtripException
	   */
	  void  addNewRoomTypeMappingToCtrip(String innId, String ctripMasterHotelId,
				MatchRoomType matchRoomType,String companyId)  throws RequestCtripException  ,JAXBException ;
	  
	  
	/**
	 *  保存关系
	 * @param crms
	 */
	  String  saveMapping(List<CtripRoomTypeMapping> crms,String ctripMasterHotelId) ;
	  
	  /**
	   *  取消所有的绑定关系
	   * @param companyId 公司ID
	   * @param innId 客栈id
	   * @param masterHotelId 携程母ID
	 * @throws RequestCtripException 
	 * @throws JAXBException 
	   */
	  void  cannelMappingAll(String companyId,String innId,String masterHotelId) throws RequestCtripException, JAXBException;
	  
	  
		 

}
