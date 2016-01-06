package com.fanqielaile.toms.support.tb;

import com.fanqie.bean.enums.*;
import com.fanqie.bean.request.room_info.RoomInfoItem;
import com.fanqie.bean.request.room_info.RoomInfoRequest;
import com.fanqie.bean.request.room_info.SetRoomInfoRequest;
import com.fanqie.bean.request.room_price.*;
import com.fanqie.bean.response.RequestResponse;
import com.fanqie.bean.response.RequestResult;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.CtripHttpClient;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.enums.UsedPriceModel;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger log = LoggerFactory.getLogger(CtripXHotelUtil.class);

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
                priceV = TomsUtil.price(priceV,parseDate,commission,priceDto);
                priceV =isSj?priceV:-1;
                priceV = roomDetail.getOtaRoomNum()==0?-1:priceV;
                Price price = new Price(0d,0d,0d,priceV,1);
                prices = new ArrayList<>();
                prices.add(price);
                PriceInfo priceInfo = new PriceInfo(prices,1,CtripBalanceType.PP, CtripPriceType.Cost);
                priceInfos.add(priceInfo);
            }
            String startDate = TomsUtil.getDateStringFormat(new Date());
            String endDate = TomsUtil.getDateStringFormat(DateUtil.addDay(new Date(), 60));
            SetRoomPriceItem setRoomPriceItem = new SetRoomPriceItem(priceInfos,Integer.valueOf(mapping.getCtripChildRoomTypeId()),startDate,endDate);
            setRoomPriceItem.setPriceInfo(priceInfos);
            setRoomPriceItems.add(setRoomPriceItem);
            setAdaperRoomPriceRequest.setSetRoomPriceItems(setRoomPriceItems);
            HeaderInfo headerInfo = new  HeaderInfo(infoRefDto.getUserId(), CtripConstants.requestorId,false);
            headerInfo.build(infoRefDto.getXcUserName(),infoRefDto.getXcPassword(),CtripRequestType.SetRoomPrice, CtripVersion.V12);
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
            HeaderInfo headerInfo = new  HeaderInfo(infoRefDto.getUserId(),CtripConstants.requestorId,false);
            headerInfo.build(infoRefDto.getXcUserName(),infoRefDto.getXcPassword(),CtripRequestType.SetRoomInfo, CtripVersion.V12);
            List<RoomInfoItem> roomInfoItems = new ArrayList<>();
            for (RoomDetail roomDetail:roomDetailList){
                CtripRoomStatus ctripRoomStatus = roomDetail.getOtaRoomNum()>0?CtripRoomStatus.G:CtripRoomStatus.N;
                Recommend recommend = roomDetail.getOtaRoomNum()>0?Recommend.R2:Recommend.R0;
                RoomInfoItem roomInfoItem = new RoomInfoItem(DeductType.C,CtripConstants.reserveTime,
                        CheckType.C,CtripConstants.lateReserveTime,
                        CtripConstants.guaranteeLCT,"",AllNeedGuarantee.F,roomDetail.getOtaRoomNum(),
                        ctripRoomStatus,CtripConstants.holdDeadline,
                        ChangeDefault.F,CtripConstants.userLimited,CtripConstants.prepayLCT);
                roomInfoItem.setRecommendValue(recommend);
                roomInfoItems.add(roomInfoItem);
            }
            SetRoomInfoRequest setRoomInfoRequest = new SetRoomInfoRequest(roomInfoItems,Integer.valueOf(mapping.getCtripChildRoomTypeId()),TomsUtil.getDateStringFormat(new Date()),TomsUtil.getDateStringFormat(DateUtil.addDay(new Date(), 1)));
            RoomInfoRequest roomInfoRequest = new RoomInfoRequest(setRoomInfoRequest,headerInfo);
            return FcUtil.fcRequest(roomInfoRequest);
        }
        return null;
    }

    /**
     * 更新房价 房态
     * @param infoRefDto 渠道信息
     * @param mapping 房型映射信息
     * @param priceDto 增减价
     * @param commission 佣金对象
     */
    public static RequestResponse syncRoomInfo(Company company, OtaInfoRefDto infoRefDto, CtripRoomTypeMapping mapping, OtaRoomPriceDto priceDto,
                                               OtaCommissionPercentDto commission) throws Exception {

        String room_type = DcUtil.omsRoomTypeUrl(company.getUserAccount(),
                company.getUserPassword(), company.getOtaId(), Integer.valueOf(mapping.getInnId()), Integer.valueOf(mapping.getTomRoomTypeId()), CommonApi.checkRoom, 60);
        log.info("xc url :"+room_type);
        List<RoomDetail> roomDetail = InnRoomHelper.getRoomDetail(room_type);
        RequestResponse response = null;
        RequestResult requestResult = new RequestResult();
        if (!CollectionUtils.isEmpty(roomDetail)){
            String requestRoomPriceXml = requestRoomPriceXml(infoRefDto, mapping, roomDetail, commission, priceDto, true);
            String requestSetRoomInfoXml = requestSetRoomInfoXml(infoRefDto, mapping, roomDetail);
            String execute = CtripHttpClient.execute(requestRoomPriceXml);
            String executeRoomStatus = CtripHttpClient.execute(requestSetRoomInfoXml);
            log.info("价格增减xml："+requestRoomPriceXml);
            log.info("房态xml："+requestSetRoomInfoXml);
            log.info("房价 result xml："+execute);
            log.info("房态 result xml："+executeRoomStatus);
            response = FcUtil.xMLStringToBean(execute);
            RequestResponse roomStatus = FcUtil.xMLStringToBean(executeRoomStatus);
            Integer resultCode = response.getRequestResult().getResultCode();
            Integer roomInfoCode = roomStatus.getRequestResult().getResultCode();

            if (CtripConstants.resultCode.equals(resultCode) && CtripConstants.resultCode.equals(roomInfoCode)){
                requestResult.setResultCode(0);
            }else {
                requestResult.setResultCode(-1);
            }
            response.setRequestResult(requestResult);

        }else {
            requestResult.setResultCode(-1);
            requestResult.setMessage("获取oms房态数据为空!");
        }
        return  response;
    }
}
