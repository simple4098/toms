package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.fc.FcCity;
import org.apache.ibatis.annotations.Param;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/8/12
 * @version: v1.0.0
 */
public interface IFcCityDao {

    void saveFcCity(FcCity fcCity);

    FcCity selectFcCityByName(@Param("cityName")String cityName);
}
