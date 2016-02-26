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
    void saveRoomTypeMapping(CtripRoomTypeMapping ctripRoomTypeMapping);
    
    /**
     *  查询房型的绑定信息
     * @param companyId 公司ID
     * @param innId 携程的子酒店ID
     * @return
     */
    List<CtripRoomTypeMapping> findRoomTypeMapping(@Param("companyId")String companyId,@Param("childHotelId") String childHotelId,@Param("innId") String innId);
    
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
     * @param mapping 更新的房型映射
     */
    void updateMappingSj(CtripRoomTypeMapping mapping);

    /**
     * 根据番茄 客栈id和房型id 查询映射关系
     * @param innId 客栈id
     * @param roomTypeId 房型id
     */
    CtripRoomTypeMapping selectMappingInnIdAndRoomTypeId(@Param("innId")String innId, @Param("roomTypeId")String roomTypeId);

    /**
     *  查询公司上架携程渠道的
     * @param ctripRoomTypeMapping 查询对象
     */
    List<CtripRoomTypeMapping> selectMapping(CtripRoomTypeMapping ctripRoomTypeMapping );
    
    /**
     *  根据ID删除记录
     * @param id
     */
    void  deleteById(@Param("id")String id);
    
    /**
     *  根据携程子酒店ID  和  子房型ID查询
     * @param childHotelId
     * @param ctripRoomId
     * @return
     */
    CtripRoomTypeMapping  selectByCtripHotelIdAndRoomId(@Param("childHotelId")String childHotelId, @Param("ctripRoomId")String ctripRoomId,@Param("companyId")String companyId);


    /**
     *  查询所有的携程房型Mapping关系
     * @return
     */
    List<CtripRoomTypeMapping> findAll();
    
    
    /**
     *  临时方法,用于修改已经Mappingg过的房型。但是传入的酒店ID和房型ID未加OTA_前缀，
     *  及其我们自己的价格计划
     */
    void  updateNewCode(@Param("id")String id,@Param("innCode")String innCode,@Param("roomTypeCode")String roomTypeCode);
}
