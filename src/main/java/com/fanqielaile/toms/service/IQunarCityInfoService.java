package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.qunar.QunarCityInfo;

/**
 * Created by wangdayin on 2016/4/15.
 * 去哪儿城市信息
 */
public interface IQunarCityInfoService {
    /**
     * 通过城市名称查询city_code
     *
     * @param name
     * @return
     */
    QunarCityInfo findQunarCityInfoByName(String name);
}
