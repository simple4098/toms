package com.fanqielaile.toms.support.tb;

import com.fanqie.util.*;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.fc.*;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC : 房仓 API
 * @author : 番茄木-ZLin
 * @data : 2015/9/28
 * @version: v2.2.0
 */
public class FCXHotelUtil {
    private static  final Logger log = LoggerFactory.getLogger(FCXHotelUtil.class);
    private FCXHotelUtil(){}


    public static   Response  syncRateInfo(Company company,OtaInfoRefDto o, FcRoomTypeFqDto fcRoomTypeFqDto ,BangInn bangInn,Integer roomTypeId)throws Exception{
        String room_type = DcUtil.omsFcRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE);
        String roomTypeGets = HttpClientUtil.httpGets(room_type, null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        SyncRateInfoRequest syncRateInfoRequest = syncRateInfoRequestMethod(o);
        SyncRateInfoDataRequest syncRateInfoDataRequest = syncRateInfoDataRequestMethod(fcRoomTypeFqDto);
        List<SaleInfo> saleInfoList = new ArrayList<>();
        SaleInfo saleInfo = null;
        //房型
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            for (RoomTypeInfo r:list){
                if (r.getRoomTypeId().equals(roomTypeId)){
                    List<RoomDetail> roomDetail = r.getRoomDetail();
                    for (RoomDetail room:roomDetail){
                        saleInfo = obtSaleInfoList(room,null);
                        saleInfoList.add(saleInfo);
                    }
                    syncRateInfoDataRequest.setSaleInfoList(saleInfoList);
                    syncRateInfoRequest.setSyncRateInfoDataRequest(syncRateInfoDataRequest);
                }

            }
        }
        String xml = FcUtil.fcRequest(syncRateInfoRequest);
        log.info("房仓推送宝贝上架xml:" + xml);
        String result = HttpClientUtil.httpPost(CommonApi.FcSyncRateInfoUrl, xml);
        log.info("fc result :"+result);
        return XmlDeal.pareFcResult(result);
    }



    public static Response syncRoomInfo(OtaInfoRefDto o, FcRoomTypeFqDto fcRoomTypeFqDto,List<RoomDetail> roomDetail, OtaRoomPriceDto priceDto)throws Exception {
        SyncRateInfoRequest syncRateInfoRequest = syncRateInfoRequestMethod(o);
        SyncRateInfoDataRequest syncRateInfoDataRequest = syncRateInfoDataRequestMethod(fcRoomTypeFqDto);
        List<SaleInfo> saleInfoList = new ArrayList<>();
        SaleInfo saleInfo = null;
        for (RoomDetail room:roomDetail){
            saleInfo = obtSaleInfoList(room,priceDto);
            saleInfoList.add(saleInfo);
        }
        syncRateInfoDataRequest.setSaleInfoList(saleInfoList);
        syncRateInfoRequest.setSyncRateInfoDataRequest(syncRateInfoDataRequest);
        String xml = FcUtil.fcRequest(syncRateInfoRequest);
        log.info("房仓增加价格之后 往房仓上推 xml:" + xml);
        String result = HttpClientUtil.httpPost(CommonApi.FcSyncRateInfoUrl, xml);
        log.info("fc result :"+result);
        return XmlDeal.pareFcResult(result);
    }

    // 组装 SyncRateInfoDataRequest 对象
    private static SyncRateInfoDataRequest syncRateInfoDataRequestMethod(FcRoomTypeFqDto fcRoomTypeFqDto) {
        SyncRateInfoDataRequest syncRateInfoDataRequest = new SyncRateInfoDataRequest();
        syncRateInfoDataRequest.setSpRoomTypeId(fcRoomTypeFqDto.getFqRoomTypeId());
        syncRateInfoDataRequest.setSpRatePlanId(fcRoomTypeFqDto.getFcRatePlanDto().getRatePlanId());
        syncRateInfoDataRequest.setSpHotelId(fcRoomTypeFqDto.getInnId());
        return syncRateInfoDataRequest;

    }

    //组装 SyncRateInfoRequest 对象
    private static SyncRateInfoRequest syncRateInfoRequestMethod(OtaInfoRefDto o) {
        SyncRateInfoRequest syncRateInfoRequest = new SyncRateInfoRequest();
        Header header = new Header(RequestType.syncRateInfo, o.getAppKey(), o.getAppSecret());
        syncRateInfoRequest.setHeader(header);
        return syncRateInfoRequest;
    }

    //封装 房仓推送宝贝集合
    private static SaleInfo obtSaleInfoList(RoomDetail room,OtaRoomPriceDto priceDto) {
        double price = 0;
        Double value = null;
        Date startDate = null;
        Date endDate = null;
        if (priceDto!=null) {
            value = priceDto.getValue() ;
            startDate = priceDto.getStartDate();
            endDate = priceDto.getEndDate();
        }
        List<SaleInfo> saleInfoList = new ArrayList<>();
        Date parseDate = DateUtil.parseDate(room.getRoomDate());
        price = room.getRoomPrice();
        if (priceDto!=null && parseDate.getTime() >= startDate.getTime() && endDate.getTime() >= parseDate.getTime()) {
            price = price + value;
        }

        SaleInfo saleInfo = new SaleInfo();
        saleInfo.setSaleDate(room.getRoomDate());
        saleInfo.setSalePrice(BigDecimal.valueOf(price));
        //早餐类型
        saleInfo.setBreakfastType(1);
        saleInfo.setBreakfastNum(0);
        saleInfo.setFreeSale(1);
        //1有房  2 待查  3满房
        saleInfo.setRoomState((room.getRoomNum()!=null ||room.getRoomNum()!=0)?Constants.FC_HAVE_ROOM:Constants.FC_NOT_HAVE_ROOM);
        saleInfo.setOverdraft("");
        saleInfo.setOverDraftNum("");
        saleInfo.setQuotaNum((room.getRoomNum()!=null ||room.getRoomNum()!=0)?room.getRoomNum():0);
        saleInfo.setMinAdvHours("");
        saleInfo.setMinDays("");
        saleInfo.setMaxDays("");
        //最少预订间数
        saleInfo.setMinRooms(1);
        saleInfo.setMinAdvCancelHours("");
        saleInfo.setCancelDescription("不能取消");
        saleInfoList.add(saleInfo);
        return saleInfo;
    }

}
