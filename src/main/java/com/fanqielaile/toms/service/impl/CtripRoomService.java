package com.fanqielaile.toms.service.impl;

import com.fanqie.bean.response.RequestResponse;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.CtripHttpClient;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.CtripRoomTypeMappingDao;
import com.fanqielaile.toms.dao.IOtaCommissionPercentDao;
import com.fanqielaile.toms.dao.IOtaRoomPriceDao;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaCommissionPercent;
import com.fanqielaile.toms.service.ICtripRoomService;
import com.fanqielaile.toms.support.tb.CtripXHotelUtil;
import com.fanqielaile.toms.support.util.FcUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private IOtaCommissionPercentDao commissionPercentDao;
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;
    @Resource
    private CtripRoomTypeMappingDao  ctripRoomTypeMappingDao;

    private static final Logger log = LoggerFactory.getLogger(CtripRoomService.class);

    @Override
    public RequestResponse updateRoomPrice(Company company, OtaInfoRefDto infoRefDto, List<CtripRoomTypeMapping> roomTypeMappingList,boolean isSj) {
        //佣金比例
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(
                new OtaCommissionPercent(company.getOtaId(), company.getId(), infoRefDto.getUsedPriceModel().name()));
        if (!CollectionUtils.isEmpty(roomTypeMappingList)){
            for (CtripRoomTypeMapping mapping:roomTypeMappingList){
                //价格增加价
                OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(),
                        Integer.valueOf(mapping.getTomRoomTypeId()),
                        infoRefDto.getOtaInfoId()));
                String room_type = DcUtil.omsRoomTypeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(),
                        Integer.valueOf(mapping.getInnId()), Integer.valueOf(mapping.getTomRoomTypeId()), CommonApi.checkRoom,60);
                Integer sj=0;
                try {
                    List<RoomDetail> roomDetailList = InnRoomHelper.getRoomDetail(room_type);
                    String requestRoomPriceXml = CtripXHotelUtil.requestRoomPriceXml(infoRefDto, mapping, roomDetailList,commission,priceDto, isSj);
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
                        sj = isSj?1:0;
                    }else {
                        log.info("房态和房价失败:" + mapping.getCtripChildRoomTypeId());
                    }
                    ctripRoomTypeMappingDao.updateMappingSj(roomTypeMappingList,sj);
                } catch (Exception e) {
                    log.error("异常:"+e);
                }

            }
        }
        return null;
    }


}
