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

    ActiveInnDto findActiveInnDto(ParamDto paramDto,UserInfo userInfo)throws  Exception;
}
