package com.fanqielaile.toms.service.impl;

import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.TomsConstants;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.enums.OperateType;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.fc.*;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.IFcRoomTypeFqService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/23
 * @version: v1.0.0
 */
@Service
public class FcRoomTypeFqService implements IFcRoomTypeFqService {

    private static  final Logger log = LoggerFactory.getLogger(FcHotelInfoService.class);
    @Resource
    private IFcRoomTypeFqDao fcRoomTypeFqDao;
    @Resource
    private IFcRatePlanDao fcRatePlanDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private CompanyDao companyDao;

    @Override
    public List<FcRoomTypeFqDto> findFcRoomTypeFq(FcRoomTypeFqDto fcRoomTypeFq) {
        Assert.hasText(fcRoomTypeFq.getCompanyId());
        Assert.hasText(fcRoomTypeFq.getInnId());
        Assert.hasText(fcRoomTypeFq.getOtaInfoId());
        return fcRoomTypeFqDao.selectFcRoomTypeFq(fcRoomTypeFq);
    }

    @Override
    public void updateRoomTypeRatePlan(String fcRoomTypeFqId, String ratePlanId)throws Exception{
        FcRoomTypeFqDto fcRoomTypeFq = fcRoomTypeFqDao.selectFcRoomTypeFqById(fcRoomTypeFqId);
        OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(fcRoomTypeFq.getCompanyId(), OtaType.FC.name());
        FcRatePlan fcRatePlan =  fcRatePlanDao.selectFcRatePlanById(ratePlanId);
        String innId = fcRoomTypeFq.getInnId();
        String roomTypeId =fcRoomTypeFq.getFqRoomTypeId();

        //todo 同步到房仓
        SyncRatePlanRequest syncRatePlanRequest = new SyncRatePlanRequest();
        Header header = new Header(RequestType.syncRatePlan, dto.getAppKey(), dto.getAppSecret());
        syncRatePlanRequest.setHeader(header);
        SyncRatePlanRequestInfo syncRatePlanRequestInfo = new SyncRatePlanRequestInfo();
        syncRatePlanRequestInfo.setSpRoomTypeId(roomTypeId);
        syncRatePlanRequestInfo.setSpHotelId(innId);
        syncRatePlanRequestInfo.setOperateType(OperateType.NEW);
        List<RatePlan> ratePlanList = new ArrayList<>();
        RatePlan ratePlan = new RatePlan();
        ratePlan.setBedType(fcRatePlan.getBedType().getValue());
        ratePlan.setCurrency(fcRatePlan.getCurrency());
        ratePlan.setPayMethod(fcRatePlan.getPayMethod().getValue());
        ratePlan.setSpRatePlanId(fcRatePlan.getRatePlanId());
        ratePlan.setSpRatePlanName(fcRatePlan.getRatePlanName());
        ratePlanList.add(ratePlan);
        syncRatePlanRequestInfo.setRatePlanList(ratePlanList);
        syncRatePlanRequest.setSyncRatePlanRequest(syncRatePlanRequestInfo);
        String xml = FcUtil.fcRequest(syncRatePlanRequest);
        log.info("同步价格计划接口:" + xml);
        String result = HttpClientUtil.httpPost(CommonApi.FcSyncRatePlanUrl, xml);
        log.info("fc result :"+result);
        Response response = XmlDeal.pareFcResult(result);
        if (Constants.FcResultNo.equals(response.getResultNo())){
            fcRoomTypeFqDao.updateRoomTypeFqRatePlan(fcRoomTypeFqId,ratePlanId);
        }else {
            throw  new Exception("同步价格计划失败:"+response.getResultMsg());
        }

    }

    @Override
    public void updateSjMatchRoomType(String companyId, String matchRoomTypeId) throws Exception {
        Company company = companyDao.selectCompanyById(companyId);
        FcRoomTypeFqDto fcRoomTypeFq = fcRoomTypeFqDao.selectFcRoomTypeFqById(matchRoomTypeId);
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(fcRoomTypeFq.getInnId()));
        OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(fcRoomTypeFq.getCompanyId(), OtaType.FC.name());
        Integer roomTypeId = Integer.valueOf(fcRoomTypeFq.getFqRoomTypeId());
        String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE);
        String roomTypeGets = HttpClientUtil.httpGets(room_type, null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);

        SyncRateInfoRequest syncRateInfoRequest = new SyncRateInfoRequest();
        Header header = new Header(RequestType.syncRateInfo, dto.getAppKey(), dto.getAppSecret());
        syncRateInfoRequest.setHeader(header);
        SyncRateInfoDataRequest syncRateInfoDataRequest = new SyncRateInfoDataRequest();
        syncRateInfoDataRequest.setSpRoomTypeId(fcRoomTypeFq.getFqRoomTypeId());
        syncRateInfoDataRequest.setSpRatePlanId(fcRoomTypeFq.getFcRatePlanDto().getRatePlanId());
        syncRateInfoDataRequest.setSpHotelId(fcRoomTypeFq.getInnId());
        List<SaleInfo> saleInfoList = new ArrayList<>();

        //房型
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            for (RoomTypeInfo r:list){
                if (r.getRoomTypeId().equals(roomTypeId)){
                    List<RoomDetail> roomDetail = r.getRoomDetail();
                    for (RoomDetail room:roomDetail){
                        SaleInfo saleInfo = new SaleInfo();
                        saleInfo.setSaleDate(room.getRoomDate());
                        saleInfo.setSalePrice(BigDecimal.valueOf(room.getRoomPrice()));
                        //早餐类型
                        saleInfo.setBreakfastType(1);
                        saleInfo.setBreakfastNum(0);
                        saleInfo.setFreeSale(1);
                        //1有房  2 待查  3满房
                        saleInfo.setRoomState((room.getRoomNum()!=null ||room.getRoomNum()!=0)?1:3);
                        saleInfo.setOverdraft(0);
                        saleInfo.setOverDraftNum(10);
                        saleInfo.setQuotaNum(room.getRoomNum());
                        saleInfo.setMinAdvHours(36);
                        saleInfo.setMinDays(1);
                        saleInfo.setMaxDays(7);
                        saleInfo.setMinRooms(1);
                        saleInfo.setMinAdvCancelHours(12);
                        saleInfo.setCancelDescription("不能取消");
                        saleInfoList.add(saleInfo);
                    }
                    syncRateInfoDataRequest.setSaleInfoList(saleInfoList);
                    syncRateInfoRequest.setSyncRateInfoDataRequest(syncRateInfoDataRequest);
                }

            }
        }
        String xml = FcUtil.fcRequest(syncRateInfoRequest);
        log.info("上架xml:" + xml);
        String result = HttpClientUtil.httpPost(CommonApi.FcSyncRateInfoUrl, xml);
        log.info("fc result :"+result);
        Response response = XmlDeal.pareFcResult(result);
        if (Constants.FcResultNo.equals(response.getResultNo())){
            fcRoomTypeFqDao.updateRoomTypeFqSj(matchRoomTypeId, Constants.FC_SJ);
        }else {
            throw  new Exception("上架失败:"+response.getResultMsg());
        }
    }
}
