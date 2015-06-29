package com.fanqielaile.toms.service;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.support.util.JsonModel;
import com.taobao.api.domain.XHotel;

import java.io.IOException;

/**
 * DESC : 淘宝对接 API
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
public interface ITBService {

    /**
     * 添加酒店
     * @param tbParam 绿番茄传过来的参数
     */
    JsonModel hotelAddOrUpdate(TBParam tbParam) throws IOException;

    /**
     * 获取酒店
     * @param company
     * @param innDto
     * @return
     */
    XHotel hotelGet(Long hid,Company company,InnDto innDto);




}
