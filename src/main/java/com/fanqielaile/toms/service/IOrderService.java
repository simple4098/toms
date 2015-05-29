package com.fanqielaile.toms.service;

import com.fanqie.core.dto.OrderSourceDto;
import com.fanqie.core.dto.ParamDto;

import java.util.Map;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/5/29
 * @version: v1.0.0
 */
public interface IOrderService {

    OrderSourceDto findOrderSourceDto(ParamDto paramDto)throws  Exception;

    Map<String,Object> findOrderSourceDetail(ParamDto paramDto) throws Exception;

}
