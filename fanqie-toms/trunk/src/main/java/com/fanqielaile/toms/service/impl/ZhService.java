package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.TBParam;
import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqie.util.DcUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.zh.JointWisdomMappingDto;
import com.fanqielaile.toms.enums.LogDec;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.enums.TimerRateType;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.model.fc.OtaRatePlan;
import com.fanqielaile.toms.service.IJointWisdomARI;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.CallableBean;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.holder.TPHolder;
import com.fanqielaile.toms.support.tb.JwXHotelUtil;
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
        tbParam.setOtaId(company.getOtaId().toString());
        String inn_info = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId() != null ? tbParam.getAccountId() : tbParam.getAccountIdDi(), CommonApi.INN_INFO);
        InnDto omsInnDto = InnRoomHelper.getInnInfo(inn_info);
        OtaRatePlan otaRatePlan = ratePlanDao.selectRatePlanByCompanyIdOtaIdDefault(company.getId(), otaInfo.getOtaInfoId());
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(),otaInfo.getUsedPriceModel().name()));
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
        OtaInnOtaDto otaInnOta = otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(bangInn.getInnId(), company.getId(), otaInfoId);
        if (!tbParam.isSj() ){
            updateFcXj(company,otaInfoId,innId,tbParam,bangInn,commission,otaInnOta,null,otaRatePlan);
        }else {
            if (omsInnDto != null) {
                omsInnDto.setInnId(innId);
                //BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
                BangInnDto bangInnDto = null;
                if (bangInn == null) {
                    bangInnDto = BangInnDto.toDto(company.getId(), tbParam, omsInnDto);
                    bangInnDao.createBangInn(bangInnDto);
                } else {
                    log.info("zh 客栈" + bangInn.getInnId() + " 已绑定" + " 状态:" + tbParam.isSj());
                    //OtaInnOtaDto otaInnOta = otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(bangInn.getInnId(), company.getId(), otaInfoId);
                    TomsUtil.sjModel(tbParam, bangInn, omsInnDto);
                    bangInnDao.updateBangInnTp(bangInn);
                    if (tbParam.isUpDown()){
                        //bang_inn   ota_inn_ota
                        updateOtaBang(bangInn,tbParam,otaInnOta,company,omsInnDto.getInnName(),otaInfoId);
                        //获取客栈的房型房态信息 60天
                        List<RoomTypeInfo> roomTypeInfoList = InnRoomHelper.obtRoomTypeInfoList(company, tbParam);
                        if (!CollectionUtils.isEmpty(roomTypeInfoList)){
                            JointWisdomMappingDto jointWisdomInnRoom = null;
                            OtaRoomPriceDto priceDto = null;
                            Result result = null;
                            for (RoomTypeInfo roomTypeInfo:roomTypeInfoList){
                                priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(roomTypeInfo.getRoomTypeId()), otaInfoId));
                                jointWisdomInnRoom = JwXHotelUtil.buildMapping(priceDto, roomTypeInfo, company.getId(), Integer.valueOf(innId), String.valueOf(company.getOtaId()), otaInfoId, otaRatePlan.getRatePlanCode(), tbParam.isSj());
                                result = jointWisdomARI.updateJsPriceInventory(jointWisdomInnRoom, roomTypeInfo, priceDto, commission);
                                if (Constants.SUCCESS200 == result.getStatus()){
                                    JointWisdomInnRoomMappingDto jw = jointWisdomInnRoomDao.selectJsInnRooType(company.getId(),Integer.valueOf(innId), roomTypeInfo.getRoomTypeId());
                                    if (jw==null ){
                                        jointWisdomInnRoomDao.insertJsRoomInnRooType(jointWisdomInnRoom);
                                    }else {
                                        jointWisdomInnRoom.setId(jw.getId());
                                        jointWisdomInnRoomDao.updateJsRoomInnRooType(jointWisdomInnRoom);
                                    }
                                    //记录日志
                                    MessageCenterUtils.savePushTomsLog(OtaType.ZH, Integer.valueOf(innId), roomTypeInfo.getRoomTypeId(), null, LogDec.RoomType_PHSH, tbParam.isSj() ? "上架房型" : "下架房型");
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 房仓 更新 bangInn  OtaInnOta
     * @param bangInn 绑定客栈
     * @param tbParam
     * @param otaInnOta
     * @param company  恭喜信息
     * @param innName 客栈名称
     * @param otaInfoId 渠道id
     */
    private  void updateOtaBang(BangInn bangInn,TBParam tbParam, OtaInnOtaDto otaInnOta,Company company, String innName,String otaInfoId){

        if (otaInnOta == null) {
            otaInnOta = OtaInnOtaDto.toDto(company.getOtaId() + "_" + bangInn.getInnId(), innName, company.getId(), tbParam, bangInn.getId(), otaInfoId);
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
    }

    private  void  updateFcXj(Company company,String otaInfoId,String innId,TBParam tbParam,BangInn bangInn,
                              OtaCommissionPercentDto commission,OtaInnOtaDto otaInnOta,String innName,OtaRatePlan otaRatePlan)throws Exception{
        OtaRoomPriceDto price = null;
        JointWisdomMappingDto jointWisdomInnRoom = null;
        RoomTypeInfo roomTypeInfo= null;
        String room_type = null;
        Result result = null;
        List<JointWisdomInnRoomMappingDto> roomMappings = jointWisdomInnRoomDao.selectJwMapping(new JointWisdomInnRoomMappingDto(company.getId(), Integer.valueOf(innId), null));
        for (JointWisdomInnRoomMappingDto mappingDto:roomMappings){
            room_type = DcUtil.omsRoomTypeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(), mappingDto.getInnId(), mappingDto.getRoomTypeId(), CommonApi.checkRoom, Constants.day);
            List<RoomDetail> roomDetailList = InnRoomHelper.getRoomDetail(room_type);
            roomTypeInfo= TomsUtil.buildRoomTypeInfo(roomDetailList,mappingDto.getRoomTypeId(),mappingDto.getRoomTypeName());
            price = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(mappingDto.getRoomTypeId()), otaInfoId));
            jointWisdomInnRoom = JwXHotelUtil.buildMapping(price, roomTypeInfo, company.getId(), Integer.valueOf(innId), String.valueOf(company.getOtaId()), otaInfoId, otaRatePlan.getRatePlanCode(), tbParam.isSj());
            result = jointWisdomARI.updateJsPriceInventory(jointWisdomInnRoom, roomTypeInfo, price, commission);
            if (Constants.SUCCESS200 == result.getStatus()){
                updateOtaBang(bangInn, tbParam, otaInnOta, company,  innName, otaInfoId);
                jointWisdomInnRoom.setId(mappingDto.getId());
                jointWisdomInnRoomDao.updateJsRoomInnRooType(jointWisdomInnRoom);
                //记录日志
                MessageCenterUtils.savePushTomsLog(OtaType.ZH, Integer.valueOf(innId), roomTypeInfo.getRoomTypeId(), null, LogDec.RoomType_PHSH, tbParam.isSj() ? "上架房型" : "下架房型");
            }
        }
    }
    @Override
    public void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {

    }

    @Override
    public void updateHotel(OtaInfoRefDto o, TBParam tbParam) throws Exception {
        log.info("====zh 同步 start====");
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
            es.shutdown();
            TomsUtil.obtNull(jointWisdomInnRoomMappings);
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
                OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(
                        new OtaRoomPriceDto(company.getId(), Integer.valueOf(mapping.getRoomTypeId()), o.getOtaInfoId()));
                try {
                    RoomTypeInfo roomTypeInfo = JwXHotelUtil.buildRoomTypeInfo(company, mapping);
                    if (CollectionUtils.isEmpty(roomTypeInfo.getRoomDetail())){
                        timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), Integer.valueOf(mapping.getRoomTypeId()),
                                Integer.valueOf(mapping.getInnId()), "oms 房型为空", TimerRateType.NOT_HOVE_ROUSE));
                    }else {
                        Result result = jointWisdomARI.updateJsPriceInventory(mapping, roomTypeInfo, priceDto, commission);
                        if (result!=null && !Constants.SUCCESS200.equals(result.getStatus())) {
                            timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), Integer.valueOf(mapping.getRoomTypeId()),
                                    Integer.valueOf(mapping.getInnId()), result.getMessage(), TimerRateType.NEW));
                            //记录日志
                            MessageCenterUtils.savePushTomsLog(OtaType.ZH, Integer.valueOf(mapping.getInnId()), roomTypeInfo.getRoomTypeId(), null, LogDec.RoomType_PHSH_PRICE, "定时任务执行,房价库存房量...");
                        }
                    }

                } catch (Exception e) {
                    log.error("同步众荟房态异常" + e);
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
                JointWisdomInnRoomMappingDto mapping = jointWisdomInnRoomDao.selectJsInnRooType(company.getId(), bangInn.getInnId(), roomTypeId);
                //满足这些条件 才是之前上架过。
                if (mapping != null && mapping.getSj() == Constants.FC_SJ) {
                    priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(mapping.getCompanyId(), roomTypeId, infoRefDto.getOtaInfoId()));
                    //上架房型 房量 库存
                    RoomTypeInfo roomTypeInfo = JwXHotelUtil.buildRoomTypeInfo(company, mapping);
                    Result result = jointWisdomARI.updateJsPriceInventory(mapping, roomTypeInfo, priceDto, commission);
                    if (Constants.SUCCESS200.equals(result.getStatus())) {
                        //记录日志
                        MessageCenterUtils.savePushTomsLog(OtaType.ZH, bangInn.getInnId(), roomTypeInfo.getRoomTypeId(), null, LogDec.RoomType_PHSH_PRICE, "及时推送房型房价库存");
                    }else {
                        log.error("及时推送失败:" + result.getMessage());
                    }
                } else {
                    log.info("(众荟)此房型还没有上架 roomTypeId:" + pushRoom.getRoomType().getRoomTypeId());
                }
            } else {
                log.info("(众荟)此客栈已经下架AccountId:" + pushRoom.getRoomType().getAccountId());
            }
        }
        TomsUtil.obtNull(pushRoomList);
    }

    @Override
    public void updateHotelFailTimer(OtaInfoRefDto o) {
        log.info("===============ZH START updateHotelFailTimer =================");
        String companyId = o.getCompanyId();
        Company company = companyDao.selectCompanyById(companyId);
        List<TimerRatePrice> timerRatePriceList = timerRatePriceDao.selectTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId()));
        Result result = null;
        for (TimerRatePrice ratePrice : timerRatePriceList) {
            List<JointWisdomInnRoomMappingDto> mappings = jointWisdomInnRoomDao.selectJwMapping(new JointWisdomInnRoomMappingDto(companyId,ratePrice.getInnId(),null));
            if (!CollectionUtils.isEmpty(mappings)) {
                log.info("众荟mappings:"+mappings.size());
                for (JointWisdomInnRoomMappingDto mapping : mappings) {
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(mapping.getRoomTypeId()), o.getOtaInfoId()));
                    try {
                        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), o.getUsedPriceModel().name()));
                        log.info("众荟下架 updateHotelFailTimer innId:"+mapping.getInnId()+" roomTypeId:"+mapping.getRoomTypeId());
                        RoomTypeInfo roomTypeInfo = JwXHotelUtil.buildRoomTypeInfo(company,mapping);
                        if (TimerRateType.NOT_HOVE_ROUSE.equals(ratePrice.getRateType())){
                            mapping.setSj(0);
                            result = jointWisdomARI.xjRoomStatus(mapping, roomTypeInfo);
                            log.info("众荟下架 updateHotelFailTimer status:"+result.getStatus());
                        }else {
                            result = jointWisdomARI.updateJsPriceInventory(mapping, roomTypeInfo, priceDto, commission);
                            log.info("众荟重新更新房价 房态 updateHotelFailTimer status:" + result.getStatus() );
                        }
                        if (Constants.SUCCESS200.equals(result.getStatus())) {
                            jointWisdomInnRoomDao.updateJsRoomInnRooType(mapping);
                        }
                        timerRatePriceDao.deletedFcTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId(),ratePrice.getInnId(), Integer.valueOf(mapping.getRoomTypeId())));
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("ZH updateHotelFailTimer 异常:",e);
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
                            String room_type = DcUtil.omsRoomTypeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(), Integer.valueOf(innId), price.getRoomTypeId(), CommonApi.checkRoom, Constants.day);
                            List<RoomDetail> roomDetailList = InnRoomHelper.getRoomDetail(room_type);
                            boolean b = tpHolder.checkRooPrice(priceDto.getValue(), roomDetailList,commission);
                            if (b) {
                                RoomTypeInfo roomTypeInfo = JwXHotelUtil.buildRoomTypeInfo(roomDetailList, mappingDto);
                                Result result = jointWisdomARI.updateJsPriceInventory(mappingDto, roomTypeInfo, priceDto, commission);
                                if (result!=null && Constants.SUCCESS200.equals(result.getStatus())) {
                                    otaRoomPriceDao.saveOtaRoomPriceDto(priceDto);
                                    //记录日志
                                    MessageCenterUtils.savePushTomsLog(OtaType.ZH, Integer.valueOf(innId), priceDto.getRoomTypeId(), userId, LogDec.MT_RoomType_Price,
                                            "startDate:" + priceDto.getStartDateStr() + " endDate:" + priceDto.getEndDateStr() + " price:" + priceDto.getValue());
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

    @Override
    public void sellingRoomType(String from , String to,OtaInfoRefDto otaInfoRefDto) {
        String companyId = otaInfoRefDto.getCompanyId();
        Company company = companyDao.selectCompanyById(companyId);
        log.info("========众荟下架房型=========");
        try {
            List<SellingRoomType> roomTypes = InnRoomHelper.obtSellingRoomType(from, to, company);
            if (!CollectionUtils.isEmpty(roomTypes)){
                JointWisdomInnRoomMappingDto mappingDto=null;
                for (SellingRoomType sellingRoomType:roomTypes){
                    if (!CollectionUtils.isEmpty(sellingRoomType.getOtaRoomTypeId())){
                        for (Integer roomTypeId:sellingRoomType.getOtaRoomTypeId()){
                            mappingDto = jointWisdomInnRoomDao.selectJsInnRooType(companyId, sellingRoomType.getInnId(), roomTypeId);
                            if (mappingDto!=null && Constants.FC_SJ.equals(mappingDto.getSj())){
                                String room_type = DcUtil.omsRoomTypeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(),
                                        sellingRoomType.getInnId(), roomTypeId, CommonApi.checkRoom, Constants.day);
                                List<RoomDetail> roomDetails = InnRoomHelper.getRoomDetail(room_type);
                                RoomTypeInfo roomTypeInfo = JwXHotelUtil.buildRoomTypeInfo(roomDetails, mappingDto);
                                mappingDto.setSj(0);
                                Result result = jointWisdomARI.xjRoomStatus(mappingDto,roomTypeInfo);
                                if (Constants.SUCCESS200 == result.getStatus()){
                                    jointWisdomInnRoomDao.updateJsRoomInnRooType(mappingDto);
                                }
                            }else {
                                log.info("此房型在众荟没有上架，客栈id："+sellingRoomType.getInnId()+" 房型id："+roomTypeId);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new TomsRuntimeException(e);
        }
    }
}
