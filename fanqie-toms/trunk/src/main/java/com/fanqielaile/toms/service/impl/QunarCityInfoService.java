package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.QunarCityInfoDao;
import com.fanqielaile.toms.model.qunar.QunarCityInfo;
import com.fanqielaile.toms.service.IQunarCityInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2016/4/15.
 */
@Service
public class QunarCityInfoService implements IQunarCityInfoService {
    @Resource
    private QunarCityInfoDao qunarCityInfoDao;

    @Override
    public QunarCityInfo findQunarCityInfoByName(String name) {
        return this.qunarCityInfoDao.selectQunarCityInfoByName(name);
    }
}
