package com.fanqielaile.toms.service;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.support.util.JsonModel;
import com.taobao.api.domain.XHotel;
import com.tomato.log.model.BusinLog;

import java.io.IOException;

/**
 * DESC : 淘宝对接 API
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
public interface ITBService {

    /**
     * 添加/更新酒店
     * @param tbParam 绿番茄传过来的参数
     */
    void updateOrAddHotel(TBParam tbParam, BusinLog businLog) throws Exception;

    /**
     * 从淘宝下架、解除绑定
     * @param tbParam
     */
    void deleteHotel(TBParam tbParam, BusinLog businLog) throws Exception;
}
