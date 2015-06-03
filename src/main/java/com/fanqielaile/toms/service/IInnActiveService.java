package com.fanqielaile.toms.service;

import com.fanqie.core.dto.ParamDto;
import com.fanqielaile.toms.dto.ActiveInnDto;
import com.fanqielaile.toms.model.UserInfo;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/1
 * @version: v1.0.0
 */
public interface IInnActiveService {

    /**
     * 查询客栈活跃列表
     * @param paramDto 查询参数
     * @param userInfo 当前用户信息
     */
    ActiveInnDto findActiveInnDto(ParamDto paramDto,UserInfo userInfo)throws  Exception;
}
