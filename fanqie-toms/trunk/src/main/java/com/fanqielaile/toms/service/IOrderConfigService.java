package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.OrderConfigDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.model.OrderConfig;
import com.fanqielaile.toms.model.UserInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.util.List;

/**
 * DESC : 客栈订单配置
 * @author : 番茄木-ZLin
 * @data : 2015/8/18
 * @version: v1.0.0
 */
public interface IOrderConfigService {


    List<OrderConfigDto> findOrderConfigByCompanyId( List<OtaInfoRefDto> list,UserInfo userInfo, PageBounds pageBounds );

    List<OrderConfigDto> findOrderConfigByCompanyIdAndInnId(String companyId, String innId);

    void saveOrderConfig(List<OrderConfigDto> list,String innId, String companyId)throws Exception;

}
