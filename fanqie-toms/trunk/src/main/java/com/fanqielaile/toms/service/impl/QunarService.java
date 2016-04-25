package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.RoomSwitchCalStatus;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.enums.LogDec;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.enums.TBType;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.model.qunar.QunarCityInfo;
import com.fanqielaile.toms.service.IQunarCityInfoService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.holder.TPHolder;
import com.fanqielaile.toms.support.tb.TBXHotelUtilPromotion;
import com.fanqielaile.toms.support.util.MessageCenterUtils;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.taobao.api.domain.XHotel;
import com.taobao.api.domain.XRoomType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdayin on 2016/4/14.
 */
@Service("qunarService")
public class QunarService implements ITPService {
    private static final Logger log = LoggerFactory.getLogger(TBService.class);
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
    private QunarCityInfoDao qunarCityInfoDao;
    @Resource
    private TPHolder tpHolder;

    @Override
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        Integer innId = Integer.valueOf(tbParam.getInnId());
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), innId);
        tpHolder.validate(company, tbParam.getInnId(), otaInfo.getOtaInfoId());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        String inn_info = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId() != null ? tbParam.getAccountId() : tbParam.getAccountIdDi(), CommonApi.INN_INFO);
        log.info("inn url:" + inn_info);
        OtaPriceModelDto otaPriceModel = null;
        OtaInnOtaDto otaInnOta = null;
        if (!tbParam.isSj()) {
            otaInnOta = otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(innId, company.getId(), otaInfo.getOtaInfoId());
            if (otaInnOta != null) {
                otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
                List<OtaInnRoomTypeGoodsDto> list = goodsDao.selectGoodsByInnIdAndCompany(innId, company.getId());
                TBXHotelUtilPromotion.roomRoomNumZeroUpdate(list, otaInfo);
                bangInnDao.updateSjBangInn(innId, company.getId(), tbParam.isSj());
                otaInnOtaDao.updateOtaInnOta(otaInnOta);
            }
        } else {
            //客栈
            InnDto omsInnDto = InnRoomHelper.getInnInfo(inn_info);
            if (omsInnDto != null && null != omsInnDto.getCity() && StringUtils.isNotEmpty(omsInnDto.getCity())) {
                omsInnDto.setInnId(tbParam.getInnId());
                //转换客栈city为
                QunarCityInfo qunarCityInfo = this.qunarCityInfoDao.selectQunarCityInfoByName(omsInnDto.getCounty());
                if (null != qunarCityInfo && StringUtils.isNotEmpty(qunarCityInfo.getCityCode())) {
                    omsInnDto.setCity(qunarCityInfo.getCityCode());
                }
                //推客栈、如果存在再获取客栈
                if (tbParam.getAccountId() != null) {
                    log.info("========开始保存去哪儿上架客栈信息【" + omsInnDto.getBrandName() + "[" + omsInnDto.getInnId() + "]" + "】==============");
                    MessageCenterUtils.savePushTomsLog(OtaType.QUNAR, innId, null, null, LogDec.INN_PUSH, omsInnDto.toString());
                    //处理推送的hid
                    String hid = tbParam.getOtaId().toString() + omsInnDto.getInnId().toString();
                    otaInnOta = otaInnOtaDao.selectOtaInnOtaByHid(Long.valueOf(hid), company.getId(), otaInfo.getOtaInfoId());
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
                        otaInnOta = OtaInnOtaDto.toDto(hid, omsInnDto.getInnName(), company.getId(), tbParam, bangInnId, otaInfo.getOtaInfoId());
                        otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
                        otaInnOtaDao.saveOtaInnOta(otaInnOta);
                        otaPriceModel = OtaPriceModelDto.toDto(otaInnOta.getUuid());
                        priceModelDao.savePriceModel(otaPriceModel);
                    } else {
                        otaPriceModel = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
                        otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
                        otaInnOtaDao.updateOtaInnOta(otaInnOta);
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

    }

    @Override
    public Result validatedOTAAccuracy(OtaInfoRefDto infoRefDto) {
        return null;
    }

    @Override
    public void sellingRoomType(String from, String to, OtaInfoRefDto otaInfoRefDto) {

    }
}
