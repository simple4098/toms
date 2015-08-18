package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.PushRoom;
import com.fanqielaile.toms.service.ITPService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DESC : 美团对接实现类
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
@Service("mtService")
public class MTService implements ITPService {


    @Override
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {

    }

    @Override
    public void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {

    }

    @Override
    public void updateHotel(OtaInfoRefDto o, TBParam tbParam) throws Exception {

    }

    @Override
    public void updateHotelRoom(OtaInfoRefDto o, List<PushRoom> pushRoomList) throws Exception {

    }
}
