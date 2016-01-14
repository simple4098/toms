package com.fanqielaile.toms.support;

import com.alibaba.fastjson.JSON;
import com.fanqie.jw.JointWiddomRequest;
import com.fanqie.jw.dto.Inventory;
import com.fanqie.jw.dto.InventoryRelation;
import com.fanqie.jw.dto.RoomPrice;
import com.fanqie.jw.dto.RoomPriceRelation;
import org.apache.commons.lang.StringUtils;
import org.opentravel.ota._2003._05.*;
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


    //CurrencyCode:默认传CNY
    private static String  CURRENCY_CODE = "CNY";

    // AgeQualifyingCode:10 成人
    private static String AGE_QUALIFYING_CODE = "10";

    //Definitive,默认值
    private static String COUNT_TYPE = "2";

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
        OTAHotelInvCountNotifRS resp = JointWiddomRequest.getDefaultInstance().otaHotelInvCountNotifRQ(invCountNotifRQ);
        LOGGER.info("请求众荟推送库存->response：" + JSON.toJSON(resp));
        LOGGER.info("请求推送库存成功，客栈ID：" + inventory.getInnId() + "房型ID:" + inventory.getRoomTypeId());
        return  resp;
    }
    /**
     *  推送房价
     * @param roomPrice
     *             库存关系对象
     * @throws Exception
     *             所有异常父类。其中包括参数异常，携程请求异常等
     */
    public static OTAHotelRatePlanNotifRS pushRoomPrice(RoomPrice roomPrice) throws Exception{
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
        OTAHotelRatePlanNotifRS resp =  JointWiddomRequest.getDefaultInstance().otaHotelRatePlanNotifRQ(otaHotel);
        LOGGER.info("请求众荟推送房价：response->"+JSON.toJSON(resp));
        return  resp;
    }
}
