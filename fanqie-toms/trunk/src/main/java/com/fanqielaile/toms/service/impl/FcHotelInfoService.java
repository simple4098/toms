package com.fanqielaile.toms.service.impl;

import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.FcHotelInfoDto;
import com.fanqielaile.toms.dto.FcRoomTypeInfoDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaInnOtaDto;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.dto.fc.MatchRoomType;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.fc.*;
import com.fanqielaile.toms.service.IFcHotelInfoService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/15
 * @version: v1.0.0
 */
@Service
public class FcHotelInfoService implements IFcHotelInfoService {
    private static  final Logger log = LoggerFactory.getLogger(FcHotelInfoService.class);
    @Resource
    private IFcHotelInfoDao fcHotelInfoDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private CompanyDao companyDao;
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IFcRoomTypeInfoDao fcRoomTypeInfoDao;
    @Resource
    private IFcRoomTypeFqDao fcRoomTypeFqDao;



    @Override
    public List<FcHotelInfoDto> findFcHotel(String innName) {
        FcHotelInfoDto fcHotelInfoDto = new FcHotelInfoDto();
        fcHotelInfoDto.setHotelName(innName);
        return fcHotelInfoDao.selectFcHotel(fcHotelInfoDto);
    }

    @Override
    public FcHotelInfoDto findFcHotelByHotelId(String hotelId) {
        return fcHotelInfoDao.selectFcHotelByHotelId(hotelId);
    }

    @Override
    public void updateMatchInn(String companyId,String innId, String fcHotelId) throws Exception {
        Company company = companyDao.selectCompanyById(companyId);
        OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(company.getId(), OtaType.FC.name());
        FcHotelInfoDto hotelInfoDto = fcHotelInfoDao.selectFcHotelByHotelId(fcHotelId);
        List<HotelInfo> list = new ArrayList<HotelInfo>();
        HotelInfo hotelInfo = new HotelInfo();
        hotelInfo.setFcHotelId(Integer.valueOf(fcHotelId));
        hotelInfo.setSpHotelId(innId);
        hotelInfo.setFcHotelName(hotelInfoDto.getHotelName());
        list.add(hotelInfo);

        AddHotelMappingListRequest listRequest = new AddHotelMappingListRequest();
        listRequest.setHotelList(list);
        AddHotelMappingRequest hotelMappingRequest = new AddHotelMappingRequest();
        hotelMappingRequest.setListRequest(listRequest);
        Header header = new Header(RequestType.addHotelMapping,dto.getAppKey(),dto.getAppSecret());
        AddHotelRequest hotelRequest = new AddHotelRequest(header,hotelMappingRequest);
        try {
            String xml = FcUtil.fcRequest(hotelRequest);
            log.info("房仓绑定xml:"+xml);
            String result = HttpClientUtil.httpPost(CommonApi.FcAddHotelMappingUrl, xml);
            log.info("fc result :"+result);
            Response response = XmlDeal.pareFcResult(result);
            if (Constants.FcResultNo.equals(response.getResultNo())){
                BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(innId));
                OtaInnOtaDto otaInnOtaDto = otaInnOtaDao.selectOtaInnOtaByHid(Long.valueOf(fcHotelId), company.getId(), dto.getOtaInfoId());
                if (otaInnOtaDto==null){
                    OtaInnOtaDto otaInnOta = OtaInnOtaDto.toFcDto(Long.valueOf(fcHotelId), bangInn.getInnName(),Integer.valueOf(innId), company, bangInn.getId(), dto.getOtaInfoId());
                    otaInnOtaDao.saveOtaInnOta(otaInnOta);
                }
            }else {
                throw  new Exception("绑定失败:"+response.getResultMsg());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("绑定失败:"+e.getMessage());
        }
    }

    @Override
    public List<FcRoomTypeInfo> finFcRoomTypeByHotelId(String fcHotelId) {
        return fcRoomTypeInfoDao.selectFcRoomTypeByHotelId(fcHotelId);

    }

    @Override
    public void updateMatchRoomType(String companyId,String innId, String fcHotelId, String json)throws Exception{
        List<MatchRoomType> matchRoomType = JacksonUtil.json2list(json, MatchRoomType.class);
        Company company = companyDao.selectCompanyById(companyId);
        OtaInfoRefDto dto = otaInfoDao.selectAllOtaByCompanyAndType(company.getId(), OtaType.FC.name());
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, Integer.valueOf(innId));
        if (bangInn!=null){
            OtaInnOtaDto innOtaDto = otaInnOtaDao.selectOtaInnOtaByBangId(bangInn.getId(), companyId, dto.getOtaInfoId());
            if (!CollectionUtils.isEmpty(matchRoomType)){
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

                AddRoomTypeMappingListRequest roomTypeMappingListRequest = new AddRoomTypeMappingListRequest();
                roomTypeMappingListRequest.setRoomTypeList(list);
                AddRoomTypeMappingRequest addRoomTypeMappingRequest = new AddRoomTypeMappingRequest();
                addRoomTypeMappingRequest.setRoomTypeList(roomTypeMappingListRequest);
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
                    //todo 房仓接口xml
                   // if (Constants.FcResultNo.equals(response.getResultNo())){
                        fcRoomTypeFqDao.saveRoomTypeFq(new FcRoomTypeFqDto(fcRoomTypeFqs));
                   /* }else {
                        throw  new Exception("绑定失败:"+response.getResultMsg());
                    }*/

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
