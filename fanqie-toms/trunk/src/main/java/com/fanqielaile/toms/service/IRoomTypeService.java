package com.fanqielaile.toms.service;

import com.fanqie.core.dto.ParamDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC : 第三方公司 查看房态房量
 * @author : 番茄木-ZLin
 * @data : 2015/6/3
 * @version: v1.0.0
 */
public interface IRoomTypeService {

    /**
     * 请求接口查询第三方的房态房量
     * @param paramDto 查询参数
     * @param userInfo 当前用户信息
     */
    RoomTypeInfoDto  findRoomType(ParamDto paramDto,UserInfo userInfo)throws Exception;
}
