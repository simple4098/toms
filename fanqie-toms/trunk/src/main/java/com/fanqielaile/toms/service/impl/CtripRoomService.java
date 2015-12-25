package com.fanqielaile.toms.service.impl;

import com.fanqie.bean.response.RequestResponse;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.CtripHttpClient;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.service.ICtripRoomService;
import com.fanqielaile.toms.support.tb.CtripXHotelUtil;
import com.fanqielaile.toms.support.util.FcUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/12/25
 * @version: v1.0.0
 */
@Service
public class CtripRoomService implements ICtripRoomService {

    private static final Logger log = LoggerFactory.getLogger(CtripRoomService.class);

    @Override
    public RequestResponse updateRoomPrice(Company company, OtaInfoRefDto infoRefDto, List<CtripRoomTypeMapping> roomTypeMappingList,boolean isSj) {

        if (!CollectionUtils.isEmpty(roomTypeMappingList)){
            for (CtripRoomTypeMapping mapping:roomTypeMappingList){
                String room_type = DcUtil.omsFcRoomTYpeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(),
                        Integer.valueOf(mapping.getInnId()), Integer.valueOf(mapping.getTomRoomTypeId()), CommonApi.checkRoom);
                try {
                    List<RoomDetail> roomDetailList = InnRoomHelper.getRoomDetail(room_type);
                    String requestRoomPriceXml = CtripXHotelUtil.requestRoomPriceXml(infoRefDto, mapping, roomDetailList, isSj);
                    String requestSetRoomInfoXml = CtripXHotelUtil.requestSetRoomInfoXml(infoRefDto, mapping, roomDetailList);
                    String execute = CtripHttpClient.execute(requestRoomPriceXml);
                    String roomInfoExecute = CtripHttpClient.execute(requestSetRoomInfoXml);
                    RequestResponse response = FcUtil.xMLStringToBean(execute);
                    RequestResponse roomInfoResponse = FcUtil.xMLStringToBean(roomInfoExecute);
                    Integer resultCode = response.getRequestResult().getResultCode();
                    Integer roomInfoCode = roomInfoResponse.getRequestResult().getResultCode();
                    if (CtripConstants.resultCode.equals(resultCode) && CtripConstants.resultCode.equals(roomInfoCode)){
                        log.info(" 价格同步成功:" + response.getRequestResult().getMessage() +
                                " 房态成功:" + roomInfoResponse.getRequestResult().getMessage() +
                                " 携程房型id" + mapping.getCtripChildRoomTypeId());
                    }else {
                        log.info("房态和房价成功:" + mapping.getCtripChildRoomTypeId());
                    }
                } catch (Exception e) {
                    log.error("异常:"+e);
                }

            }
        }
        return null;
    }


}
