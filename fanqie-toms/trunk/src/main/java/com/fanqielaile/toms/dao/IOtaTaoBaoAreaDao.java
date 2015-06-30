package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.OtaTaoBaoArea;
import org.apache.ibatis.annotations.Param;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public interface IOtaTaoBaoAreaDao {

    OtaTaoBaoArea findCityAndArea(String name);

    OtaTaoBaoArea findCountyAndCity(@Param("cityCode")String cityCode, @Param("county")String county);
}
