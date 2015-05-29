package com.fanqielaile.toms.service;

import com.fanqie.core.dto.CustomerDto;
import com.fanqie.core.dto.ParamDto;

import java.util.List;

/**
 * DESC : 运营数据service
 * @author : 番茄木-ZLin
 * @data : 2015/5/29
 * @version: v1.0.0
 */
public interface IOperateTrendService {

    CustomerDto findCustomer(ParamDto paramDto)throws Exception;

}
