package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.enums.LogDec;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.enums.TimerRateType;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.CallableBean;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.holder.TPHolder;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.MessageCenterUtils;
import com.fanqielaile.toms.support.util.ThreadCallableBean;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.taobao.api.domain.Rate;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoom;
import com.taobao.api.domain.XRoomType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * DESC : 添加/获取/更新 酒店
 * @author : 番茄木-ZLin
 * @data : 2015/6/23
 * @version: v1.0.0
 */
@Service("tbService")
/*@LogModule("TP 推酒店房型service")*/
public class TBService implements ITPService {
    private static  final Logger log = LoggerFactory.getLogger(TBService.class);
    @Resource
    private CompanyDao companyDao;
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IOtaPriceModelDao priceModelDao;
    @Resource
    private IOtaTaoBaoAreaDao taoBaoAreaDao;
    @Resource
    private IOtaBangInnRoomDao otaBangInnRoomDao;
    @Resource
    private IOtaInnRoomTypeGoodsDao goodsDao;
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


    /**
     * 想淘宝添加/更新酒店
     * @param tbParam 参数
     */
    @Override
    /*@Log(descr ="酒店更新、增加")*/
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        Integer innId = Integer.valueOf(tbParam.getInnId());
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), innId);
        tpHolder.validate(company,tbParam.getInnId(),otaInfo.getOtaInfoId());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        String inn_info = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId()!=null?tbParam.getAccountId():tbParam.getAccountIdDi(), CommonApi.INN_INFO);
        XHotel xHotel = null;
        OtaPriceModelDto otaPriceModel = null;
        OtaInnOtaDto otaInnOta = null;
        OtaTaoBaoArea andArea = null;
        if (!tbParam.isSj()){
            otaInnOta = otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(innId,company.getId(),otaInfo.getOtaInfoId());
            if (otaInnOta!=null){
                otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
                List<OtaInnRoomTypeGoodsDto> list = goodsDao.selectGoodsByInnIdAndCompany(innId,company.getId());
                TBXHotelUtil.roomRoomNumZeroUpdate(list,otaInfo);
                bangInnDao.updateSjBangInn(innId,company.getId(),tbParam.isSj());
                otaInnOtaDao.updateOtaInnOta(otaInnOta);
            }
        }else {
            //客栈
            InnDto omsInnDto = InnRoomHelper.getInnInfo(inn_info);
            if (omsInnDto != null) {
                omsInnDto.setInnId(tbParam.getInnId());
                if (!StringUtils.isEmpty(omsInnDto.getCity())) {
                    andArea = taoBaoAreaDao.findCityAndArea(omsInnDto.getCity());
                }
                if (!StringUtils.isEmpty(omsInnDto.getCounty()) && andArea != null) {
                    OtaTaoBaoArea countyArea = taoBaoAreaDao.findCountyAndCity(andArea.getCityCode(), omsInnDto.getCounty());
                    if (countyArea != null) {
                        andArea.setAreaCode(countyArea.getAreaCode());
                    }
                }
                //推客栈、如果存在再获取客栈
                if (tbParam.getAccountId() != null) {
                    log.info("========开始推客栈【" + omsInnDto.getBrandName() + "[" + omsInnDto.getInnId() + "]" + "】==============");
                    xHotel = TBXHotelUtil.hotelAddOrUpdate(otaInfo, omsInnDto, andArea);
                    if (xHotel != null) {
                        MessageCenterUtils.savePushTomsLog(OtaType.TB, innId, null, null, LogDec.INN_PUSH, "hid:" + xHotel.getHid() + omsInnDto.toString());
                        otaInnOta = otaInnOtaDao.selectOtaInnOtaByHid(xHotel.getHid(), company.getId(), otaInfo.getOtaInfoId());
                        //未绑定
                        BangInnDto bangInnDto = null;
                        if (bangInn == null) {
                            bangInnDto = BangInnDto.toDto(company.getId(), tbParam, omsInnDto);
                            bangInnDao.createBangInn(bangInnDto);
                            //已绑定
                        } else {
                            BangInnDto.toUpdateDto(bangInn, tbParam, omsInnDto);
                            bangInnDao.updateBangInnTp(bangInn);
                        }
                        String bangInnId = bangInn == null ? bangInnDto.getUuid() : bangInn.getId();
                        if (otaInnOta == null) {
                            otaInnOta = OtaInnOtaDto.toDto(String.valueOf(xHotel.getHid()), omsInnDto.getInnName(), company.getId(), tbParam, bangInnId, otaInfo.getOtaInfoId());
                            otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
                            otaInnOtaDao.saveOtaInnOta(otaInnOta);
                            otaPriceModel = OtaPriceModelDto.toDto(otaInnOta.getUuid());
                            priceModelDao.savePriceModel(otaPriceModel);
                        } else {
                            otaPriceModel = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
                            otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
                            otaInnOtaDao.updateOtaInnOta(otaInnOta);
                        }
                        //添加更新宝贝
                        updateOrAddRoom(tbParam, otaInfo, otaPriceModel, otaInnOta, andArea);
                    } else {
                        throw new Exception(" 推送淘宝客栈失败!");
                    }
                    //绑定底价的客栈
                } else {
                    //未绑定
                    BangInnDto bangInnDto = null;
                    if (bangInn == null) {
                        bangInnDto = BangInnDto.toDto(company.getId(), tbParam, omsInnDto);
                        bangInnDao.createBangInn(bangInnDto);
                        //已绑定
                    } else {
                        BangInnDto.toUpdateDiDto(bangInn, tbParam, omsInnDto);
                        if (!tbParam.isSj()) {
                            bangInn.setAccountIdDi(null);
                        } else {
                            bangInn.setSj(1);
                        }
                        bangInnDao.updateBangInnTp(bangInn);
                    }
                }
            } else {
                log.info("无客栈信息!");
            }
        }

    }

    //添加更新宝贝
    private   void updateOrAddRoom(TBParam tbParam, OtaInfoRefDto otaInfo, OtaPriceModelDto otaPriceModel, OtaInnOtaDto otaInnOta, OtaTaoBaoArea andArea)throws Exception{
        String otaPriceModelId = TomsUtil.obtOtaPriceModelId(otaPriceModel);
        String otaInnOtaId=TomsUtil.obtOtaInnOtaId(otaInnOta);
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), otaInfo.getUsedPriceModel().name()));
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.ROOM_TYPE);
        String roomStatus = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.roomStatus);
        List<RoomTypeInfo> list = InnRoomHelper.getRoomTypeInfo(room_type);
        List<RoomStatusDetail> statusDetails = InnRoomHelper.getRoomStatus(roomStatus);
        InnRoomHelper.updateRoomTypeInfo(list,statusDetails);
        //房型
        if (!CollectionUtils.isEmpty(list)){
            for (RoomTypeInfo r:list){
                log.info("========开始推客栈房型【"+r.getRoomTypeName()+"["+r.getRoomTypeId()+"]"+"】==============");
                XRoomType xRoomType = TBXHotelUtil.addRoomType(tbParam.getInnId(), Long.valueOf(otaInnOta.getWgHid()), r, otaInfo);
                log.info("updateOrAddRoom xRoomType:" + xRoomType);
                if (xRoomType!=null){
                    OtaBangInnRoomDto otaBangInnRoomDto = otaBangInnRoomDao.selectBangInnRoomByRidAndCompanyId(String.valueOf( xRoomType.getRid()),company.getId());
                    if (otaBangInnRoomDto==null){
                        OtaBangInnRoomDto innRoomDto = OtaBangInnRoomDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), r.getRoomTypeName(), company.getId(), otaPriceModelId, otaInnOtaId, xRoomType.getRid());
                        otaBangInnRoomDao.saveBangInnRoom(innRoomDto);
                    }
                    //添加商品
                    Long gid = TBXHotelUtil.roomAddOrUpdate(r.getRoomTypeId(), r, otaInfo, tbParam.getStatus(), otaInnOta, andArea);
                    //创建酒店rp
                    Long rpid = TBXHotelUtil.ratePlanAddOrUpdate(otaInfo, r);
                    OtaInnRoomTypeGoodsDto innRoomTypeGoodsDto = goodsDao.findRoomTypeByRid(xRoomType.getRid());
                    if (innRoomTypeGoodsDto==null){
                        OtaInnRoomTypeGoodsDto goodsDto = OtaInnRoomTypeGoodsDto.toDto(tbParam.getInnId(), r.getRoomTypeId(), rpid, gid, company.getId(), otaInnOtaId, String.valueOf(xRoomType.getRid()));
                        goodsDao.saveRoomTypeGoodsRp(goodsDto);
                    }else {
                        if (gid!=null){
                            innRoomTypeGoodsDto.setGid(String.valueOf(gid));
                        }
                        if (rpid!=null){
                            innRoomTypeGoodsDto.setRpid(String.valueOf(rpid));
                        }
                        if (xRoomType.getRid()!=null){
                            innRoomTypeGoodsDto.setRid(String.valueOf(xRoomType.getRid()));
                        }
                        goodsDao.updateRoomTypeGoodsProductDate(innRoomTypeGoodsDto);
                    }
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), r.getRoomTypeId(), otaInfo.getOtaInfoId()));
                    TBXHotelUtil.rateAddOrUpdate(otaInfo, gid, rpid, r, otaPriceModel, !tbParam.isSj(), priceDto,commission);
                    //记录日志
                    MessageCenterUtils.savePushTomsLog(OtaType.TB, Integer.valueOf(tbParam.getInnId()), r.getRoomTypeId(), null, LogDec.RoomType_PHSH, "gid:" + gid + " rpid:" + rpid);
                }else {
                   throw new Exception(" 推房型失败~");
                }
            }
        }else {
            throw new Exception("无房型信息!");
        }
    }
    @Override
   /* @Log(descr ="酒店酒店删除、解绑")*/
    public void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
    }

    @Override
    public void updateHotel(OtaInfoRefDto o, TBParam tbParam) throws Exception {
        log.info("====同步 start====");
        Company company = companyDao.selectCompanyByCompanyCode(o.getCompanyCode());
        tbParam.setCompanyCode(o.getCompanyCode());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        tbParam.setSj(true);
        List<BangInnDto> bangInnDtoList =  bangInnDao.selectBangInnByCompanyIdSj(company.getId(),o.getOtaInfoId());
         for (int i=0;i<6;i++){
            bangInnDtoList.addAll(bangInnDtoList);
        }
        log.info("定时任务数据大小："+bangInnDtoList.size());
        if (!CollectionUtils.isEmpty(bangInnDtoList)){
            List<ProxyInns> proxyList = TomsUtil.toProxyInns(bangInnDtoList,company);
            int size = bangInnDtoList.size();
            int timerThread = size/Constants.timerThread;
            int threadNum = timerThread==0?1:timerThread;
            ExecutorService es = Executors.newFixedThreadPool(threadNum);
            CompletionService cs = new ExecutorCompletionService(es);
            OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), o.getUsedPriceModel().name()));
            for (ProxyInns proxyInns:proxyList){
                cs.submit(getTask(company, o,  proxyInns,commission));
            }
            es.shutdown();
            TomsUtil.obtNull(proxyList);
            TomsUtil.obtNull(bangInnDtoList);
        }
    }

    private Callable getTask(final Company company, final OtaInfoRefDto o,  final ProxyInns proxyInns,final  OtaCommissionPercentDto commission) {
        return new Callable<CallableBean>() {
            @Override
            public CallableBean call()  {
                log.info(" url:" + proxyInns.getRoomTypeUrl());
                try {
                    long start = new Date().getTime();
                    List<RoomTypeInfo> list = InnRoomHelper.getRoomTypeInfo( proxyInns.getRoomTypeUrl());
                    List<RoomStatusDetail> roomStatusDetails = InnRoomHelper.getRoomStatus( proxyInns.getRoomStatusUrl());
                    InnRoomHelper.updateRoomTypeInfo(list,roomStatusDetails);
                    log.info("=======start=======");
                        //房型
                        if (list != null) {
                            OtaRoomPriceDto priceDto = null;
                            String inventoryRate = null;
                            String inventory = null;
                            String gidAndRpId = null;
                            Long gId = null;
                            Rate rate = null;
                            XRoom xRoom = null;
                            for (RoomTypeInfo r : list) {
                                OtaInnRoomTypeGoodsDto good = goodsDao.selectGoodsByRoomTypeIdAndCompany(o.getCompanyId(), r.getRoomTypeId());
                                rate = TBXHotelUtil.rateGet(o, r);
                                xRoom = TBXHotelUtil.roomGet(r.getRoomTypeId(), o);
                                if (good!=null && rate!=null && xRoom!=null){
                                    priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), r.getRoomTypeId(), o.getOtaInfoId()));
                                    inventoryRate = TomsUtil.obtInventoryRate(r, new OtaPriceModelDto(new BigDecimal(1)), priceDto, commission);
                                    inventory = TomsUtil.obtInventory(r);
                                    log.info("roomId:"+r.getRoomTypeId()+" goodId:"+good.getGid()+" xRoom:"+xRoom.getGid() +" inventory:"+inventory+" inventoryRate:"+inventoryRate);
                                    rate.setInventoryPrice(inventoryRate);
                                    xRoom.setInventory(inventory);
                                    gidAndRpId = TBXHotelUtil.rateUpdate(o, r, rate);
                                    gId = TBXHotelUtil.roomUpdate(o, xRoom);
                                    if (StringUtils.isEmpty(gidAndRpId) || gId==null){
                                        timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), r.getRoomTypeId(),proxyInns.getInnId(),"gidAndRpId is "+gidAndRpId+" gId is "+gId, TimerRateType.NEW));
                                    }
                                    log.info("客栈id"+proxyInns.getInnId()+" roomTypeId:"+r.getRoomTypeId()+" xRoom" + xRoom.getGid()+ " rate:" + rate.getGid());
                                }else {
                                    log.info("保存信息："+company.getId()+"客栈id"+proxyInns.getInnId()+" otaInfoId:"+o.getOtaInfoId()+" roomTypeId:"+r.getRoomTypeId());
                                    timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), r.getRoomTypeId(),proxyInns.getInnId(),"rate is "+rate+" xRoom is "+xRoom, TimerRateType.NEW));
                                }
                            }
                            log.info("=======end======= 消耗时间："+(System.currentTimeMillis()-start));
                        }else {
                            timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), null,proxyInns.getInnId(),"获取oms房型信息为空", TimerRateType.NOT_HOVE_ROUSE));
                        }
                    TomsUtil.obtNull(list);
                    TomsUtil.obtNull(roomStatusDetails);
                    TomsUtil.gc();
                    } catch (Exception e) {
                        log.error("定时任务 获取oms房型异常"+e);
                    }
               //}
                return null;
            }
        };

    }

    @Override
    public void updateHotelRoom(OtaInfoRefDto o, List<PushRoom> pushRoomList) throws Exception {
        Company company = companyDao.selectCompanyById(o.getCompanyId());
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), o.getUsedPriceModel().name()));
        for (PushRoom pushRoom: pushRoomList){
           Integer roomTypeId = pushRoom.getRoomType().getRoomTypeId();
            //查询客栈是否是上架状态
            BangInn bangInn =  bangInnDao.selectBangInnByParam(o.getCompanyId(), o.getOtaInfoId(), pushRoom.getRoomType().getAccountId());
            //验证此房型是不是在数据库存在
            OtaInnRoomTypeGoodsDto good  = goodsDao.selectGoodsByRoomTypeIdAndCompany(o.getCompanyId(), roomTypeId);
            if ( bangInn!=null){
                if (good!=null){
                    OtaPriceModelDto priceModel = priceModelDao.findOtaPriceModelByWgOtaId(good.getOtaWgId());
                    log.info("roomTypeId:"+roomTypeId+" roomTypeName："+pushRoom.getRoomType().getRoomTypeName());
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(o.getCompanyId(), roomTypeId,  o.getOtaInfoId()));
                    TBXHotelUtil.updateHotelPushRoom(o, pushRoom, priceModel, priceDto,commission);
                } else {
                    log.info("此房型还没有上架 roomTypeId:"+pushRoom.getRoomType().getRoomTypeId());
                }
            }else {
                log.info("此客栈已经下架AccountId:"+ pushRoom.getRoomType().getAccountId());
            }
        }
    }




    @Override
    public void updateHotelFailTimer(OtaInfoRefDto o)  {
        String companyId = o.getCompanyId();
        Company company = companyDao.selectCompanyById(companyId);
        List<TimerRatePrice> timerRatePriceList = timerRatePriceDao.selectTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId()));
        TBParam tbParam = null;
        try {
            for (TimerRatePrice ratePrice:timerRatePriceList){
                BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, ratePrice.getInnId());
                    if (TimerRateType.NOT_HOVE_ROUSE.equals(ratePrice.getRateType())){
                        List<OtaInnRoomTypeGoodsDto> list = goodsDao.selectGoodsByInnIdAndCompany(ratePrice.getInnId(),companyId);
                        TBXHotelUtil.roomRoomNumZeroUpdate(list,o);
                    }else {
                        OtaInnOtaDto otaInnOtaDto = otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(ratePrice.getInnId(), companyId, o.getOtaInfoId());
                        if (bangInn != null && otaInnOtaDto != null) {
                            tbParam = TomsUtil.toTbParam(bangInn, company, otaInnOtaDto);
                            updateOrAddHotel(tbParam, o);
                        }
                    }
                    timerRatePriceDao.deletedTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId(), bangInn.getInnId()));
            }
        } catch (Exception e) {
            log.error("异常信息updateHotelFailTimer：", e);
        }
    }

    @Override
    public void updateRoomTypePrice(OtaInfoRefDto infoRefDto, String innId, String companyId, String userId , String json) throws Exception{
        List<AddFangPrice> prices = JacksonUtil.json2list(json, AddFangPrice.class);
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(innId));
        OtaInnOtaDto otaInnOta = otaInnOtaDao.selectOtaInnOtaByBangId(bangInn.getId(),companyId,infoRefDto.getOtaInfoId());
        Company company = companyDao.selectCompanyById(companyId);
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), infoRefDto.getUsedPriceModel().name()));
        if (otaInnOta!=null && otaInnOta.getSj()==0){
            throw  new TomsRuntimeException("此客栈已经下架!");
        }
        OtaPriceModelDto priceModelDto = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
        if (!CollectionUtils.isEmpty(prices)){
            String checkRoom = null;
            OtaRoomPriceDto priceDto = null;
            List<RoomDetail> roomDetailList = null;
            XRoom xRoom = null;
            Rate rate = null;
            String obtInventory = null;
            String obtInventoryRate = null;
            String gidAndRpId = null;
            for (AddFangPrice price:prices){
                if (!StringUtils.isEmpty(price.getEndDateStr()) && !StringUtils.isEmpty(price.getStartDateStr()) && price.getPriceChange()!=null){
                    priceDto = TomsUtil.buildRoomPrice(companyId,price.getRoomTypeId(),infoRefDto.getOtaInfoId(),price,Integer.valueOf(innId),userId);
                    priceDto.setRoomTypeName(price.getRoomTypeName());
                    if (bangInn!=null && bangInn.getSj()== Constants.FC_SJ){
                        checkRoom = DcUtil.omsTbRoomTYpeUrl( company.getUserAccount(), company.getUserPassword(),company.getOtaId(),bangInn.getInnId(),price.getRoomTypeId(), CommonApi.checkRoom);
                        roomDetailList = InnRoomHelper.getRoomDetail(checkRoom);
                        boolean b = tpHolder.checkRooPrice(priceDto.getValue(), roomDetailList,commission);
                        if (b){
                            xRoom = TBXHotelUtil.roomGet(price.getRoomTypeId(), infoRefDto);
                            rate = TBXHotelUtil.rateGet(infoRefDto, price.getRoomTypeId());
                            if (xRoom!=null && rate!=null ){
                                if (roomDetailList!= null){
                                    obtInventory = TomsUtil.obtInventory(roomDetailList);
                                    xRoom.setInventory(obtInventory);
                                    log.info("宝贝roomTypeId：" + price.getRoomTypeId() + " 同步到tp店" + obtInventory);
                                    TBXHotelUtil.roomUpdate(infoRefDto, xRoom);
                                    log.info("处理之前的价格json：" + TomsUtil.obtInventoryRate(roomDetailList, priceModelDto));
                                    obtInventoryRate = TomsUtil.obtInventoryRate(roomDetailList, priceModelDto, priceDto,commission);
                                    log.info("处理之后的价格json：" + obtInventoryRate);
                                    rate.setInventoryPrice(obtInventoryRate);
                                    gidAndRpId = TBXHotelUtil.rateUpdate(infoRefDto, price.getRoomTypeId(), rate);
                                    MessageCenterUtils.savePushTomsLog(OtaType.TB, Integer.valueOf(innId), price.getRoomTypeId(), userId, LogDec.MT_RoomType_Price, "startDate:" + priceDto.getStartDateStr() + " endDate:" + priceDto.getEndDateStr() + " price:" + priceDto.getValue() + " gidAndRpId:" + gidAndRpId);
                                    if (!StringUtils.isEmpty(gidAndRpId)){
                                        otaRoomPriceDao.saveOtaRoomPriceDto(priceDto);
                                    }else {
                                        throw new TomsRuntimeException("房型名称:"+price.getRoomTypeId()+" 同步失败");
                                    }
                                }
                            }else {
                                throw new TomsRuntimeException("xRoom  rate 为null"+price.getRoomTypeId()+" xRoom:"+xRoom+" rate:"+rate);
                            }
                        }else {
                            log.error("房型Id"+price.getRoomTypeId()+" 减小的价格不能低于1元");
                            throw new TomsRuntimeException("房型Id"+price.getRoomTypeName()+" 减小的价格不能低于1元");
                        }
                    }else {
                        log.error("innId："+innId+" 房型id"+price.getRoomTypeId()+"还没有上架到房仓");
                        throw new TomsRuntimeException("innId："+innId+" 房型名称:"+price.getRoomTypeName()+"还没有上架到淘宝");
                    }
                }
            }
        }
    }

    @Override
    public Result validatedOTAAccuracy(OtaInfoRefDto infoRefDto) {
        Result result = new Result();
        try {
            TBXHotelUtil.vettedOtaAppKey(infoRefDto,Constants.TB_InnId);
            otaInfoDao.saveOtaInfo(infoRefDto);
            result.setStatus(Constants.SUCCESS200);
        } catch (Exception e) {
            result.setStatus(Constants.ERROR400);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
