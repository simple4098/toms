package com.fanqielaile.toms.support;

import com.alibaba.fastjson.JSON;
import com.fanqie.jw.JointWiddomRequest;
import com.fanqie.jw.dto.*;
import com.fanqielaile.toms.support.util.FcUtil;
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
import java.util.List;

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
     * @param inventorys
     *             库存关系对象
     * @throws Exception
     *             所有异常父类。其中包括参数异常，携程请求异常等
     */
    public static OTAHotelInvCountNotifRS pushRoomInventory(List<Inventory> inventorys) {
        LOGGER.info("推送库存接受的参数：" + JSON.toJSON(inventorys));
        OTAHotelInvCountNotifRQ invCountNotifRQ = new OTAHotelInvCountNotifRQ();
        InvCountType invCountType = new InvCountType();
        invCountNotifRQ.setInventories(invCountType);
        for (Inventory inventory : inventorys) { // 合并节点
            inventory.setRelations(JoinUtils.joinInventory(inventory.getRelations()));
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
        }
        OTAHotelInvCountNotifRS resp = null;
        try{
            LOGGER.info("请求众荟推送库存->request：" + FcUtil.fcRequest(invCountNotifRQ));
            resp =  JointWiddomRequest.getDefaultInstance().otaHotelInvCountNotifRQ(invCountNotifRQ);
            LOGGER.info("请求众荟推送库存：response->"+FcUtil.fcRequest(resp));
        }catch (Exception e){
            LOGGER.error("众荟推送库存响应失败：",e);
            throw  new RuntimeException(e.getMessage());
        }
        return  resp;
    }
    /**
     *  推送房价
     * @param roomPrices
     *             库存关系对象
     * @throws Exception
     *             所有异常父类。其中包括参数异常，携程请求异常等
     */
    public static OTAHotelRatePlanNotifRS pushRoomPrice(List<RoomPrice> roomPrices){
        LOGGER.info("推送房价接受的参数："+JSON.toJSON(roomPrices));
        OTAHotelRatePlanNotifRQ otaHotel = new OTAHotelRatePlanNotifRQ();
        OTAHotelRatePlanNotifRQ.RatePlans ratePlans = new OTAHotelRatePlanNotifRQ.RatePlans();
        otaHotel.setRatePlans(ratePlans);
        for (RoomPrice roomPrice : roomPrices) {
        roomPrice.setRelations(JoinUtils.joinRoomPriceRate(roomPrice.getRelations()));
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
    //    otaHotel.setRatePlans(ratePlans);
        }
        OTAHotelRatePlanNotifRS resp = null;
        try{
            String hotelCode = otaHotel.getRatePlans().getHotelCode();
            LOGGER.info("请求众荟推送房价 request->"+hotelCode+ FcUtil.fcRequest(otaHotel));
            resp =  JointWiddomRequest.getDefaultInstance().otaHotelRatePlanNotifRQ(otaHotel);
            LOGGER.info("请求众荟推送房价 response->"+hotelCode+FcUtil.fcRequest(resp));
        }catch (Exception e){
            LOGGER.error("众荟推送响应失败：",e);
            throw  new RuntimeException(e.getMessage());
        }
        return  resp;
    }



    /**
     *  设置开关房
     */
    public static  OTAHotelAvailNotifRS  hotelAvailNotifRQ(List<HotelRoomAvail> hotelRoomAvails){
        LOGGER.info("请求众荟设置房态接受的参数："+JSON.toJSON(hotelRoomAvails));
        OTAHotelAvailNotifRQ rq = new OTAHotelAvailNotifRQ();
        AvailStatusMessages availStatusMessages = new AvailStatusMessages();
        rq.setAvailStatusMessages(availStatusMessages);
        for (HotelRoomAvail hotelRoomAvail : hotelRoomAvails) {
            availStatusMessages.setHotelCode(hotelRoomAvail.getInnId());
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
        }
        OTAHotelAvailNotifRS  resp = null;
        try{
            LOGGER.info("请求众荟设置房态: request->："+FcUtil.fcRequest(rq));
            resp =  JointWiddomRequest.getDefaultInstance().otaHotelAvailNotifRQ(rq);
            LOGGER.info("请求众荟设置房态：response->"+FcUtil.fcRequest(resp));
        }catch (Exception e){
            LOGGER.error("众荟设置响应失败：",e);
            throw  new RuntimeException(e.getMessage());
        }
        return resp;
    }



}
