package com.fanqielaile.toms.service;

import com.fanqie.core.dto.ParamDto;
import com.fanqielaile.toms.model.UserInfo;

/**
 * DESC : 第三方公司 查看房态房量
 * @author : 番茄木-ZLin
 * @data : 2015/6/3
 * @version: v1.0.0
 */
public interface IRoomTypeService {

    void findRoomType(ParamDto paramDto,UserInfo userInfo);
}
