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
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.JwXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.opentravel.ota._2003._05.OTAHotelInvCountNotifRS;
import org.opentravel.ota._2003._05.OTAHotelRatePlanNotifRS;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2016/1/13
 * @version: v1.0.0
 */
@Service
public class JointWisdomARI implements IJointWisdomARI {



    @Override
    public Result updateJsPriceInventory(JointWisdomInnRoomMappingDto mappingDto, RoomTypeInfo roomTypeInfo,
                                       OtaRoomPriceDto priceDto, OtaCommissionPercentDto commission) {
        Result result = new Result();
        if (roomTypeInfo!=null){
            RoomPrice roomPrice = JwXHotelUtil.buildRoomPrice(mappingDto,roomTypeInfo,priceDto,commission);
            Inventory inventory = JwXHotelUtil.inventory(mappingDto, roomTypeInfo);
            try {
                OTAHotelRatePlanNotifRS otaHotelRatePlanNotifRS = JointWisdomARIUtils.pushRoomPrice(roomPrice);
                OTAHotelInvCountNotifRS otaHotelInvCountNotifRS = JointWisdomARIUtils.pushRoomInventory(inventory);
                List<Object> refsOrSuccess = null;
                List<Object> successOrWarnings = null;
                if (otaHotelRatePlanNotifRS!=null && otaHotelInvCountNotifRS!=null ){
                    refsOrSuccess = otaHotelRatePlanNotifRS.getErrorsOrRatePlanCrossRefsOrSuccess();
                    successOrWarnings = otaHotelInvCountNotifRS.getErrorsOrSuccessOrWarnings();
                    if (refsOrSuccess==null && successOrWarnings==null){
                        result.setStatus(Constants.SUCCESS200);
                    }else {

                        if (!CollectionUtils.isEmpty(refsOrSuccess) ){
                            throw new TomsRuntimeException(" 推送价格异常 ");
                        }
                        if (!CollectionUtils.isEmpty(successOrWarnings)){
                            throw new TomsRuntimeException(" 推送库存异常 ");
                        }
                        result.setStatus(Constants.ERROR400);
                    }
                }
            } catch (Exception e) {
                throw new TomsRuntimeException(e.getMessage());
            }
        }
        return result;
    }
}
