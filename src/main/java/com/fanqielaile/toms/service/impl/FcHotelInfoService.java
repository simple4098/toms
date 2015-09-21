package com.fanqielaile.toms.service.impl;

import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.FcHotelInfoDto;
import com.fanqielaile.toms.dto.FcRoomTypeInfoDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.OtaInnOtaDto;
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

import javax.annotation.Resource;
import java.util.ArrayList;
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
            log.info("fc result :"+result);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new Exception("绑定失败:"+e.getMessage());
        }
    }

    @Override
    public List<FcRoomTypeInfo> finFcRoomTypeByHotelId(String fcHotelId) {
        return fcRoomTypeInfoDao.selectFcRoomTypeByHotelId(fcHotelId);

    }
}
