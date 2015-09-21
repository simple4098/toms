package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.FcHotelInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/11
 * @version: v2.2.0
 */
public interface IFcHotelInfoDao {

    /**
     * 查询房仓酒店
     * @param infoDto 查询酒店参数
     */
    List<FcHotelInfoDto> selectFcHotel(FcHotelInfoDto infoDto);

    /**
     * 创建酒店基本信息
     *
     * @param fcHotelInfoDto
     */
    void insertFcHotelInfo(FcHotelInfoDto fcHotelInfoDto);

    FcHotelInfoDto selectFcHotelByHotelId(@Param("hotelId")String hotelId);

    /**
     * 根据天下房仓酒店ID查询酒店
     *
     * @param fcHotelId
     * @return
     */
    FcHotelInfoDto selectFcHotelInfoByFcHotelId(String fcHotelId);

    /**
     * 更新天下房仓酒店信息
     *
     * @param fcHotelInfoDto
     */
    void updateFcHotelInfo(FcHotelInfoDto fcHotelInfoDto);
}
