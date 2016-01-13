package com.fanqielaile.toms.dao;

import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC : 众荟房型映射
 * @author : 番茄木-ZLin
 * @data : 2016/1/11
 * @version: v1.0.0
 */
public interface IJointWisdomInnRoomDao {

    List<JointWisdomInnRoomMappingDto> selectJsRoomMapping();

    /**
     * 查询众荟的客栈房型关联关系
     * @param innId 客栈id
     * @param roomTypeId 房型id
     * @return
     */
    JointWisdomInnRoomMappingDto selectJsRoomInnRooType(@Param("innId")Integer innId, @Param("roomTypeId")Integer roomTypeId);

    void  insertJsRoomInnRooType(JointWisdomInnRoomMappingDto jointWisdomInnRoomMappingDto);
}
