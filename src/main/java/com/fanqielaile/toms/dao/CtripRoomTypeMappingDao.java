package com.fanqielaile.toms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;




public interface CtripRoomTypeMappingDao {

	/**
	 *  删除之前的绑定关系
	 * @param innId 客栈ID
	 * @param ctripChildHotelId 携程子酒店ID
	 */
    void   updateRoomMappingDelete(@Param("innId")String innId,@Param("ctripChildHotelId")String ctripChildHotelId);
    
    
    /**
     *  添加新的记录
     * @param ctripRoomTypeMapping
     */
    void  saveRoomTypeMapping(CtripRoomTypeMapping ctripRoomTypeMapping);
    
    /**
     *  查询房型的绑定信息
     * @param companyId 公司ID
     * @param innId 携程的子酒店ID
     * @return
     */
    List<CtripRoomTypeMapping> findRoomTypeMapping(@Param("companyId")String companyId,@Param("childHotelId") String childHotelId);
    
  
}
