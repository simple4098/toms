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
    
    /**
     *  通过主键查询记录
     * @param id
     * @return
     */
    CtripRoomTypeMapping findById(@Param("id")Integer id);
    
    /**
     *  修改Mapping的房价计划
     * @param id 主键ID
     * @param code 新的计划编号
     * @param codeName 新的计划名称
     */
    void  updateMappingRatePlanCode(@Param("id")String id,@Param("code")String code,@Param("codeName")String codeName);

    /**
     * 根据携程酒店id，房型id查询
     * @param hotelId
     * @param roomTypeId
     * @return
     */
    CtripRoomTypeMapping selectRoomTypeByHotelIdAndRoomTypeId(@Param("hotelId") String hotelId, @Param("roomTypeId") String roomTypeId);


    /**
     * 更新携程渠道 上下状态。
     * @param mappingList 要更新的集合
     */
    void updateMappingSj(@Param("mappingList") List<CtripRoomTypeMapping>  mappingList, @Param("sj")Integer sj);
}
