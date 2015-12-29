package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaCommissionPercent;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.model.fc.Response;
import com.fanqielaile.toms.service.IFcRoomTypeFqService;
import com.fanqielaile.toms.service.IOtaRoomPriceService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.exception.VettedOTAException;
import com.fanqielaile.toms.support.holder.TPHolder;
import com.fanqielaile.toms.support.tb.FCXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private IOtaCommissionPercentDao commissionPercentDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private TPHolder tpHolder;

    @Override
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {

    }

    @Override
    public void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {

    }

    @Override
    public void updateHotel(OtaInfoRefDto infoRefDto, TBParam tbParam) throws Exception {

    }

    @Override
    public void updateHotelRoom(OtaInfoRefDto infoRefDto, List<PushRoom> pushRoomList) throws Exception {

    }

    @Override
    public void updateHotelFailTimer(OtaInfoRefDto infoRefDto) {

    }

    @Override
    public void updateRoomTypePrice(OtaInfoRefDto infoRefDto, String innId, String companyId, String userId, String json) throws Exception {
        Company company = companyDao.selectCompanyById(companyId);
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), infoRefDto.getUsedPriceModel().name()));
        List<AddFangPrice> prices = JacksonUtil.json2list(json, AddFangPrice.class);
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(innId));
        if (!CollectionUtils.isEmpty(prices)) {
            FcRoomTypeFqDto fcRoomTypeFqDto = null;
            OtaRoomPriceDto priceDto = null;
            for (AddFangPrice price : prices) {
                if (!StringUtils.isEmpty(price.getEndDateStr()) && !StringUtils.isEmpty(price.getStartDateStr()) && price.getPriceChange() != null) {
                    fcRoomTypeFqDto = fcRoomTypeFqDao.findRoomTypeFqInnIdRoomIdOtaInfoId(Integer.valueOf(innId), price.getRoomTypeId(), infoRefDto.getOtaInfoId(), companyId);
                    priceDto = new OtaRoomPriceDto(companyId, price.getRoomTypeId(), infoRefDto.getOtaInfoId());
                    priceDto.setStartDateStr(price.getStartDateStr());
                    priceDto.setEndDateStr(price.getEndDateStr());
                    priceDto.setValue(price.getPriceChange());
                    priceDto.setInnId(Integer.valueOf(innId));
                    priceDto.setModifierId(userId);
                    priceDto.setRoomTypeName(price.getRoomTypeName());
                    if (fcRoomTypeFqDto != null && !StringUtils.isEmpty(fcRoomTypeFqDto.getFcRoomTypeId()) && fcRoomTypeFqDto.getSj() == Constants.FC_SJ) {
                        String room_type = DcUtil.omsFcRoomTYpeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(), bangInn.getInnId(), price.getRoomTypeId(), CommonApi.checkRoom);
                        //List<RoomDetail> roomDetailList = otaRoomPriceService.obtRoomAvailFc(bangInn, price.getRoomTypeId());
                        List<RoomDetail> roomDetailList = InnRoomHelper.getRoomDetail(room_type);
                        boolean b = tpHolder.checkRooPrice(priceDto.getValue(), roomDetailList,commission);
                        if (b) {
                            Response response = FCXHotelUtil.syncRoomInfo(infoRefDto, fcRoomTypeFqDto, roomDetailList, priceDto, commission);
                            if (Constants.FcResultNo.equals(response.getResultNo())) {
                                otaRoomPriceDao.saveOtaRoomPriceDto(priceDto);
                            } else {
                                log.info("房型Id" + price.getRoomTypeId() + " 同步失败：" + response.getResultMsg());
                                throw new TomsRuntimeException("房型名称:" + price.getRoomTypeName() + " 同步失败：" + response.getResultMsg());
                            }
                        } else {
                            log.info("房型Id" + price.getRoomTypeId() + " 减小的价格不能低于1元");
                            throw new TomsRuntimeException("房型名称:" + price.getRoomTypeName() + " 减小的价格不能低于1元");
                        }

                    } else {
                        log.info("innId：" + innId + " 房型id" + price.getRoomTypeId() + "还没有上架到房仓");
                        throw new TomsRuntimeException("innId：" + innId + " 房型名称" + price.getRoomTypeName() + "还没有上架到房仓");
                    }

                } else {
                    //priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(companyId, price.getRoomTypeId(), infoRefDto.getOtaInfoId()));
                    log.info("innId：" + innId + " 房型id" + price.getRoomTypeId() + " 开始结束时间为空!");
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
