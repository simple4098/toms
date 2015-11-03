package com.fanqielaile.toms.service;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.PushRoom;

import java.util.List;


/**
 * DESC : 对接接口
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
public interface ITPService {

    /**
     * 添加/更新酒店
     * @param tbParam 绿番茄传过来的参数
     */
    void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception;
    /**
     * 从淘宝下架、解除绑定
     * @param tbParam
     */
    void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo  ) throws Exception;
    /**
     * 定时更新淘宝上的房型信息
     */
    void updateHotel(OtaInfoRefDto o, TBParam tbParam  ) throws Exception;

    /**
     * @param pushRoomList 获取oms 要更新房型的库存集合
     */
    void updateHotelRoom(OtaInfoRefDto o, List<PushRoom> pushRoomList)throws Exception;

    void updateRoomTypePrice(OtaInfoRefDto o, OtaRoomPriceDto roomPriceDto)throws Exception;

    /**
     * 定时推送失败的 客栈数据
     * @param o
     */
    void updateHotelFailTimer(OtaInfoRefDto o);

    /**
     * @param infoRefDto
     * @param innId
     * @param json  同步房型与时间
     */
    void updateRoomTypePrice(OtaInfoRefDto infoRefDto, String innId,String companyId,String userId,String json)throws Exception;
}
