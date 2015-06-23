package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dao.IOtaTaoBaoAreaDao;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.model.Company;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.XHotel;
import com.taobao.api.request.XhotelAddRequest;

import javax.annotation.Resource;

/**
 * DESC : 淘宝对接 API
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
public interface ITBService {

    /**
     * 添加酒店
     * @param company
     * @param innDto
     * @return
     */
    XHotel hotelAdd(Company company,InnDto innDto);

    /**
     * 获取酒店
     * @param company
     * @param innDto
     * @return
     */
    XHotel hotelGet(Long hid,Company company,InnDto innDto);




}
