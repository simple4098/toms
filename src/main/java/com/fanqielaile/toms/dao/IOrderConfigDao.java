package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OrderConfigDto;
import com.fanqielaile.toms.model.OrderConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/8/18
 * @version: v1.0.0
 */
public interface IOrderConfigDao {

    List<OrderConfig> selectOrderConfig(OrderConfig orderConfig);

    OrderConfigDto selectOrderConfigByOtaInfoId(OrderConfig orderConfig);
}
