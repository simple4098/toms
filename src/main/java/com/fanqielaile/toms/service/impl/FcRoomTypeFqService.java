package com.fanqielaile.toms.service.impl;

import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.TomsConstants;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaInnOtaDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.dto.fc.MatchRoomType;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;

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
        String room_type = DcUtil.omsFcRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE);
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


    @Override
    public void updateXjMatchRoomType(String companyId, String fqRoomTypeFcId) throws Exception{
        FcRoomTypeFqDto fcRoomTypeFq = fcRoomTypeFqDao.selectFcRoomTypeFqById(fqRoomTypeFcId);
        OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(companyId, OtaType.FC.name());
        List<DeleteRoomType> list = new ArrayList<DeleteRoomType>();
        DeleteRoomType deleteRoomType = new DeleteRoomType();
        deleteRoomType.setFcRoomTypeId(fcRoomTypeFq.getFqRoomTypeId());
        list.add(deleteRoomType);


        DeleteRoomTypeMappingInfoRequest deleteRatePlanInfoRequest = new DeleteRoomTypeMappingInfoRequest();
        deleteRatePlanInfoRequest.setSpHotelId(fcRoomTypeFq.getInnId());
        deleteRatePlanInfoRequest.setList(list);

        DeleteRoomTypeMappingRequest deleteRatePlanRequest = new DeleteRoomTypeMappingRequest();
        Header header = new Header(RequestType.deleteRoomTypeMapping, dto.getAppKey(),dto.getAppSecret());
        deleteRatePlanRequest.setHeader(header);
        deleteRatePlanRequest.setDeleteRoomTypeMappingInfoRequest(deleteRatePlanInfoRequest);

        String xml = FcUtil.fcRequest(deleteRatePlanRequest);
        log.info("下架xml:" + xml);
        String result = HttpClientUtil.httpPost(CommonApi.FcDeleteRoomTypeUrl, xml);
        log.info("fc result :"+result);
        Response response = XmlDeal.pareFcResult(result);
        //下架成功后 对应的匹配记录删除
        if (Constants.FcResultNo.equals(response.getResultNo())){
            //fcRoomTypeFqDao.updateRoomTypeFqSj(fqRoomTypeFcId, Constants.FC_XJ);
            fcRoomTypeFqDao.deletedFcRoomTypeById(fqRoomTypeFcId);
        }else {
            throw  new Exception("下架失败:"+response.getResultMsg());
        }
    }

    @Override
    public void updateMatchRoomType(String companyId, String innId, String fcHotelId, String json) throws Exception {
        List<MatchRoomType> matchRoomType = JacksonUtil.json2list(json, MatchRoomType.class);
        Company company = companyDao.selectCompanyById(companyId);
        OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(company.getId(), OtaType.FC.name());
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(innId));
        if (bangInn!=null){
            OtaInnOtaDto innOtaDto = otaInnOtaDao.selectOtaInnOtaByBangId(bangInn.getId(), companyId, dto.getOtaInfoId());
            if (!CollectionUtils.isEmpty(matchRoomType)){
                //删除绑定过的房型数据
                List<FcRoomTypeFqDto> roomTypeFqDtoList = fcRoomTypeFqDao.selectFcRoomTypeFq(new FcRoomTypeFqDto(String.valueOf(innId), companyId, dto.getOtaInfoId()));
                if (!CollectionUtils.isEmpty(roomTypeFqDtoList)){
                    for (FcRoomTypeFqDto roomTypeFqDto:roomTypeFqDtoList){
                        //FcRoomTypeId不为null才是绑定房型的 依次下架
                        if(!StringUtils.isEmpty(roomTypeFqDto.getFcRoomTypeId())){
                            updateXjMatchRoomType(companyId,roomTypeFqDto.getId());
                        }
                    }
                    //把没有绑定的房型也删除掉。
                    fcRoomTypeFqDao.deletedFcRoomTypeFqByInnIdAndCompanyId(innId,companyId,dto.getOtaInfoId());
                }

                List<RoomType> list = new ArrayList<RoomType>();
                List<FcRoomTypeFq> fcRoomTypeFqs = new ArrayList<FcRoomTypeFq>();
                RoomType roomType=null;
                FcRoomTypeFq fcRoomTypeFq = null;
                for (MatchRoomType room:matchRoomType){
                    fcRoomTypeFq = new FcRoomTypeFq();
                    fcRoomTypeFq.setCompanyId(companyId);
                    fcRoomTypeFq.setInnId(innId);
                    fcRoomTypeFq.setFcHotelId(fcHotelId);
                    fcRoomTypeFq.setFcRoomTypeId(room.getFcRoomTypeId());
                    fcRoomTypeFq.setFcRoomTypeName(room.getFcRoomTypeName());
                    fcRoomTypeFq.setFqRoomTypeName(room.getRoomTypeName());
                    fcRoomTypeFq.setRoomArea(room.getRoomArea());
                    fcRoomTypeFq.setOtaInfoId(dto.getOtaInfoId());
                    fcRoomTypeFq.setOtaInnOtaId(innOtaDto.getId());
                    fcRoomTypeFq.setFqRoomTypeId(room.getRoomTypeId());
                    if (!StringUtils.isEmpty(room.getFcRoomTypeId())){
                        roomType = new RoomType();
                        roomType.setFcRoomTypeId(Long.valueOf(room.getFcRoomTypeId()));
                        roomType.setFcRoomTypeName(room.getFcRoomTypeName());
                        roomType.setSpRoomTypeId(room.getRoomTypeId());
                        roomType.setSpRoomTypeName(room.getRoomTypeName());
                        list.add(roomType);
                    }
                    fcRoomTypeFqs.add(fcRoomTypeFq);
                }

                AddRoomTypeMappingRequest addRoomTypeMappingRequest = new AddRoomTypeMappingRequest();
                addRoomTypeMappingRequest.setRoomTypeList(list);
                addRoomTypeMappingRequest.setFcHotelId(Integer.valueOf(fcHotelId));
                addRoomTypeMappingRequest.setSpHotelId(innId);
                Header header = new Header(RequestType.addRoomTypeMapping,dto.getAppKey(),dto.getAppSecret());
                AddRoomTypeRequest hotelRequest = new AddRoomTypeRequest(header,addRoomTypeMappingRequest);
                try {
                    String xml = FcUtil.fcRequest(hotelRequest);
                    log.info("绑定房型:" + xml);
                    String result = HttpClientUtil.httpPost(CommonApi.FcAddRoomTypeMappingUrl, xml);
                    log.info("fc result :"+result);
                    Response response = XmlDeal.pareFcResult(result);
                    //todo 匹配接口不通
                    if (Constants.FcResultNo.equals(response.getResultNo())){
                        fcRoomTypeFqDao.saveRoomTypeFq(new FcRoomTypeFqDto(fcRoomTypeFqs));
                    }else {
                        throw  new Exception("绑定失败:"+response.getResultMsg());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    throw  new Exception("绑定失败:"+e.getMessage());
                }
            }
        }else {
            throw  new Exception("此客栈未绑定:"+innId);
        }

    }
}
