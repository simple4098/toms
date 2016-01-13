package com.fanqielaile.toms.service;

import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.Result;

/**
 * DESC : 众荟推送房价 房态， 逻辑处理
 * @author : 番茄木-ZLin
 * @data : 2016/1/13
 * @version: v1.0.0
 */
public interface IJointWisdomARI {


    /**
     * 往众荟推送
     * @param mappingDto 映射对象
     * @param roomTypeInfo 房型对象 （库存 价格）
     * @param priceDto 价格
     * @param commission 佣金比
     */
    public Result updateJsPriceInventory(JointWisdomInnRoomMappingDto mappingDto,RoomTypeInfo roomTypeInfo,OtaRoomPriceDto priceDto,OtaCommissionPercentDto commission) throws Exception;
}
