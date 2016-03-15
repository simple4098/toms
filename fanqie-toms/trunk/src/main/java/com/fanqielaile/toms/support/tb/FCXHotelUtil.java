package com.fanqielaile.toms.support.tb;

import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.enums.UsedPriceModel;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.fc.*;
import com.fanqielaile.toms.support.exception.VettedOTAException;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.apache.commons.collections.CollectionUtils;
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
    private static final Logger log = LoggerFactory.getLogger(FCXHotelUtil.class);

    private FCXHotelUtil() {
    }


    public static Response syncRateInfo(Company company, OtaInfoRefDto o, FcRoomTypeFqDto fcRoomTypeFqDto,
                                        BangInn bangInn, Integer roomTypeId, OtaRoomPriceDto priceDto, OtaCommissionPercentDto commission) throws Exception {
        SyncRateInfoRequest syncRateInfoRequest = syncRateInfoRequestMethod(o);
        SyncRateInfoDataRequest syncRateInfoDataRequest = syncRateInfoDataRequestMethod(fcRoomTypeFqDto);
        List<SaleInfo> saleInfoList = new ArrayList<>();
        SaleInfo saleInfo = null;
        String room_type = DcUtil.omsFcRoomTYpeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(), bangInn.getInnId(), roomTypeId, CommonApi.checkRoom);
        log.info("fc oms url:" + room_type);
        List<RoomDetail> roomDetail = InnRoomHelper.getRoomDetail(room_type);
        if (!CollectionUtils.isEmpty(roomDetail)) {
            for (RoomDetail room : roomDetail) {
                saleInfo = obtSaleInfoList(room, priceDto, commission);
                saleInfoList.add(saleInfo);
            }
            syncRateInfoDataRequest.setSaleInfoList(saleInfoList);
            syncRateInfoRequest.setSyncRateInfoDataRequest(syncRateInfoDataRequest);
            String xml = FcUtil.fcRequest(syncRateInfoRequest);
            log.info("房仓请求xml:"+xml);
            String result = HttpClientUtil.httpPost(CommonApi.FcSyncRateInfoUrl, xml);
            log.info("fc result :" + result);
            return XmlDeal.pareFcResult(result);
        }
        return null;
    }


    public static Response syncRoomInfo(OtaInfoRefDto o, FcRoomTypeFqDto fcRoomTypeFqDto, List<RoomDetail> roomDetail, OtaRoomPriceDto priceDto,
                                        OtaCommissionPercentDto commission) throws Exception {
        SyncRateInfoRequest syncRateInfoRequest = syncRateInfoRequestMethod(o);
        SyncRateInfoDataRequest syncRateInfoDataRequest = syncRateInfoDataRequestMethod(fcRoomTypeFqDto);
        List<SaleInfo> saleInfoList = new ArrayList<>();
        SaleInfo saleInfo = null;
        for (RoomDetail room : roomDetail) {
            saleInfo = obtSaleInfoList(room, priceDto, commission);
            saleInfoList.add(saleInfo);
        }
        syncRateInfoDataRequest.setSaleInfoList(saleInfoList);
        syncRateInfoRequest.setSyncRateInfoDataRequest(syncRateInfoDataRequest);
        String xml = FcUtil.fcRequest(syncRateInfoRequest);
        log.info("房仓增加价格之后 往房仓上推 xml:" + xml);
        String result = HttpClientUtil.httpPost(CommonApi.FcSyncRateInfoUrl, xml);
        log.info("fc result :" + result);
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
    private static SaleInfo obtSaleInfoList(RoomDetail room, OtaRoomPriceDto priceDto, OtaCommissionPercentDto commission) {
        double price = 0;
        Double value = null;
        Date startDate = null;
        Date endDate = null;
        if (priceDto != null) {
            value = priceDto.getValue();
            startDate = priceDto.getStartDate();
            endDate = priceDto.getEndDate();
        }
        List<SaleInfo> saleInfoList = new ArrayList<>();
        Date parseDate = DateUtil.parseDate(room.getRoomDate());
        price = room.getRoomPrice();
        //售价只有MAI2DI才展示
        if (commission != null && commission.getsJiaModel().equals(UsedPriceModel.MAI2DI.name())) {
            price = TomsUtil.price(price, new BigDecimal(commission.getCommissionPercent()));
        }
        if (priceDto != null && parseDate.getTime() >= startDate.getTime() && endDate.getTime() >= parseDate.getTime()) {
            price = price + value;
        }

        SaleInfo saleInfo = new SaleInfo();
        saleInfo.setSaleDate(room.getRoomDate());
        saleInfo.setSalePrice(BigDecimal.valueOf(price));
        //早餐类型
        saleInfo.setBreakfastType(1);
        saleInfo.setBreakfastNum(0);
        saleInfo.setFreeSale(0);
        //1有房  2 待查  3满房
        saleInfo.setRoomState((room.getRoomNum() != null || room.getRoomNum() != 0) ? Constants.FC_HAVE_ROOM : Constants.FC_NOT_HAVE_ROOM);
        saleInfo.setOverdraft("");
        saleInfo.setOverDraftNum("");
        saleInfo.setQuotaNum((room.getRoomNum()!=null ||room.getRoomNum()!=0)?room.getRoomNum():0);
        saleInfo.setMinAdvHours("");
        saleInfo.setMinDays("");
        saleInfo.setMaxDays("");
        //最少预订间数
        saleInfo.setMinRooms(1);
        saleInfo.setMinAdvCancelHours("0");
        saleInfo.setCancelDescription("不能取消");
        saleInfoList.add(saleInfo);
        return saleInfo;
    }


    public static void vettedOtaAppKey(OtaInfoRefDto o) throws VettedOTAException {
        GetHotelInfoRequest getHotelInfoRequest = new GetHotelInfoRequest();
        getHotelInfoRequest.setFcHotelIds(" ");
        Header header = new Header(RequestType.getHotelInfo, o.getAppKey(), o.getAppSecret());
        GetHotelRequest getHotelRequest = new GetHotelRequest(header, getHotelInfoRequest);
        Response response = null;
        try {
            String s = FcUtil.fcRequest(getHotelRequest);
            String gets = HttpClientUtil.httpPost(CommonApi.fcHotelInfo, s);
            response = XmlDeal.pareFcResult(gets);
        } catch (Exception e) {
           log.error("房仓验证异常 vettedOtaAppKey:"+e);
        }
        if (Constants.FcPartnerCodeNo.equals(response.getResultNo())
                || Constants.FcSignatureNo.equals(response.getResultNo())
                || Constants.FcPartnerCodeFailureNo.equals(response.getResultNo())) {
            throw new VettedOTAException(response.getResultMsg());
        }
    }

}
