package com.fanqielaile.toms.service.impl;

import com.fanqie.jw.dto.HotelRoomAvail;
import com.fanqie.jw.dto.Inventory;
import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqie.jw.dto.RoomPrice;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.zh.JointWisdomMappingDto;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.service.IJointWisdomARI;
import com.fanqielaile.toms.support.JointWisdomARIUtils;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.JwXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.OTAHotelAvailNotifRS;
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
            if (CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
                throw new TomsRuntimeException("oms 获取房型为空 ");
            }
            String sj = mappingDto.getSj()==1?"上架":"下架";
            RoomPrice roomPrice = JwXHotelUtil.buildRoomPrice(mappingDto,roomTypeInfo,priceDto,commission);
            List<RoomPrice> roomPrices = new ArrayList<>();
            roomPrices.add(roomPrice);
            Inventory inventory = JwXHotelUtil.inventory(mappingDto, roomTypeInfo);
            List<Inventory> inventories = new ArrayList<>();
            inventories.add(inventory);
            OTAHotelRatePlanNotifRS otaHotelRatePlanNotifRS = JointWisdomARIUtils.pushRoomPrice(roomPrices);
            OTAHotelInvCountNotifRS otaHotelInvCountNotifRS = JointWisdomARIUtils.pushRoomInventory(inventories);
            List<Object> refsOrSuccess = null;
            List<Object> successOrWarnings = null;
            if (otaHotelRatePlanNotifRS!=null && otaHotelInvCountNotifRS!=null ){
                refsOrSuccess = otaHotelRatePlanNotifRS.getErrorsOrRatePlanCrossRefsOrSuccess();
                successOrWarnings = otaHotelInvCountNotifRS.getErrorsOrSuccessOrWarnings();
                if (CollectionUtils.isEmpty(refsOrSuccess) && CollectionUtils.isEmpty(successOrWarnings)){
                    //房型上下架
                    Result status = xjRoomStatus(mappingDto, roomTypeInfo);
                    logger.info(sj+status.getStatus()+"=====众荟成功=====");
                    if (Constants.SUCCESS200 == status.getStatus()){
                        result.setStatus(Constants.SUCCESS200);
                    }else {
                        result.setStatus(Constants.ERROR400);
                    }
                }else {
                    if (!CollectionUtils.isEmpty(refsOrSuccess) ){
                        logger.error(sj+"=====推送价格异常=====");
                        result.setMessage(sj+"=====推送价格异常=====");
                    }
                    if (!CollectionUtils.isEmpty(successOrWarnings)){
                        logger.error(sj+"=====推送库存异常=====");
                        result.setMessage(sj+"=====推送库存异常=====");

                    }
                    result.setStatus(Constants.ERROR400);
                }
            }
        }
        return result;
    }

    @Override
    public Result xjRoomStatus(JointWisdomInnRoomMappingDto mappingDto, RoomTypeInfo roomTypeInfo) {
        HotelRoomAvail hotelRoomAvail = JwXHotelUtil.hotelRoomAvail(mappingDto, roomTypeInfo);
        List<HotelRoomAvail> list = new ArrayList<>();
        list.add(hotelRoomAvail);
        Result result = new Result();
        try {
            OTAHotelAvailNotifRS otaHotelAvailNotifRS = JointWisdomARIUtils.hotelAvailNotifRQ(list);
            List<Object> errorsOrWarningsOrSuccess = otaHotelAvailNotifRS.getErrorsOrWarningsOrSuccess();
            if (CollectionUtils.isEmpty(errorsOrWarningsOrSuccess)){
                result.setStatus(Constants.SUCCESS200);
            }else {
                logger.info("下架失败");
                result.setStatus(Constants.ERROR400);
            }
            return  result;
        } catch (Exception e) {
            result.setStatus(Constants.ERROR400);
            logger.error("下架失败",e);
        }
        return result;
    }

    @Override
    public Result updateJsPriceInventory(List<JointWisdomMappingDto> jointWisdomInnRoomList, OtaRoomPriceDto priceDto, OtaCommissionPercentDto commission) {
        Result result = new Result();
        if (jointWisdomInnRoomList!=null){

            List<RoomPrice> roomPrice = JwXHotelUtil.buildRoomPrice(jointWisdomInnRoomList,priceDto,commission);
            List<Inventory> inventory = JwXHotelUtil.inventory(jointWisdomInnRoomList);
            OTAHotelRatePlanNotifRS otaHotelRatePlanNotifRS = JointWisdomARIUtils.pushRoomPrice(roomPrice);
            OTAHotelInvCountNotifRS otaHotelInvCountNotifRS = JointWisdomARIUtils.pushRoomInventory(inventory);
            List<Object> refsOrSuccess = null;
            List<Object> successOrWarnings = null;
            if (otaHotelRatePlanNotifRS!=null && otaHotelInvCountNotifRS!=null ){
                refsOrSuccess = otaHotelRatePlanNotifRS.getErrorsOrRatePlanCrossRefsOrSuccess();
                successOrWarnings = otaHotelInvCountNotifRS.getErrorsOrSuccessOrWarnings();
                if (CollectionUtils.isEmpty(refsOrSuccess) && CollectionUtils.isEmpty(successOrWarnings)){
                    //房型上下架
                    Result status = xjRoomStatus(jointWisdomInnRoomList);
                    if (Constants.SUCCESS200 == status.getStatus()){
                        logger.info("上/下架 和 推送价格库存成功");
                        result.setStatus(Constants.SUCCESS200);
                    }else {
                        result.setStatus(Constants.ERROR400);
                        throw  new TomsRuntimeException("上/下架 失败");
                    }
                }else {
                    if (!CollectionUtils.isEmpty(refsOrSuccess) ){
                        logger.error("=====推送价格异常=====");
                        result.setMessage("=====推送价格异常=====");
                        throw  new TomsRuntimeException("推送价格异常");
                    }
                    if (!CollectionUtils.isEmpty(successOrWarnings)){
                        logger.error("=====推送库存异常=====");
                        result.setMessage("=====推送库存异常=====");
                        throw  new TomsRuntimeException("推送库存异常");

                    }
                    result.setStatus(Constants.ERROR400);
                }
            }
        }
        return result;
    }

    @Override
    public Result xjRoomStatus(List<JointWisdomMappingDto> jointWisdomInnRoomList) {
        List<HotelRoomAvail> hotelRoomAvail = JwXHotelUtil.hotelRoomAvail(jointWisdomInnRoomList);
        Result result = new Result();
        try {
            OTAHotelAvailNotifRS otaHotelAvailNotifRS = JointWisdomARIUtils.hotelAvailNotifRQ(hotelRoomAvail);
            List<Object> errorsOrWarningsOrSuccess = otaHotelAvailNotifRS.getErrorsOrWarningsOrSuccess();
            if (CollectionUtils.isEmpty(errorsOrWarningsOrSuccess)){
                result.setStatus(Constants.SUCCESS200);
            }else {
                logger.info("上/下架失败");
                result.setStatus(Constants.ERROR400);
            }
            return  result;
        } catch (Exception e) {
            result.setStatus(Constants.ERROR400);
            logger.error("上/下架失败",e);
            throw  new TomsRuntimeException("上/下架失败",e);
        }

    }
}
