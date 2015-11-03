package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;

import java.io.IOException;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/8/19
 * @version: v1.0.0
 */
public interface IOtaRoomPriceService {

    List<RoomTypeInfo> obtOmsRoomInfo( BangInn bangInn) throws Exception;

    List<RoomTypeInfo> obtOmsRoomInfoToFc( BangInn bangInn) throws Exception;

    OtaRoomPriceDto findRoomPrice(OtaRoomPriceDto roomPriceDto);

    void saveRoomPriceDto(OtaRoomPriceDto roomPriceDto,  BangInn bangInn)throws Exception;

    //获取oms具体某一个房型数据
    List<RoomDetail> obtRoomAvailFc(BangInn bangInn,Integer roomTypeId)throws Exception;
    List<RoomDetail> obtRoomAvailTb(BangInn bangInn,Integer roomTypeId)throws Exception;
}
