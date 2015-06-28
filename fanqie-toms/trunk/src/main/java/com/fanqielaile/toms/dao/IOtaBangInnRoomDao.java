package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtaBangInnRoomDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
public interface IOtaBangInnRoomDao {

    void saveBangInnRoom(OtaBangInnRoomDto otaBangInnRoomDto);

    List<OtaBangInnRoomDto> selectBangInnRoomByInnIdAndRoomTypeId(@Param("innId") int innId, @Param("roomTypeId") int roomTypeId);

    OtaBangInnRoomDto findOtaBangInnRoom(@Param("wgOtaId")String id, @Param("roomTypeId")Integer roomTypeId);
}
