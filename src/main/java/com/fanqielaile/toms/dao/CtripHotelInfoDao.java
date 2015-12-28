package com.fanqielaile.toms.dao;


import com.fanqie.bean.response.CtripHotelInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CtripHotelInfoDao {

    int insert(CtripHotelInfo ctripHotelInfo);
    
    List<CtripHotelInfo> findByHotelName(@Param("hotelName")String  hotelName);
    
    List<CtripHotelInfo> findHotelInfoByPage(@Param("hotelName") String hotelName, PageBounds pageBounds);
    
    /**
     * 通过母酒店ID 查询酒店信息
     * @param parentHotelId
     * @return
     */
    CtripHotelInfo findByParentHotelId(@Param("parentHotelId") String parentHotelId);
    
    /**
     *  通过子酒店ID 查询酒店信息
     * @param parentHotelId 
     * @return
     */
    CtripHotelInfo findByChildHotelId(@Param("childHotelId") String childHotelId);
    
    /**
     * 保存携程酒店信息
     * @param ctripHotelInfo 携程酒店对象
     */
    void saveHotelInfo(CtripHotelInfo ctripHotelInfo);
    
    /**
     *  修改子酒店ID
     * @param childHotelId 子酒店ID
     * @param parentHotelId 母酒店ID
     */
    void updateChildHotelId(@Param("childHotelId")String childHotelId,@Param("parentHotelId")String parentHotelId);
    

    /**
     * 更新携程酒店信息
     *
     * @param ctripHotelInfo
     */
    void updateHotelInfo(CtripHotelInfo ctripHotelInfo);
  
}
