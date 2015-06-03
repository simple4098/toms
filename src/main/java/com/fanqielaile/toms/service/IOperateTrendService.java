package com.fanqielaile.toms.service;

import com.fanqie.core.domain.OperateTrend;
import com.fanqie.core.dto.CustomerDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqielaile.toms.model.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * DESC : 运营数据service
 * @author : 番茄木-ZLin
 * @data : 2015/5/29
 * @version: v1.0.0
 */
public interface IOperateTrendService {

    /**
     * 客户资料分布查询
     */
    CustomerDto findCustomer(ParamDto paramDto,UserInfo userInfo)throws Exception;

    /**
     * 查询运营概况数据
     * @param paramDto 查询参数
     * @param userInfo 当前登录用户
     */
    OperateTrend findOperateTrend(ParamDto paramDto,UserInfo userInfo)throws Exception;

    Map<String,Object> findQsDetail(ParamDto paramDto,UserInfo userInfo) throws  Exception;

}
