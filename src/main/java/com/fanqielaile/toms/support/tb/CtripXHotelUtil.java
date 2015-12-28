package com.fanqielaile.toms.support.tb;

import com.fanqie.bean.enums.*;
import com.fanqie.bean.request.room_info.RoomInfoItem;
import com.fanqie.bean.request.room_info.RoomInfoRequest;
import com.fanqie.bean.request.room_info.SetRoomInfoRequest;
import com.fanqie.bean.request.room_price.*;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.enums.UsedPriceModel;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.apache.commons.collections.CollectionUtils;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/12/25
 * @version: v1.0.0
 */
public class CtripXHotelUtil {

    /**
     * 根据房型组装房价xml
     * @param roomDetailList 某一个房型的房态信息
     * @return
     */

    public static String requestRoomPriceXml( OtaInfoRefDto infoRefDto,CtripRoomTypeMapping mapping,List<RoomDetail> roomDetailList,
                                              OtaCommissionPercentDto commission,OtaRoomPriceDto priceDto,boolean isSj) throws JAXBException {
        if (!CollectionUtils.isEmpty(roomDetailList)){

            RoomPriceRequest roomPriceRequest = new RoomPriceRequest();
            SetAdaperRoomPriceRequest setAdaperRoomPriceRequest = new SetAdaperRoomPriceRequest(Integer.valueOf(mapping.getCtripChildHotelId()));
            List<SetRoomPriceItem> setRoomPriceItems = new ArrayList<>();
            List<PriceInfo> priceInfos = new ArrayList<>();
            List<Price> prices = null;
            double priceV = 0;
            for (RoomDetail roomDetail:roomDetailList){
                priceV = roomDetail.getRoomPrice();
                Date parseDate = DateUtil.parseDate(roomDetail.getRoomDate());
                TomsUtil.price(priceV,parseDate,commission,priceDto);
                priceV =isSj?priceV:-1;
                priceV = roomDetail.getRoomNum()==0?-1:priceV;
                Price price = new Price(0d,0d,0d,priceV,1);
                prices = new ArrayList<>();
                prices.add(price);
                PriceInfo priceInfo = new PriceInfo(prices,1,CtripBalanceType.PP, CtripPriceType.Cost);
                priceInfos.add(priceInfo);
            }
            String startDate = TomsUtil.getDateStringFormat(new Date());
            String endDate = TomsUtil.getDateStringFormat(DateUtil.addDay(new Date(), 29));
            SetRoomPriceItem setRoomPriceItem = new SetRoomPriceItem(priceInfos,Integer.valueOf(mapping.getCtripChildRoomTypeId()),startDate,endDate);
            setRoomPriceItem.setPriceInfo(priceInfos);
            setRoomPriceItems.add(setRoomPriceItem);
            setAdaperRoomPriceRequest.setSetRoomPriceItems(setRoomPriceItems);
            Authentication authentication = new Authentication(infoRefDto.getXcUserName(),infoRefDto.getXcPassword());
            RequestType requestType = new RequestType(CtripRequestType.SetRoomInfo, CtripVersion.V12);
            HeaderInfo headerInfo = new  HeaderInfo(infoRefDto.getUserId(), CtripConstants.requestorId,false,authentication,requestType);
            roomPriceRequest.setSetAdaperRoomPriceRequest(setAdaperRoomPriceRequest);
            roomPriceRequest.setHeaderInfo(headerInfo);
            return FcUtil.fcRequest(roomPriceRequest);
        }
        return null;
    }

    /**
     * 根据房型组装设置房态xml
     * @param roomDetailList 某一个房型的房态信息
     */

    public static String requestSetRoomInfoXml( OtaInfoRefDto infoRefDto,CtripRoomTypeMapping mapping,List<RoomDetail> roomDetailList) throws JAXBException {
        if (!CollectionUtils.isEmpty(roomDetailList)){
            Authentication authentication = new Authentication(infoRefDto.getXcUserName(),infoRefDto.getXcPassword());
            RequestType requestType = new RequestType(CtripRequestType.SetRoomInfo, CtripVersion.V12);
            HeaderInfo headerInfo = new  HeaderInfo(infoRefDto.getUserId(),CtripConstants.requestorId,false,authentication,requestType);
            List<RoomInfoItem> roomInfoItems = new ArrayList<>();
            for (RoomDetail roomDetail:roomDetailList){
                CtripRoomStatus ctripRoomStatus = roomDetail.getRoomNum()>0?CtripRoomStatus.G:CtripRoomStatus.N;
                RoomInfoItem roomInfoItem = new RoomInfoItem(DeductType.C,CtripConstants.reserveTime,
                        CheckType.C,CtripConstants.lateReserveTime,
                        CtripConstants.guaranteeLCT,"",AllNeedGuarantee.F,roomDetail.getRoomNum(),
                        ctripRoomStatus,CtripConstants.holdDeadline,
                        ChangeDefault.F,CtripConstants.userLimited,CtripConstants.prepayLCT);
                roomInfoItems.add(roomInfoItem);
            }
            SetRoomInfoRequest setRoomInfoRequest = new SetRoomInfoRequest(roomInfoItems,Integer.valueOf(mapping.getCtripChildRoomTypeId()),TomsUtil.getDateStringFormat(new Date()),TomsUtil.getDateStringFormat(DateUtil.addDay(new Date(), 1)));
            RoomInfoRequest roomInfoRequest = new RoomInfoRequest(setRoomInfoRequest,headerInfo);
            return FcUtil.fcRequest(roomInfoRequest);
        }
        return null;
    }
}
