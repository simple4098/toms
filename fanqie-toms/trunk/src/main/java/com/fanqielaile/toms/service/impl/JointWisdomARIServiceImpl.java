package com.fanqielaile.toms.service.impl;

import com.alibaba.fastjson.JSON;
import com.fanqie.jw.JointWiddomRequest;
import com.fanqie.jw.dto.Inventory;
import com.fanqie.jw.dto.InventoryRelation;
import com.fanqie.jw.dto.RoomPrice;
import com.fanqie.jw.dto.RoomPriceRelation;
import com.fanqielaile.toms.service.JointWisdomARIService;
import com.fanqielaile.toms.support.HandlerJointWisdomResult;
import org.apache.commons.lang.StringUtils;
import org.opentravel.ota._2003._05.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public class JointWisdomARIServiceImpl  implements JointWisdomARIService{

    private final static Logger LOGGER = LoggerFactory.getLogger(JointWisdomARIServiceImpl.class);


    @Override
    public void pushRoomInventory(Inventory inventory) throws Exception {
        LOGGER.info("接受的参数："+JSON.toJSON(inventory));
        Assert.isTrue(StringUtils.isNotEmpty(inventory.getInnId()));
        Assert.isTrue(StringUtils.isNotEmpty(inventory.getRoomTypeId()));
        OTAHotelInvCountNotifRQ invCountNotifRQ = new OTAHotelInvCountNotifRQ();
        InvCountType invCountType = new InvCountType();
        invCountNotifRQ.setInventories(invCountType);
        invCountType.setHotelCode(inventory.getInnId());
        for (InventoryRelation relation : inventory.getRelations()) {
            BaseInvCountType bct = new BaseInvCountType();
            invCountType.getInventory().add(bct);
            StatusApplicationControlType sact = new StatusApplicationControlType();
            sact.setStart(relation.getStart());
            sact.setEnd(relation.getEnd());
            sact.getInvTypeCode().add(inventory.getRoomTypeId());
            ArrayOfBaseInvCountTypeInvCount invCounts = new ArrayOfBaseInvCountTypeInvCount();
            ArrayOfBaseInvCountTypeInvCount.InvCount invCount = new ArrayOfBaseInvCountTypeInvCount.InvCount();
            invCount.setCountType(COUNT_TYPE);
            invCount.setCount(new BigInteger(relation.getInventoryCount()+""));
            invCounts.getInvCount().add(invCount);
            bct.setInvCounts(invCounts);
            bct.setStatusApplicationControl(sact);
        }
        LOGGER.info("请求携程推送库存："+JSON.toJSON(invCountNotifRQ));
        OTAHotelInvCountNotifRS result =  JointWiddomRequest.getDefaultInstance().otaHotelInvCountNotifRQ(invCountNotifRQ);
        List<Object>  listResults = result.getErrorsOrSuccessOrWarnings();
        HandlerJointWisdomResult.handler(listResults);
        LOGGER.info("推送库存成功，客栈ID："+inventory.getInnId()+"房型ID:"+inventory.getRoomTypeId());

    }

    @Override
    public void pushRoomPrice(RoomPrice roomPrice) throws Exception{
        LOGGER.info("接受的参数："+JSON.toJSON(roomPrice));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getInnId()));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getRatePlanCode()));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getRoomTypeId()));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getStart()));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getEnd()));
        Assert.isTrue(null!=roomPrice.getRelations());
        OTAHotelRatePlanNotifRQ otaHotel = new OTAHotelRatePlanNotifRQ();
        OTAHotelRatePlanNotifRQ.RatePlans ratePlans = new OTAHotelRatePlanNotifRQ.RatePlans();
        otaHotel.setRatePlans(ratePlans);
        ratePlans.setHotelCode(roomPrice.getInnId());
        HotelRatePlanType hotelRatePlanType = new HotelRatePlanType();
        hotelRatePlanType.setRatePlanCode(roomPrice.getRatePlanCode());
        hotelRatePlanType.setStart(roomPrice.getStart());
        hotelRatePlanType.setEnd(roomPrice.getEnd());
        hotelRatePlanType.setCurrencyCode(CURRENCY_CODE);
        ArrayOfHotelRatePlanTypeRate rates = new ArrayOfHotelRatePlanTypeRate();
        hotelRatePlanType.setRates(rates);
        for(RoomPriceRelation relation :  roomPrice.getRelations()){
            ArrayOfHotelRatePlanTypeRate.Rate rate = new ArrayOfHotelRatePlanTypeRate.Rate();
            rates.getRate().add(rate);
            rate.setStart(relation.getStart());
            rate.setEnd(relation.getEnd());
            rate.setRateTimeUnit(TimeUnitType.DAY);
            rate.setInvTypeCode(roomPrice.getRoomTypeId());
            rate.setCurrencyCode(CURRENCY_CODE);
            ArrayOfRateUploadTypeBaseByGuestAmt baseByGuestAmts = new ArrayOfRateUploadTypeBaseByGuestAmt();
            rate.setBaseByGuestAmts(baseByGuestAmts);
            for (int i = 1; i<=4 ; i++){
                ArrayOfRateUploadTypeBaseByGuestAmt.BaseByGuestAmt baseByGuestAmt = new ArrayOfRateUploadTypeBaseByGuestAmt.BaseByGuestAmt();
                baseByGuestAmt.setNumberOfGuests(new BigInteger(i+""));
                baseByGuestAmt.setAgeQualifyingCode(AGE_QUALIFYING_CODE);
                baseByGuestAmt.setAmountBeforeTax(new BigDecimal(relation.getAmountBeforeTax()));
                baseByGuestAmt.setAmountAfterTax(new BigDecimal(relation.getAmountAfterTax()));
                baseByGuestAmts.getBaseByGuestAmt().add(baseByGuestAmt);
            }
        }
        ratePlans.getRatePlan().add(hotelRatePlanType);
        otaHotel.setRatePlans(ratePlans);
        LOGGER.info("请求携程推送房价："+JSON.toJSON(otaHotel));
        OTAHotelRatePlanNotifRS resp =  JointWiddomRequest.getDefaultInstance().otaHotelRatePlanNotifRQ(otaHotel);
        List<Object> result =  resp.getErrorsOrRatePlanCrossRefsOrSuccess();
        HandlerJointWisdomResult.handler(result);
        LOGGER.info("房价推送，酒店ID：" + roomPrice.getInnId() + ",房型ID："+roomPrice.getRoomTypeId());
    }
}
