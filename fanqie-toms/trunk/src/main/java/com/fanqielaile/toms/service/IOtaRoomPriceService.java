package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.BangInn;

import java.io.IOException;
import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/8/19
 * @version: v1.0.0
 */
public interface IOtaRoomPriceService {

    List<RoomTypeInfo> obtOmsRoomInfo( BangInn bangInn,String companyId) throws Exception;
}
