package com.fanqielaile.toms.service;

import com.fanqie.bean.response.RequestResponse;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.model.Company;

import java.util.List;

/**
 * DESC : 携程处理房态service
 * @author : 番茄木-ZLin
 * @data : 2015/12/25
 * @version: v1.0.0
 */
public interface ICtripRoomService {

    /**
     * 更新房型的价格、房态 信息
     * @param company 公司对象
     * @param infoRefDto 渠道信息
     * @param roomTypeMappingList 房型mapping
     * @param isSj 是否上架，true 上架  false 下架
     */
    RequestResponse updateRoomPrice(Company company,OtaInfoRefDto infoRefDto,List<CtripRoomTypeMapping> roomTypeMappingList,boolean isSj);
}
