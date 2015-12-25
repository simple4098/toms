package com.fanqielaile.toms.dao;


import com.fanqie.bean.response.CtripHotelInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CtripHotelInfoDao {

    int insert(CtripHotelInfo ctripHotelInfo);
    
    List<CtripHotelInfo> findByHotelName(@Param("hotelName")String  hotelName);
    
    List<CtripHotelInfo> findHotelInfoByPage(@Param("hotelName") String hotelName, PageBounds pageBounds);
    
    CtripHotelInfo findByParentHotelId(@Param("parentHotelId") String parentHotelId);

    /**
     * 保存携程酒店信息
     * @param ctripHotelInfo 携程酒店对象
     */
    void saveHotelInfo(CtripHotelInfo ctripHotelInfo);

    /**
     * 更新携程酒店信息
     *
     * @param ctripHotelInfo
     */
    void updateHotelInfo(CtripHotelInfo ctripHotelInfo);
  
}
