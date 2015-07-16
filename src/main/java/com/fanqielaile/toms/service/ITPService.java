package com.fanqielaile.toms.service;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dto.OtaInfoDto;
import com.fanqielaile.toms.model.OtaInfo;
import com.tomato.log.model.BusinLog;

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
    void updateOrAddHotel(TBParam tbParam, OtaInfo otaInfo, BusinLog businLog) throws Exception;



    /**
     * 从淘宝下架、解除绑定
     * @param tbParam
     */
    void deleteHotel(TBParam tbParam, OtaInfo otaInfo, BusinLog businLog) throws Exception;

    /**
     * 定时更新淘宝上的房型信息
     */
    void updateHotel(OtaInfoDto o, TBParam tbParam, BusinLog businLog) throws Exception;
}
