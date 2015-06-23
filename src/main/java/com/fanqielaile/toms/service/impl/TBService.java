package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.IOtaTaoBaoAreaDao;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.service.ITBService;
import com.taobao.api.domain.XHotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * DESC : 添加/获取/更新 酒店
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
@Service
public class TBService implements ITBService {
    private static  final Logger log = LoggerFactory.getLogger(TBService.class);
    @Resource
    private IOtaTaoBaoAreaDao taoBaoAreaDao;
    private static  final  String url = "http://gw.api.tbsandbox.com/router/rest";

    /**
     * 想淘宝添加酒店
     * @param company 公司对象，存放 appKey ，appSecret ... 信息
     * @param innDto 客栈信息
     */
    @Override
    public  XHotel  hotelAdd(Company company,InnDto innDto){

        return null;
    }

    @Override
    public XHotel hotelGet(Long hid,Company company, InnDto innDto) {

        return null;
    }
}
