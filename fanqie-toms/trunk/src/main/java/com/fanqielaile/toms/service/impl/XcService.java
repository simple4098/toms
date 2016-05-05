package com.fanqielaile.toms.service.impl;

import com.fanqie.bean.response.RequestResponse;
import com.fanqie.bean.response.RequestResult;
import com.fanqie.core.dto.TBParam;
import com.fanqie.support.CtripConstants;
import com.fanqie.util.DcUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.enums.LogDec;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.enums.TimerRateType;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.ICtripRoomService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.CallableBean;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.holder.TPHolder;
import com.fanqielaile.toms.support.tb.CtripXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.MessageCenterUtils;
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
@Service("xcService")
public class XcService implements ITPService {
    private static final Logger log = LoggerFactory.getLogger(XcService.class);
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

    @Override
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
        String innId = tbParam.getInnId();
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        tpHolder.validate(company, innId, otaInfo.getOtaInfoId());
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
                log.info("xc 客栈" + tbParam.getInnId() + " 绑定");
            } else {
                log.info("xc 客栈" + bangInn.getInnId() + " 已绑定" + " 状态:" + tbParam.isSj());
                TomsUtil.sjModel(tbParam,bangInn,omsInnDto);
                bangInnDao.updateBangInnTp(bangInn);
                //下架状态的时候 要把房仓的宝贝下架掉
                List<CtripRoomTypeMapping> list = ctripRoomTypeMappingDao.selectMapping(new CtripRoomTypeMapping(Constants.FC_SJ,innId,company.getId()));
                if (!CollectionUtils.isEmpty(list)) {
                    ctripRoomService.updateRoomPrice(company,otaInfo,list, tbParam.isSj());
                }
            }
        }else {
            log.info("携程客栈上下架 获取不到客栈基本信息");
        }
    }

    @Override
    public void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {

    }

    @Override
    public void updateHotel(OtaInfoRefDto o, TBParam tbParam) throws Exception {
        log.info("====Xc 同步 start====");
        Company company = companyDao.selectCompanyByCompanyCode(o.getCompanyCode());
        List<CtripRoomTypeMapping> mappings = ctripRoomTypeMappingDao.selectMapping(
                new CtripRoomTypeMapping(Constants.FC_SJ, null, company.getId()));
        if (!CollectionUtils.isEmpty(mappings)) {
            int size = mappings.size();
            int timerThread = size / Constants.timerThread;
            int threadNum = timerThread == 0 ? 1 : timerThread;
            ExecutorService es = Executors.newFixedThreadPool(threadNum);
            CompletionService cs = new ExecutorCompletionService(es);
            for (CtripRoomTypeMapping mapping : mappings) {
                cs.submit(getTask(company, mapping, o));
            }
            es.shutdown();
            TomsUtil.obtNull(mappings);
        }
    }

    private Callable getTask(final Company company1, final CtripRoomTypeMapping mapping, final OtaInfoRefDto o1) {
        return new Callable<CallableBean>() {
            @Override
            public CallableBean call() {
                ThreadCallableBean.setLocalThreadBean(new CallableBean(company1, o1, null));
                Company company = ThreadCallableBean.getLocalThreadBean().getCompany();
                OtaInfoRefDto o = ThreadCallableBean.getLocalThreadBean().getO();
                OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), o.getUsedPriceModel().name()));
                OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(
                        new OtaRoomPriceDto(company.getId(), Integer.valueOf(mapping.getTomRoomTypeId()), o.getOtaInfoId()));
                try {
                    RequestResponse syncRoomInfo = CtripXHotelUtil.syncRoomInfo(company,o, mapping,  priceDto, commission);
                    RequestResult requestResult = syncRoomInfo.getRequestResult();
                    if (syncRoomInfo!=null && !CtripConstants.resultCode.equals(requestResult.getResultCode())) {
                        timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), Integer.valueOf(mapping.getTomRoomTypeId()),
                                Integer.valueOf(mapping.getInnId()), requestResult.getMessage(), TimerRateType.NEW));
                    }
                    if (!CtripConstants.resultCode.equals(requestResult.getResultCode())){
                        timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), Integer.valueOf(mapping.getTomRoomTypeId()),
                                Integer.valueOf(mapping.getInnId()), requestResult.getMessage(),TimerRateType.NOT_HOVE_ROUSE));
                    }
                } catch (Exception e) {
                    log.error("同步携程房型接口异常", e);
                }
                return null;
            }
        };
    }

    @Override
    public void updateHotelRoom(OtaInfoRefDto infoRefDto, List<PushRoom> pushRoomList) throws Exception {
        Company company = companyDao.selectCompanyById(infoRefDto.getCompanyId());
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), infoRefDto.getUsedPriceModel().name()));
        BangInn bangInn = null;
        OtaRoomPriceDto priceDto = null;
        for (PushRoom pushRoom : pushRoomList) {
            Integer roomTypeId = pushRoom.getRoomType().getRoomTypeId();
            //查询客栈是否是上架状态
            bangInn = bangInnDao.selectBangInnByParam(infoRefDto.getCompanyId(), infoRefDto.getOtaInfoId(), pushRoom.getRoomType().getAccountId());
            //验证此房型是不是在数据库存在
            if (bangInn != null && Constants.FC_SJ.equals(bangInn.getSj())) {
                CtripRoomTypeMapping mapping = ctripRoomTypeMappingDao.selectMappingInnIdAndRoomTypeId(String.valueOf(bangInn.getInnId()), String.valueOf(roomTypeId));
                //满足这些条件 才是之前上架过。
                if (mapping != null && !StringUtils.isEmpty(mapping.getTomRoomTypeId()) && mapping.getSj() == Constants.FC_SJ) {
                    priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(mapping.getCompanyId(), roomTypeId, infoRefDto.getOtaInfoId()));
                    //上架房型 房量 库存
                    RequestResponse syncRoomInfo = CtripXHotelUtil.syncRoomInfo(company,infoRefDto, mapping, priceDto, commission);
                    if (CtripConstants.resultCode.equals(syncRoomInfo.getRequestResult().getResultCode())) {
                        //记录日志
                        MessageCenterUtils.savePushTomsLog(OtaType.XC, Integer.valueOf(mapping.getInnId()), Integer.valueOf(mapping.getTomRoomTypeId()),
                                null, LogDec.RoomType_PHSH_PRICE, "及时修改房型价格");
                    }else {
                        log.error("及时推送失败:" + syncRoomInfo.getRequestResult().getMessage());
                    }
                } else {
                    log.info("(携程)此房型还没有上架 roomTypeId:" + pushRoom.getRoomType().getRoomTypeId());
                }
            } else {
                log.info("(携程)此客栈已经下架AccountId:" + pushRoom.getRoomType().getAccountId());
            }
        }
        TomsUtil.obtNull(pushRoomList);
    }

    @Override
    public void updateHotelFailTimer(OtaInfoRefDto o) {
        String companyId = o.getCompanyId();
        Company company = companyDao.selectCompanyById(companyId);
        List<TimerRatePrice> timerRatePriceList = timerRatePriceDao.selectTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId()));
        List<CtripRoomTypeMapping> list = new ArrayList<>();
        for (TimerRatePrice ratePrice : timerRatePriceList) {
            List<CtripRoomTypeMapping> mappings = ctripRoomTypeMappingDao.selectMapping(
                    new CtripRoomTypeMapping(Constants.FC_SJ, String.valueOf(ratePrice.getInnId()), company.getId()));
            if (!CollectionUtils.isEmpty(mappings)) {
                for (CtripRoomTypeMapping mapping : mappings) {
                    //BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(mapping.getInnId()));
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(mapping.getTomRoomTypeId()), o.getOtaInfoId()));
                    try {
                        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), o.getUsedPriceModel().name()));
                        RequestResponse syncRoomInfo = CtripXHotelUtil.syncRoomInfo(company,o, mapping, priceDto, commission);
                        RequestResult requestResult = syncRoomInfo.getRequestResult();
                        if (requestResult!=null && CtripConstants.resultCode.equals(requestResult.getResultCode())) {
                            timerRatePriceDao.deletedFcTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId(), ratePrice.getInnId(),
                                       Integer.valueOf(mapping.getTomRoomTypeId())));
                        }else {
                            timerRatePriceDao.deletedFcTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId(),ratePrice.getInnId(), Integer.valueOf(mapping.getTomRoomTypeId())));
                            list.add(mapping);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("FC updateHotelFailTimer 异常:" + e.getMessage());
                    }

                }

            }
        }
        ctripRoomService.updateRoomPrice(company,o,list, false);
    }

    @Override
    public void updateRoomTypePrice(OtaInfoRefDto infoRefDto, String innId, String companyId, String userId, String json) throws Exception {
        log.info("===================携程修改价格=======================");
        Company company = companyDao.selectCompanyById(companyId);
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), infoRefDto.getUsedPriceModel().name()));
        List<AddFangPrice> prices = JacksonUtil.json2list(json, AddFangPrice.class);
        if (!CollectionUtils.isEmpty(prices)) {
            OtaRoomPriceDto priceDto = null;
            for (AddFangPrice price : prices) {
                if (!StringUtils.isEmpty(price.getEndDateStr()) && !StringUtils.isEmpty(price.getStartDateStr()) && price.getPriceChange() != null) {
                    CtripRoomTypeMapping ctripRoomTypeMapping = ctripRoomTypeMappingDao.selectMappingInnIdAndRoomTypeId(innId,String.valueOf(price.getRoomTypeId()));
                    priceDto = new OtaRoomPriceDto(companyId, price.getRoomTypeId(), infoRefDto.getOtaInfoId());
                    priceDto.build(price,innId,userId);
                    priceDto.setRoomTypeName(price.getRoomTypeName());
                    if (ctripRoomTypeMapping != null && !StringUtils.isEmpty(ctripRoomTypeMapping.getTomRoomTypeId())
                            && ctripRoomTypeMapping.getSj() == Constants.FC_SJ) {
                        String room_type = DcUtil.omsRoomTypeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(), Integer.valueOf(innId), price.getRoomTypeId(), CommonApi.checkRoom,60);
                        List<RoomDetail> roomDetailList = InnRoomHelper.getRoomDetail(room_type);
                        boolean b = tpHolder.checkRooPrice(priceDto.getValue(), roomDetailList,commission);
                        if (b) {
                            RequestResponse response = CtripXHotelUtil.syncRoomInfo(company,infoRefDto, ctripRoomTypeMapping , priceDto, commission);
                            RequestResult requestResult = response.getRequestResult();
                            if (requestResult!=null && CtripConstants.resultCode.equals(requestResult.getResultCode())) {
                                otaRoomPriceDao.saveOtaRoomPriceDto(priceDto);
                                //记录日志
                                MessageCenterUtils.savePushTomsLog(OtaType.XC, Integer.valueOf(innId), priceDto.getRoomTypeId(), userId, LogDec.MT_RoomType_Price,
                                        "startDate:" + priceDto.getStartDateStr() + " endDate:" + priceDto.getEndDateStr() + " price:" + priceDto.getValue());
                            } else {
                                log.info("房型Id" + price.getRoomTypeId() + " 同步失败：" + requestResult.getMessage());
                                throw new TomsRuntimeException("房型名称:" + price.getRoomTypeName() + " 同步失败：" + requestResult.getMessage());
                            }
                        } else {
                            log.info("房型Id" + price.getRoomTypeId() + " 减小的价格不能低于1元");
                            throw new TomsRuntimeException("房型名称:" + price.getRoomTypeName() + " 减小的价格不能低于1元");
                        }

                    } else {
                        log.info("innId：" + innId + " 房型id" + price.getRoomTypeId() + "还没有上架到携程");
                        throw new TomsRuntimeException("innId：" + innId + " 房型名称" + price.getRoomTypeName() + "还没有上架到携程");
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

    @Override
    public void sellingRoomType(String from , String to,OtaInfoRefDto otaInfoRefDto) {
        String companyId = otaInfoRefDto.getCompanyId();
        Company company = companyDao.selectCompanyById(companyId);
        try {
            List<SellingRoomType> roomTypes = InnRoomHelper.obtSellingRoomType(from,to,company);
            List<CtripRoomTypeMapping> list = new ArrayList<CtripRoomTypeMapping>();
            CtripRoomTypeMapping mapping = null;
            log.info("========携程下架房型=========");
            for (SellingRoomType sellingRoomType:roomTypes){
                if (!CollectionUtils.isEmpty(sellingRoomType.getOtaRoomTypeId())){
                    for (Integer roomTypeId:sellingRoomType.getOtaRoomTypeId()){
                        mapping = ctripRoomTypeMappingDao.selectMappingInnIdAndRoomTypeId(sellingRoomType.getInnId().toString(), roomTypeId.toString());
                        if (mapping!=null && Constants.FC_SJ.equals(mapping.getSj())){
                            //记录日志
                            //MessageCenterUtils.savePushTomsLog(OtaType.XC, Integer.valueOf(mapping.getInnId()), Integer.valueOf(mapping.getTomRoomTypeId()),null, LogDec.XJ_RoomType,"");
                            list.add(mapping);
                        }else {
                            log.info("此房型在携程没有上架，客栈id："+sellingRoomType.getInnId()+" 房型id："+roomTypeId);
                        }
                    }
                }
            }
            if (!CollectionUtils.isEmpty(list)) {
                ctripRoomService.updateRoomPrice(company, otaInfoRefDto, list, false);
            }
        } catch (Exception e) {
            log.error("携程下架房型失败",e);
        }
    }
}

