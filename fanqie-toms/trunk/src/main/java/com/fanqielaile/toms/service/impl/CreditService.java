package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.PushRoom;
import com.fanqielaile.toms.dto.RoomStatusDetail;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.service.ICreditService;
import com.fanqielaile.toms.service.ITPService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/3/15
 * @version: v1.0.0
 */
@Service("creditService")
public class CreditService implements ITPService {
    @Resource
    private TBService tbService;

    @Override
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
        tbService.updateOrAddHotel(tbParam,otaInfo);

    }

    @Override
    public void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
        tbService.deleteHotel(tbParam,otaInfo);
    }

    @Override
    public void updateHotel(OtaInfoRefDto infoRefDto, TBParam tbParam) throws Exception {
        tbService.updateHotel(infoRefDto,tbParam);
    }

    @Override
    public void updateHotelRoom(OtaInfoRefDto infoRefDto, List<PushRoom> pushRoomList) throws Exception {

        tbService.updateHotelRoom(infoRefDto,pushRoomList);
    }

    @Override
    public void updateHotelFailTimer(OtaInfoRefDto infoRefDto) {

        tbService.updateHotelFailTimer(infoRefDto);
    }

    @Override
    public void updateRoomTypePrice(OtaInfoRefDto infoRefDto, String innId, String companyId, String userId, String json) throws Exception {
        tbService.updateRoomTypePrice(infoRefDto,innId,companyId,userId,json);
    }

    @Override
    public Result validatedOTAAccuracy(OtaInfoRefDto infoRefDto) {
        return tbService.validatedOTAAccuracy(infoRefDto);
    }

    @Override
    public void sellingRoomType(String from, String to, OtaInfoRefDto otaInfoRefDto) {
        tbService.sellingRoomType(from,to,otaInfoRefDto);
    }
}
