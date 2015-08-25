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

    OtaBangInnRoomDto selectBangInnRoomByInnIdAndRoomTypeId(@Param("innId") int innId, @Param("roomTypeId") int roomTypeId, @Param("companyId") String companyId);

    OtaBangInnRoomDto findOtaBangInnRoom(@Param("wgOtaId")String id, @Param("roomTypeId")Integer roomTypeId);

    //删除客栈房型关系
    void deletedBangInnRoom(@Param("wgOtaId")String otaWgId);
    //根据rid companyId 查询
    OtaBangInnRoomDto selectBangInnRoomByRidAndCompanyId(@Param("rid")String rid, @Param("companyId")String CompanyId);

    /**
     * 通过Rid查询房型信息
     *
     * @param rId
     * @return
     */
    OtaBangInnRoomDto selectOtaBangInnRoomByRid(@Param("rId") String rId);
}
