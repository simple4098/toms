package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.qunar.QunarCityInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wangdayin on 2016/4/15.
 */
public interface QunarCityInfoDao {
    QunarCityInfo selectQunarCityInfoByName(@Param("name") String name);
}
