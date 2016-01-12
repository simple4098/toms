package com.fanqielaile.toms.dao;

import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;

import java.util.List;

/**
 * DESC : 众荟房型映射
 * @author : 番茄木-ZLin
 * @data : 2016/1/11
 * @version: v1.0.0
 */
public interface IJointWisdomInnRoomDao {

    List<JointWisdomInnRoomMappingDto> selectJsRoomMapping();

}
