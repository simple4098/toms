package com.fanqielaile.toms.support;

import com.alibaba.fastjson.JSON;
import com.fanqie.jw.JointWiddomRequest;
import com.fanqie.jw.dto.*;
import org.apache.commons.lang.StringUtils;
import org.opentravel.ota._2003._05.ArrayOfBaseInvCountTypeInvCount;
import org.opentravel.ota._2003._05.ArrayOfDestinationSystemCodesTypeDestinationSystemCode;
import org.opentravel.ota._2003._05.ArrayOfHotelRatePlanTypeRate;
import org.opentravel.ota._2003._05.ArrayOfRateUploadTypeBaseByGuestAmt;
import org.opentravel.ota._2003._05.AvailStatusMessageType;
import org.opentravel.ota._2003._05.BaseInvCountType;
import org.opentravel.ota._2003._05.HotelRatePlanType;
import org.opentravel.ota._2003._05.InvCountType;
import org.opentravel.ota._2003._05.LengthsOfStayType;
import org.opentravel.ota._2003._05.LengthsOfStayType.LengthOfStay;
import org.opentravel.ota._2003._05.OTAHotelAvailNotifRQ;
import org.opentravel.ota._2003._05.OTAHotelAvailNotifRQ.AvailStatusMessages;
import org.opentravel.ota._2003._05.OTAHotelAvailNotifRS;
import org.opentravel.ota._2003._05.OTAHotelInvCountNotifRQ;
import org.opentravel.ota._2003._05.OTAHotelInvCountNotifRS;
import org.opentravel.ota._2003._05.OTAHotelRatePlanNotifRQ;
import org.opentravel.ota._2003._05.OTAHotelRatePlanNotifRS;
import org.opentravel.ota._2003._05.StatusApplicationControlType;
import org.opentravel.ota._2003._05.TimeUnitType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *  众荟ARI相关
 */
public class JointWisdomARIUtils{

    private final static Logger LOGGER = LoggerFactory.getLogger(JointWisdomARIUtils.class);

    /**
     * 最大入住
     */
    private static final String SET_MAX_LOS = "SetMaxLOS";

    /**
     * 最大入住天数
     */
    private static String SET_MAX_LOS_DAYS = "9999";


    //CurrencyCode:默认传CNY
    private static String  CURRENCY_CODE = "CNY";

    // AgeQualifyingCode:10 成人
    private static String AGE_QUALIFYING_CODE = "10";

    //Definitive,默认值
    private static String COUNT_TYPE = "2";

    //渠道名称 携程填CTRIP
    private static String CTRIP = "CTRIP";

    //Restriction: 默认填Master
    private static String MASTER = "Master";

    //BookingLimitMessageType==默认传setLimit
    private static String SET_LIMIT = "SetLimit";


    /**
     *  推送库存
     * @param inventory
     *             库存关系对象
     * @throws Exception
     *             所有异常父类。其中包括参数异常，携程请求异常等
     */
    public static OTAHotelInvCountNotifRS pushRoomInventory(Inventory inventory) {
        LOGGER.info("推送库存接受的参数：" + JSON.toJSON(inventory));
        Assert.isTrue(StringUtils.isNotEmpty(inventory.getInnId()));
        Assert.isTrue(StringUtils.isNotEmpty(inventory.getRoomTypeId()));
        inventory.setRelations(JoinUtils.joinInventory(inventory.getRelations()));
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
            sact.setUsed(relation.getUsed());
            sact.getInvTypeCode().add(inventory.getRoomTypeId());
            ArrayOfBaseInvCountTypeInvCount invCounts = new ArrayOfBaseInvCountTypeInvCount();
            ArrayOfBaseInvCountTypeInvCount.InvCount invCount = new ArrayOfBaseInvCountTypeInvCount.InvCount();
            invCount.setCountType(COUNT_TYPE);
            invCount.setCount(new BigInteger(relation.getInventoryCount() + ""));
            invCounts.getInvCount().add(invCount);
            bct.setInvCounts(invCounts);
            bct.setStatusApplicationControl(sact);
        }
        LOGGER.info("请求众荟推送库存->request：" + JSON.toJSON(invCountNotifRQ));
        OTAHotelInvCountNotifRS resp = null;
        try{
            resp =  JointWiddomRequest.getDefaultInstance().otaHotelInvCountNotifRQ(invCountNotifRQ);
            LOGGER.info("请求众荟推送库存：response->"+JSON.toJSON(resp));
        }catch (Exception e){
            LOGGER.error("众荟推送库存响应失败：",e);
            throw  new RuntimeException(e.getMessage());
        }
        return  resp;
    }
    /**
     *  推送房价
     * @param roomPrice
     *             库存关系对象
     * @throws Exception
     *             所有异常父类。其中包括参数异常，携程请求异常等
     */
    public static OTAHotelRatePlanNotifRS pushRoomPrice(RoomPrice roomPrice){
        LOGGER.info("推送房价接受的参数："+JSON.toJSON(roomPrice));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getInnId()));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getRatePlanCode()));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getRoomTypeId()));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getStart()));
        Assert.isTrue(StringUtils.isNotEmpty(roomPrice.getEnd()));
        Assert.isTrue(null!=roomPrice.getRelations());
        roomPrice.setRelations(JoinUtils.joinRoomPriceRate(roomPrice.getRelations()));
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
            rate.setUsed(relation.getUsed());
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
        LOGGER.info("请求众荟推送房价 request->："+JSON.toJSON(otaHotel));
        OTAHotelRatePlanNotifRS resp = null;
        try{
            resp =  JointWiddomRequest.getDefaultInstance().otaHotelRatePlanNotifRQ(otaHotel);
            LOGGER.info("请求众荟推送房价：response->"+JSON.toJSON(resp));
        }catch (Exception e){
            LOGGER.error("众荟推送响应失败：",e);
            throw  new RuntimeException(e.getMessage());
        }
        return  resp;
    }



    /**
     *  设置开关房
     */
    public static  OTAHotelAvailNotifRS  hotelAvailNotifRQ(HotelRoomAvail hotelRoomAvail){
        LOGGER.info("请求众荟设置房态接受的参数："+JSON.toJSON(hotelRoomAvail));
        Assert.isTrue(StringUtils.isNotEmpty(hotelRoomAvail.getInnId()));
        Assert.isTrue(StringUtils.isNotEmpty(hotelRoomAvail.getRoomTypeId()));
        Assert.isTrue(StringUtils.isNotEmpty(hotelRoomAvail.getStart()));
        Assert.isTrue(StringUtils.isNotEmpty(hotelRoomAvail.getEnd()));
        OTAHotelAvailNotifRQ rq = new OTAHotelAvailNotifRQ();
        AvailStatusMessages availStatusMessages = new AvailStatusMessages();
        availStatusMessages.setHotelCode(hotelRoomAvail.getInnId());
        rq.setAvailStatusMessages(availStatusMessages);
        AvailStatusMessageType availStatusMessage = new AvailStatusMessageType();
        availStatusMessages.getAvailStatusMessage().add(availStatusMessage);
        //	availStatusMessage.setBookingLimit(); 最大库存(不能确认的值。暂时不传，根据电话会议沟通，这个值众荟目前也没有处理)
        availStatusMessage.setBookingLimitMessageType(SET_LIMIT);
        StatusApplicationControlType applicationControlType = new StatusApplicationControlType();
        availStatusMessage.setStatusApplicationControl(applicationControlType);
        applicationControlType.setStart(hotelRoomAvail.getStart()); //开始时间
        applicationControlType.setEnd(hotelRoomAvail.getEnd()); // 结束时间
        applicationControlType.setRatePlanCode(hotelRoomAvail.getRatePlan());//房价
        applicationControlType.getInvTypeCode().add(hotelRoomAvail.getRoomTypeId()); // 房型
        applicationControlType.setUsed(hotelRoomAvail.getUsed());                              // 设置是否应用该规则
        ArrayOfDestinationSystemCodesTypeDestinationSystemCode destinationSystemCodes =
                new ArrayOfDestinationSystemCodesTypeDestinationSystemCode();
        applicationControlType.setDestinationSystemCodes(destinationSystemCodes);
        ArrayOfDestinationSystemCodesTypeDestinationSystemCode.DestinationSystemCode destinationSystemCode =
                new ArrayOfDestinationSystemCodesTypeDestinationSystemCode.DestinationSystemCode();
        destinationSystemCodes.getDestinationSystemCode().add(destinationSystemCode);
        destinationSystemCode.setValue(CTRIP);
        LengthsOfStayType lengthsOfStay = new LengthsOfStayType();
        availStatusMessage.setLengthsOfStay(lengthsOfStay);
        LengthOfStay lengthOfStay = new LengthOfStay();
        lengthOfStay.setMinMaxMessageType(SET_MAX_LOS);
        lengthOfStay.setTime(new BigInteger(SET_MAX_LOS_DAYS));
        lengthOfStay.setTimeUnit(TimeUnitType.DAY);
        lengthsOfStay.getLengthOfStay().add(lengthOfStay);
        AvailStatusMessageType.RestrictionStatus restrictionStatus = new AvailStatusMessageType.RestrictionStatus();
        availStatusMessage.setRestrictionStatus(restrictionStatus);
        restrictionStatus.setRestriction(MASTER); //默认
        restrictionStatus.setStatus(hotelRoomAvail.getAvailabilityStatusType());// 房态（开房或者关房状态）
        LOGGER.info("请求众荟设置房态: request->："+JSON.toJSON(rq));
        OTAHotelAvailNotifRS  resp = null;
        try{
            resp =  JointWiddomRequest.getDefaultInstance().otaHotelAvailNotifRQ(rq);
            LOGGER.info("请求众荟设置房态：response->"+JSON.toJSON(resp));
        }catch (Exception e){
            LOGGER.error("众荟设置响应失败：",e);
            throw  new RuntimeException(e.getMessage());
        }
        return resp;
    }


}
