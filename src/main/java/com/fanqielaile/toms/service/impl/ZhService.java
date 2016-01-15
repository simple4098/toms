package com.fanqielaile.toms.service.impl;

import com.fanqie.bean.response.RequestResponse;
import com.fanqie.bean.response.RequestResult;
import com.fanqie.core.dto.TBParam;
import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.DcUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.enums.TimerRateType;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.model.fc.OtaRatePlan;
import com.fanqielaile.toms.service.ICtripRoomService;
import com.fanqielaile.toms.service.IJointWisdomARI;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.CallableBean;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.holder.TPHolder;
import com.fanqielaile.toms.support.tb.CtripXHotelUtil;
import com.fanqielaile.toms.support.tb.JwXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.ThreadCallableBean;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * DESC : 携程实现类
 * @author : 番茄木-ZLin
 * @data : 2015/12/22
 * @version: v1.0.0
 */
@Service("zhService")
public class ZhService implements ITPService {
    private static final Logger log = LoggerFactory.getLogger(ZhService.class);
    @Resource
    private CompanyDao companyDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;
    @Resource
    private ITimerRatePriceDao timerRatePriceDao;
    @Resource
    private IOtaCommissionPercentDao commissionPercentDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private TPHolder tpHolder;
    @Resource
    private CtripRoomTypeMappingDao ctripRoomTypeMappingDao;
    @Resource
    private ICtripRoomService ctripRoomService;
    @Resource
    private IJointWisdomInnRoomDao jointWisdomInnRoomDao;
    @Resource
    private IOtaPriceModelDao priceModelDao;
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private IFcRatePlanDao ratePlanDao;
    @Resource
    private IJointWisdomARI jointWisdomARI;


    @Override
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
        String innId = tbParam.getInnId();
        String otaInfoId = otaInfo.getOtaInfoId();
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        tpHolder.validate(company, innId, otaInfoId);
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        String inn_info = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId() != null ? tbParam.getAccountId() : tbParam.getAccountIdDi(), CommonApi.INN_INFO);
        InnDto omsInnDto = InnRoomHelper.getInnInfo(inn_info);
        if (omsInnDto != null) {
            omsInnDto.setInnId(innId);
            BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
            BangInnDto bangInnDto = null;
            if (bangInn == null) {
                bangInnDto = BangInnDto.toDto(company.getId(), tbParam, omsInnDto);
                bangInnDao.createBangInn(bangInnDto);
            } else {
                log.info("zh 客栈" + bangInn.getInnId() + " 已绑定" + " 状态:" + tbParam.isSj());
                OtaInnOtaDto otaInnOta = otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(bangInn.getInnId(), company.getId(), otaInfoId);
                TomsUtil.sjModel(tbParam,bangInn,omsInnDto);
                bangInnDao.updateBangInnTp(bangInn);
                String bangInnId = bangInn == null ? bangInnDto.getUuid() : bangInn.getId();
                if (otaInnOta == null) {
                    otaInnOta = OtaInnOtaDto.toDto(company.getOtaId()+"_"+bangInn.getInnId(), omsInnDto.getInnName(), company.getId(), tbParam, bangInnId, otaInfoId);
                    otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
                    otaInnOtaDao.saveOtaInnOta(otaInnOta);
                    OtaPriceModelDto otaPriceModel = OtaPriceModelDto.toDto(otaInnOta.getUuid());
                    priceModelDao.savePriceModel(otaPriceModel);
                } else {
                    otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
                    //下架状态的 删除关联关系
                    if (!tbParam.isSj()){
                        otaInnOtaDao.deletedOtaInnOtaById(otaInnOta.getId());
                    }else {
                        otaInnOtaDao.updateOtaInnOta(otaInnOta);
                    }
                }
                String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.ROOM_TYPE);
                String roomStatus = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.roomStatus,Constants.day);
                List<RoomTypeInfo> roomTypeInfoList = InnRoomHelper.getRoomTypeInfo(room_type);
                List<RoomStatusDetail> statusDetails = InnRoomHelper.getRoomStatus(roomStatus);
                InnRoomHelper.updateRoomTypeInfo(roomTypeInfoList,statusDetails);
                if (!CollectionUtils.isEmpty(roomTypeInfoList)){
                    OtaRatePlan otaRatePlan = ratePlanDao.selectRatePlanByCompanyIdOtaIdDefault(company.getId(), otaInfo.getOtaInfoId());
                    JointWisdomInnRoomMappingDto jointWisdomInnRoom = null;
                    List<JointWisdomInnRoomMappingDto> allList = new ArrayList<>();
                    OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(),otaInfo.getUsedPriceModel().name()));
                    OtaRoomPriceDto priceDto = null;
                    for (RoomTypeInfo roomTypeInfo:roomTypeInfoList){
                        priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(roomTypeInfo.getRoomTypeId()), otaInfo.getOtaInfoId()));
                        JointWisdomInnRoomMappingDto jw = jointWisdomInnRoomDao.selectJsInnRooType(company.getId(),Integer.valueOf(innId), roomTypeInfo.getRoomTypeId());
                        jointWisdomInnRoom = JwXHotelUtil.buildMapping(roomTypeInfo, company.getId(), Integer.valueOf(innId),String.valueOf(company.getOtaId()), otaInfoId, otaRatePlan.getRatePlanCode(),tbParam.isSj());
                        allList.add(jointWisdomInnRoom);
                        Result result = jointWisdomARI.updateJsPriceInventory(jointWisdomInnRoom, roomTypeInfo, priceDto, commission);
                        jointWisdomInnRoom.build(result.getStatus());
                        if (jw==null ){
                            jointWisdomInnRoomDao.insertJsRoomInnRooType(jointWisdomInnRoom);
                        }else {
                            jointWisdomInnRoom.setId(jw.getId());
                            jointWisdomInnRoomDao.updateJsRoomInnRooType(jointWisdomInnRoom);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {

    }

    @Override
    public void updateHotel(OtaInfoRefDto o, TBParam tbParam) throws Exception {
        log.info("====Xc 同步 start====");
        Company company = companyDao.selectCompanyByCompanyCode(o.getCompanyCode());
        List<JointWisdomInnRoomMappingDto> jointWisdomInnRoomMappings = jointWisdomInnRoomDao.selectJsRoomInnSj(company.getId(), o.getOtaInfoId());
        if (!CollectionUtils.isEmpty(jointWisdomInnRoomMappings)) {
            int size = jointWisdomInnRoomMappings.size();
            int timerThread = size / Constants.timerThread;
            int threadNum = timerThread == 0 ? 1 : timerThread;
            ExecutorService es = Executors.newFixedThreadPool(threadNum);
            CompletionService cs = new ExecutorCompletionService(es);
            for (JointWisdomInnRoomMappingDto mapping : jointWisdomInnRoomMappings) {
                cs.submit(getTask(company, mapping, o));
            }
        }
    }

    private Callable getTask(final Company company1, final JointWisdomInnRoomMappingDto mapping, final OtaInfoRefDto o1) {
        return new Callable<CallableBean>() {
            @Override
            public CallableBean call() {
                ThreadCallableBean.setLocalThreadBean(new CallableBean(company1, o1, null));
                Company company = ThreadCallableBean.getLocalThreadBean().getCompany();
                OtaInfoRefDto o = ThreadCallableBean.getLocalThreadBean().getO();
                OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), o.getUsedPriceModel().name()));
                //BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(mapping.getInnId()));
                OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(
                        new OtaRoomPriceDto(company.getId(), Integer.valueOf(mapping.getRoomTypeId()), o.getOtaInfoId()));
                try {
                    RoomTypeInfo roomTypeInfo = JwXHotelUtil.buildRoomTypeInfo(company,mapping);
                    Result result = jointWisdomARI.updateJsPriceInventory(mapping, roomTypeInfo, priceDto, commission);
                    if (result!=null && !Constants.SUCCESS200.equals(result.getStatus())) {
                        timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), Integer.valueOf(mapping.getRoomTypeId()),
                                Integer.valueOf(mapping.getInnId()), result.getMessage(), TimerRateType.NEW));
                    }
                } catch (Exception e) {
                    timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), Integer.valueOf(mapping.getRoomTypeId()),
                            Integer.valueOf(mapping.getInnId()), "oms 房型为空",TimerRateType.NOT_HOVE_ROUSE));
                    log.error("同步携程房型接口异常" + e);
                }
                return null;
            }
        };
    }

    @Override
    public void updateHotelRoom(OtaInfoRefDto infoRefDto, List<PushRoom> pushRoomList) throws Exception {
        Company company = companyDao.selectCompanyById(infoRefDto.getCompanyId());
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), infoRefDto.getUsedPriceModel().name()));
        for (PushRoom pushRoom : pushRoomList) {
            Integer roomTypeId = pushRoom.getRoomType().getRoomTypeId();
            //查询客栈是否是上架状态
            BangInn bangInn = bangInnDao.selectBangInnByParam(infoRefDto.getCompanyId(), infoRefDto.getOtaInfoId(), pushRoom.getRoomType().getAccountId());
            //验证此房型是不是在数据库存在
            if (bangInn != null) {
                JointWisdomInnRoomMappingDto mapping = jointWisdomInnRoomDao.selectJsInnRooType(company.getId(), bangInn.getInnId(), roomTypeId);
                //满足这些条件 才是之前上架过。
                if (mapping != null && mapping.getSj() == Constants.FC_SJ) {
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(mapping.getCompanyId(), roomTypeId, infoRefDto.getOtaInfoId()));
                    //上架房型 房量 库存
                    RoomTypeInfo roomTypeInfo = JwXHotelUtil.buildRoomTypeInfo(company,mapping);
                    Result result = jointWisdomARI.updateJsPriceInventory(mapping, roomTypeInfo, priceDto, commission);
                    if (!Constants.SUCCESS200.equals(result.getStatus())) {
                        log.error("及时推送失败:" + result.getMessage());
                    }
                } else {
                    log.info("(众荟)此房型还没有上架 roomTypeId:" + pushRoom.getRoomType().getRoomTypeId());
                }
            } else {
                log.info("(众荟)此客栈已经下架AccountId:" + pushRoom.getRoomType().getAccountId());
            }
        }
    }

    @Override
    public void updateHotelFailTimer(OtaInfoRefDto o) {
        String companyId = o.getCompanyId();
        Company company = companyDao.selectCompanyById(companyId);
        List<TimerRatePrice> timerRatePriceList = timerRatePriceDao.selectTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId()));
        List<CtripRoomTypeMapping> list = new ArrayList<>();
        for (TimerRatePrice ratePrice : timerRatePriceList) {
            List<JointWisdomInnRoomMappingDto> mappings = jointWisdomInnRoomDao.selectJwMapping(new JointWisdomInnRoomMappingDto(companyId,ratePrice.getInnId(),null));
            if (!CollectionUtils.isEmpty(mappings)) {
                for (JointWisdomInnRoomMappingDto mapping : mappings) {
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(mapping.getRoomTypeId()), o.getOtaInfoId()));
                    try {
                        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), o.getUsedPriceModel().name()));
                        RoomTypeInfo roomTypeInfo = JwXHotelUtil.buildRoomTypeInfo(company,mapping);
                        Result result = jointWisdomARI.updateJsPriceInventory(mapping, roomTypeInfo, priceDto, commission);
                        if (!Constants.SUCCESS200.equals(result.getStatus())) {
                            timerRatePriceDao.deletedFcTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId(), ratePrice.getInnId(),
                                       Integer.valueOf(mapping.getRoomTypeId())));
                            mapping.setSj(0);
                            jointWisdomInnRoomDao.updateJsRoomInnRooType(mapping);
                        }
                        timerRatePriceDao.deletedFcTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId(),ratePrice.getInnId(), Integer.valueOf(mapping.getRoomTypeId())));
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("ZH updateHotelFailTimer 异常:" + e.getMessage());
                    }
                }

            }
        }

    }

    @Override
    public void updateRoomTypePrice(OtaInfoRefDto infoRefDto, String innId, String companyId, String userId, String json) throws Exception {
        log.info("===================众荟修改价格=======================");
        OtaInnOtaDto otaInnOta = otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(Integer.valueOf(innId),companyId, infoRefDto.getOtaInfoId());
        if (otaInnOta!=null && Constants.FC_SJ == otaInnOta.getSj()){
            Company company = companyDao.selectCompanyById(companyId);
            OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), infoRefDto.getUsedPriceModel().name()));
            List<AddFangPrice> prices = JacksonUtil.json2list(json, AddFangPrice.class);
            if (!CollectionUtils.isEmpty(prices)) {
                OtaRoomPriceDto priceDto = null;
                for (AddFangPrice price : prices) {
                    if (!StringUtils.isEmpty(price.getEndDateStr()) && !StringUtils.isEmpty(price.getStartDateStr()) && price.getPriceChange() != null) {
                        JointWisdomInnRoomMappingDto mappingDto = jointWisdomInnRoomDao.selectJsInnRooType(companyId,Integer.valueOf(innId), price.getRoomTypeId());
                        priceDto = new OtaRoomPriceDto(companyId, price.getRoomTypeId(), infoRefDto.getOtaInfoId());
                        priceDto.build(price,innId,userId);
                        if (mappingDto != null  && mappingDto.getSj() == Constants.FC_SJ) {
                            String room_type = DcUtil.omsRoomTypeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(), Integer.valueOf(innId), price.getRoomTypeId(), CommonApi.checkRoom,Constants.day);
                            List<RoomDetail> roomDetailList = InnRoomHelper.getRoomDetail(room_type);
                            boolean b = tpHolder.checkRooPrice(priceDto.getValue(), roomDetailList,commission);
                            if (b) {
                                RoomTypeInfo roomTypeInfo = JwXHotelUtil.buildRoomTypeInfo(roomDetailList,mappingDto);
                                Result result = jointWisdomARI.updateJsPriceInventory(mappingDto, roomTypeInfo, priceDto, commission);
                                if (result!=null && Constants.SUCCESS200.equals(result.getStatus())) {
                                    otaRoomPriceDao.saveOtaRoomPriceDto(priceDto);
                                } else {
                                    log.info("房型Id" + price.getRoomTypeId() + " 同步失败：" + result.getMessage());
                                    throw new TomsRuntimeException("房型名称:" + price.getRoomTypeName() + " 同步失败：" + result.getMessage());
                                }


                            } else {
                                log.info("房型Id" + price.getRoomTypeId() + " 减小的价格不能低于1元");
                                throw new TomsRuntimeException("房型名称:" + price.getRoomTypeName() + " 减小的价格不能低于1元");
                            }

                        } else {
                            log.info("innId：" + innId + " 房型id" + price.getRoomTypeId() + "还没有上架到众荟");
                            throw new TomsRuntimeException("innId：" + innId + " 房型名称" + price.getRoomTypeName() + "还没有上架到mappingDto");
                        }

                    } else {
                        log.info("innId：" + innId + " 房型id" + price.getRoomTypeId() + " 开始结束时间为空!");
                    }


                }
            }

        }


    }

    @Override
    public Result validatedOTAAccuracy(OtaInfoRefDto infoRefDto) {
        Result result = new Result();
        try {
            otaInfoDao.saveOtaInfo(infoRefDto);
            result.setStatus(Constants.SUCCESS200);
        } catch (Exception e) {
            result.setStatus(Constants.ERROR400);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
