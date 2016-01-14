package com.fanqielaile.toms.service.impl;

import com.fanqie.jw.dto.Inventory;
import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqie.jw.dto.RoomPrice;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.service.IJointWisdomARI;
import com.fanqielaile.toms.support.JointWisdomARIUtils;
import com.fanqielaile.toms.support.tb.JwXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.OTAHotelInvCountNotifRS;
import org.opentravel.ota._2003._05.OTAHotelRatePlanNotifRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/1/13
 * @version: v1.0.0
 */
@Service
public class JointWisdomARI implements IJointWisdomARI {
    private static Logger logger = LoggerFactory.getLogger(JointWisdomARI.class);


    @Override
    public Result updateJsPriceInventory(JointWisdomInnRoomMappingDto mappingDto, RoomTypeInfo roomTypeInfo,
                                       OtaRoomPriceDto priceDto, OtaCommissionPercentDto commission) throws Exception{
        Result result = new Result();
        if (roomTypeInfo!=null){
            RoomPrice roomPrice = JwXHotelUtil.buildRoomPrice(mappingDto,roomTypeInfo,priceDto,commission);
            Inventory inventory = JwXHotelUtil.inventory(mappingDto, roomTypeInfo);
                OTAHotelRatePlanNotifRS otaHotelRatePlanNotifRS = JointWisdomARIUtils.pushRoomPrice(roomPrice);
                OTAHotelInvCountNotifRS otaHotelInvCountNotifRS = JointWisdomARIUtils.pushRoomInventory(inventory);
                List<Object> refsOrSuccess = null;
                List<Object> successOrWarnings = null;
                if (otaHotelRatePlanNotifRS!=null && otaHotelInvCountNotifRS!=null ){
                    refsOrSuccess = otaHotelRatePlanNotifRS.getErrorsOrRatePlanCrossRefsOrSuccess();
                    successOrWarnings = otaHotelInvCountNotifRS.getErrorsOrSuccessOrWarnings();
                    if (CollectionUtils.isEmpty(refsOrSuccess) && CollectionUtils.isEmpty(successOrWarnings)){
                        logger.info("=====众荟成功=====");
                        result.setStatus(Constants.SUCCESS200);
                    }else {
                        if (!CollectionUtils.isEmpty(refsOrSuccess) ){
                            logger.error("=====推送价格异常=====");
                        }
                        if (!CollectionUtils.isEmpty(successOrWarnings)){
                            logger.error("=====推送库存异常=====");

                        }
                        result.setStatus(Constants.ERROR400);
                    }
                }
        }
        return result;
    }
}
