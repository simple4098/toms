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
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.fc.Response;
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

    @Override
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
        String innId = tbParam.getInnId();
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        tbParam.setOtaId(String.valueOf(company.getOtaId()));
        String inn_info = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId(), CommonApi.INN_INFO);
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
                //已绑定
            }else {
                BangInnDto.toUpdateDto(bangInn, tbParam, omsInnDto);
                bangInnDao.updateBangInnTp(bangInn);
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
        List<FcRoomTypeFqDto> roomTypeFqDtoList = fcRoomTypeFqDao.selectFcRoomTypeFqBySJ(company.getId(), o.getOtaInfoId());
        for (FcRoomTypeFqDto fcRoomTypeFqDto:roomTypeFqDtoList){
            if (!StringUtils.isEmpty(fcRoomTypeFqDto.getFcRoomTypeId()) && fcRoomTypeFqDto.getSj()==Constants.FC_SJ){
                BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(fcRoomTypeFqDto.getInnId()));
                FCXHotelUtil.syncRateInfo(company,o,fcRoomTypeFqDto,bangInn,Integer.valueOf(fcRoomTypeFqDto.getFqRoomTypeId()));
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
            FcRoomTypeFqDto fcRoomTypeFqDto = fcRoomTypeFqDao.findRoomTypeFqInnIdRoomIdOtaInfoId(bangInn.getInnId(), roomTypeId, o.getOtaInfoId(),o.getCompanyId());
            if ( bangInn!=null){
                //满足这些条件 才是之前上架过。
                if (fcRoomTypeFqDto!=null && !StringUtils.isEmpty(fcRoomTypeFqDto.getFcRoomTypeId()) && fcRoomTypeFqDto.getSj() == Constants.FC_SJ){
                    Company company = companyDao.selectCompanyById(o.getCompanyId());
                    //上架房型 房量 库存
                    Response response = FCXHotelUtil.syncRateInfo(company, o, fcRoomTypeFqDto, bangInn, roomTypeId);
                    if (Constants.FcResultNo.equals(response.getResultNo())){
                        fcRoomTypeFqDao.updateRoomTypeFqSj(fcRoomTypeFqDto.getId(), Constants.FC_SJ);
                    }else {
                        throw  new Exception("及时推送失败:"+response.getResultMsg());
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
}
