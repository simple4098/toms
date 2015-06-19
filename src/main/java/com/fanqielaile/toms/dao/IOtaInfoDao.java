package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.OtaInfo;

import java.util.List;

/**
 * DESC : 渠道
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public interface IOtaInfoDao {

    List<OtaInfo> findCityAndArea(String name);
}
