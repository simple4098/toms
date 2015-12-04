package com.fanqielaile.toms.service;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.PushRoom;
import com.fanqielaile.toms.model.Result;

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
     * @param tbParam 绿番茄 参数(见接口文档)
     */
    void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo  ) throws Exception;

    /**
     * 定时更新淘宝上的房型信息
     * @param infoRefDto 渠道信息
     * @param tbParam 参数
     */
    void updateHotel(OtaInfoRefDto infoRefDto, TBParam tbParam  ) throws Exception;

    /**
     * 及时推送，oms修改房型价格 =>  要更新房型的库存集合
     * @param infoRefDto  渠道信息
     * @param pushRoomList 要更新房型价格 日期
     */
    void updateHotelRoom(OtaInfoRefDto infoRefDto, List<PushRoom> pushRoomList)throws Exception;

    /**
     * 增减价后推送到卖房网站上去
     * @param infoRefDto 渠道信息
     * @param roomPriceDto 房型价格对象
     */
    void updateRoomTypePrice(OtaInfoRefDto infoRefDto, OtaRoomPriceDto roomPriceDto)throws Exception;

    /**
     * 定时推送失败的 客栈数据
     * @param infoRefDto 渠道信息
     */
    void updateHotelFailTimer(OtaInfoRefDto infoRefDto);

    /**
     * 增减价后推送到卖房网站上去
     * @param infoRefDto  渠道信息
     * @param innId 客栈id
     * @param json  同步房型与时间
     */
    void updateRoomTypePrice(OtaInfoRefDto infoRefDto, String innId,String companyId,String userId,String json)throws Exception;

    /**
     * 验证OTA渠道输入AppKey
     * AppSecret
     * SessionKey 的准确性
     * @param infoRefDto
     */
    Result validatedOTAAccuracy(OtaInfoRefDto infoRefDto);
}
