package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.TomsConstants;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.dto.fc.MatchRoomType;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.TimerRatePrice;
import com.fanqielaile.toms.model.fc.Response;
import com.fanqielaile.toms.service.IFcRoomTypeFqService;
import com.fanqielaile.toms.service.IOtaRoomPriceService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.FCXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DESC : 美团对接实现类
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
@Service("fcService")
public class FcService implements ITPService {
    private static  final Logger log = LoggerFactory.getLogger(FcService.class);
    @Resource
    private CompanyDao companyDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IFcRoomTypeFqDao fcRoomTypeFqDao;
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private IOtaRoomPriceService otaRoomPriceService;
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;
    @Resource
    private IFcRoomTypeFqService fcRoomTypeFqService;
    @Resource
    private ITimerRatePriceDao timerRatePriceDao;

    @Override
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
        String innId = tbParam.getInnId();
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        String inn_info = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId()!=null?tbParam.getAccountId():tbParam.getAccountIdDi(), CommonApi.INN_INFO);
        String innInfoGet = HttpClientUtil.httpGets(inn_info, null);
        JSONObject jsonInn = JSONObject.fromObject(innInfoGet);
        //客栈
        if (TomsConstants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null){
            InnDto omsInnDto = JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
            omsInnDto.setInnId(innId);
            BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(tbParam.getInnId()));
            //未绑定
            BangInnDto bangInnDto = null;
            if (bangInn==null){
                bangInnDto = BangInnDto.toDto(company.getId(), tbParam, omsInnDto);
                bangInnDao.createBangInn(bangInnDto);
                log.info("fc 客栈"+tbParam.getInnId()+" 绑定");
            }else {
                log.info("fc 客栈"+bangInn.getInnId()+" 已绑定"+" 状态:"+tbParam.isSj());
                BangInnDto.toUpdateDiDto(bangInn, tbParam, omsInnDto);
                if (Constants.DI.equals(tbParam.getsJiaModel()) && tbParam.getAccountId()==null) {
                    if (!tbParam.isSj()){
                        bangInn.setAccountIdDi(null);
                    }else {
                        bangInn.setSj(1);
                    }
                }else {
                    BangInnDto.toUpdateDto(bangInn, tbParam, omsInnDto);
                }
                bangInnDao.updateBangInnTp(bangInn);
                //下架状态的时候 要把房仓的宝贝下架掉
                List<FcRoomTypeFqDto> roomTypeFqDtoList = fcRoomTypeFqDao.selectFcRoomTypeFqBySJ(new FcRoomTypeFqDto(Constants.FC_SJ,innId,company.getId(),otaInfo.getOtaInfoId()));
                if (!CollectionUtils.isEmpty(roomTypeFqDtoList)) {
                    if (Constants.FC_XJ.equals(bangInn.getSj())) {
                        for (FcRoomTypeFqDto fqDto : roomTypeFqDtoList) {
                            fcRoomTypeFqService.updateXjMatchRoomType(company.getId(), fqDto.getId());
                        }
                    } else if (Constants.FC_SJ.equals(bangInn.getSj())) {
                        for (FcRoomTypeFqDto fqDto : roomTypeFqDtoList) {
                            fcRoomTypeFqService.updateSjMatchRoomType(company.getId(), fqDto.getId());
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
        log.info("====Fc 同步 start====");
        Company company = companyDao.selectCompanyByCompanyCode(o.getCompanyCode());
        List<FcRoomTypeFqDto> roomTypeFqDtoList = fcRoomTypeFqDao.selectFcRoomTypeFqBySJ(new FcRoomTypeFqDto(Constants.FC_SJ,null,company.getId(),o.getOtaInfoId()));
        if (!CollectionUtils.isEmpty(roomTypeFqDtoList)){
            for (FcRoomTypeFqDto fcRoomTypeFqDto:roomTypeFqDtoList){
                BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(fcRoomTypeFqDto.getInnId()));
                OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(fcRoomTypeFqDto.getFqRoomTypeId()), fcRoomTypeFqDto.getOtaInfoId()));
                Response response = FCXHotelUtil.syncRateInfo(company, o, fcRoomTypeFqDto, bangInn, Integer.valueOf(fcRoomTypeFqDto.getFqRoomTypeId()), priceDto);
                if (!Constants.FcResultNo.equals(response.getResultNo())){
                    timerRatePriceDao.saveTimerRatePrice(new TimerRatePrice(company.getId(), o.getOtaInfoId(), Integer.valueOf(fcRoomTypeFqDto.getFqRoomTypeId()),Integer.valueOf(fcRoomTypeFqDto.getInnId()), response.getResultMsg()));
                }
            }
        }

    }

    @Override
    public void updateHotelRoom(OtaInfoRefDto o, List<PushRoom> pushRoomList) throws Exception {

        for (PushRoom pushRoom: pushRoomList){
            Integer roomTypeId = pushRoom.getRoomType().getRoomTypeId();
            //查询客栈是否是上架状态
            BangInn bangInn =  bangInnDao.selectBangInnByParam(o.getCompanyId(), o.getOtaInfoId(), pushRoom.getRoomType().getAccountId());
            //验证此房型是不是在数据库存在
            if ( bangInn!=null){
                FcRoomTypeFqDto fcRoomTypeFqDto = fcRoomTypeFqDao.findRoomTypeFqInnIdRoomIdOtaInfoId(bangInn.getInnId(), roomTypeId, o.getOtaInfoId(),o.getCompanyId());
                //满足这些条件 才是之前上架过。
                if (fcRoomTypeFqDto!=null && !StringUtils.isEmpty(fcRoomTypeFqDto.getFcRoomTypeId()) && fcRoomTypeFqDto.getSj() == Constants.FC_SJ){
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(fcRoomTypeFqDto.getCompanyId(), roomTypeId, fcRoomTypeFqDto.getOtaInfoId()));
                    Company company = companyDao.selectCompanyById(o.getCompanyId());
                    //上架房型 房量 库存
                    Response response = FCXHotelUtil.syncRateInfo(company, o, fcRoomTypeFqDto, bangInn, roomTypeId,priceDto);
                    if (Constants.FcResultNo.equals(response.getResultNo())){
                        fcRoomTypeFqDao.updateRoomTypeFqSj(fcRoomTypeFqDto.getId(), Constants.FC_SJ);
                    }else {
                       log.error("及时推送失败:" + response.getResultMsg());
                    }

                } else {
                    log.info("(房仓)此房型还没有上架 roomTypeId:"+pushRoom.getRoomType().getRoomTypeId());
                }
            }else {
                log.info("(房仓)此客栈已经下架AccountId:"+ pushRoom.getRoomType().getAccountId());
            }
        }

    }

    @Override
    public void updateRoomTypePrice(OtaInfoRefDto o, OtaRoomPriceDto roomPriceDto) throws Exception {
        Integer innId = roomPriceDto.getInnId();
        String companyId = roomPriceDto.getCompanyId();
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, innId);
        List<RoomTypeInfo> list = otaRoomPriceService.obtOmsRoomInfoToFc(bangInn);
        OtaInnOtaDto otaInnOta = otaInnOtaDao.selectOtaInnOtaByBangId(bangInn.getId(),companyId,roomPriceDto.getOtaInfoId());
        if (otaInnOta!=null && otaInnOta.getSj()==0){
            throw  new TomsRuntimeException("FC 此客栈已经下架!");
        }
        //房型
        if (!CollectionUtils.isEmpty(list)){
            for (RoomTypeInfo r:list){
                FcRoomTypeFqDto roomIdOtaInfo= fcRoomTypeFqDao.findRoomTypeFqInnIdRoomIdOtaInfoId(bangInn.getInnId(), r.getRoomTypeId(), o.getOtaInfoId(), o.getCompanyId());
                if (roomIdOtaInfo!=null && !StringUtils.isEmpty(roomIdOtaInfo.getFcRoomTypeId()) && Constants.FC_SJ.equals(roomIdOtaInfo.getSj())){
                    List<RoomDetail> roomDetail = r.getRoomDetail();
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(companyId, r.getRoomTypeId(), roomPriceDto.getOtaInfoId()));
                    FCXHotelUtil.syncRoomInfo(o,roomIdOtaInfo,roomDetail,priceDto);
                }else {
                    log.info("FC 此房型还没有在上架 roomTypeId"+r.getRoomTypeId());
                }

            }
        }else {
            throw new TomsRuntimeException("FC 无房型信息!");
        }
    }

    @Override
    public void updateHotelFailTimer(OtaInfoRefDto o){
        String companyId = o.getCompanyId();
        Company company = companyDao.selectCompanyById(companyId);
        List<TimerRatePrice> timerRatePriceList = timerRatePriceDao.selectTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId()));
        for (TimerRatePrice ratePrice:timerRatePriceList){
            List<FcRoomTypeFqDto> roomTypeFqDtoList = fcRoomTypeFqDao.selectFcRoomTypeFqBySJ(new FcRoomTypeFqDto(Constants.FC_SJ,String.valueOf(ratePrice.getInnId()),company.getId(),o.getOtaInfoId()));
            if (!CollectionUtils.isEmpty(roomTypeFqDtoList)){
                for (FcRoomTypeFqDto fcRoomTypeFqDto:roomTypeFqDtoList){
                    BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(fcRoomTypeFqDto.getInnId()));
                    OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(fcRoomTypeFqDto.getFqRoomTypeId()), fcRoomTypeFqDto.getOtaInfoId()));
                    try {
                        Response response = FCXHotelUtil.syncRateInfo(company, o, fcRoomTypeFqDto, bangInn, Integer.valueOf(fcRoomTypeFqDto.getFqRoomTypeId()), priceDto);
                        if (Constants.FcResultNo.equals(response.getResultNo())){
                            timerRatePriceDao.deletedFcTimerRatePrice(new TimerRatePrice(companyId, o.getOtaInfoId(), bangInn.getInnId(), Integer.valueOf(fcRoomTypeFqDto.getFqRoomTypeId())));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                       log.error("FC updateHotelFailTimer 异常:"+e.getMessage());
                    }

                }

            }
        }

    }

    @Override
    public void updateRoomTypePrice(OtaInfoRefDto infoRefDto, String innId,String companyId, String userId,String json) throws Exception{
        List<AddFangPrice> prices = JacksonUtil.json2list(json, AddFangPrice.class);
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(innId));
        if (!CollectionUtils.isEmpty(prices)){
            FcRoomTypeFqDto fcRoomTypeFqDto = null;
            OtaRoomPriceDto priceDto = null;
            for (AddFangPrice price:prices){
                if (!StringUtils.isEmpty(price.getEndDateStr()) && !StringUtils.isEmpty(price.getStartDateStr()) && price.getPriceChange()!=null){
                    fcRoomTypeFqDto = fcRoomTypeFqDao.findRoomTypeFqInnIdRoomIdOtaInfoId(Integer.valueOf(innId), price.getRoomTypeId(), infoRefDto.getOtaInfoId(), companyId);
                    priceDto = new OtaRoomPriceDto(companyId,price.getRoomTypeId(),infoRefDto.getOtaInfoId());
                    priceDto.setStartDateStr(price.getStartDateStr());
                    priceDto.setEndDateStr(price.getEndDateStr());
                    priceDto.setValue(price.getPriceChange());
                    priceDto.setInnId(Integer.valueOf(innId));
                    priceDto.setModifierId(userId);
                    if (fcRoomTypeFqDto!=null && !StringUtils.isEmpty(fcRoomTypeFqDto.getFcRoomTypeId()) && fcRoomTypeFqDto.getSj() == Constants.FC_SJ){
                        List<RoomDetail> roomDetailList  = otaRoomPriceService.obtRoomAvailFc(bangInn,price.getRoomTypeId());
                        boolean b = checkRooPrice(priceDto.getValue(), roomDetailList);
                        if (b){
                            Response response = FCXHotelUtil.syncRoomInfo(infoRefDto, fcRoomTypeFqDto, roomDetailList, priceDto);
                            if (Constants.FcResultNo.equals(response.getResultNo()) ){
                                otaRoomPriceDao.saveOtaRoomPriceDto(priceDto);
                            }else {
                                log.info("房型Id" + price.getRoomTypeId() + " 同步失败：" + response.getResultMsg());
                                throw new TomsRuntimeException("房型Id"+price.getRoomTypeId()+" 同步失败："+response.getResultMsg());
                            }
                        }else {
                            log.info("房型Id"+price.getRoomTypeId()+" 减小的价格不能低于1元");
                            throw new TomsRuntimeException("房型Id"+price.getRoomTypeId()+" 减小的价格不能低于1元");
                        }

                    }else {
                        log.info("innId："+innId+" 房型id"+price.getRoomTypeId()+"还没有上架到房仓");
                        throw new TomsRuntimeException("innId："+innId+" 房型id"+price.getRoomTypeId()+"还没有上架到房仓");
                    }

                }else {
                    //priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(companyId, price.getRoomTypeId(), infoRefDto.getOtaInfoId()));
                    log.info("innId："+innId+" 房型id"+price.getRoomTypeId()+" 开始结束时间为空!");
                }


            }
        }
    }

    private boolean checkRooPrice(double value,List<RoomDetail> roomDetailList){
        if (value<0){
           if (!CollectionUtils.isEmpty(roomDetailList)){
                List<Double> priceList = new ArrayList<Double>();
                for (RoomDetail roomDetail:roomDetailList){
                    priceList.add(roomDetail.getRoomPrice());
                }
                if (!CollectionUtils.isEmpty(priceList)){
                    Collections.sort(priceList);
                    Double price  = priceList.get(0);
                    //value本来为负数, 转化为整数比较
                    if (!(price+value >= 1)){
                        return false;
                    }
                }
           }
        }
        return true;
    }
}
