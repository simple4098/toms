package com.fanqielaile.toms.service.impl;

import com.fanqie.util.*;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.fc.*;
import com.fanqielaile.toms.enums.BedType;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.fc.*;
import com.fanqielaile.toms.service.IFcHotelInfoService;
import com.fanqielaile.toms.service.IOtaRoomPriceService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.ExportExcelUtil;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlDeal;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
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
    @Resource
    private IOtaPriceModelDao priceModelDao;
    @Resource
    private IOtaRoomPriceService otaRoomPriceService;
    @Resource
    private IFcCityDao fcCityDao;
    @Resource
    private IFcProvinceDao fcProvinceDao;



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
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.valueOf(innId));
        OtaInnOtaDto innOtaDto = otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(Integer.valueOf(innId), companyId, dto.getOtaInfoId());

        List<HotelInfo> list = new ArrayList<HotelInfo>();
        HotelInfo hotelInfo = new HotelInfo();
        hotelInfo.setSpHotelId(innId);
        list.add(hotelInfo);

        //先删除之前的匹配情况
        if (innOtaDto!=null && !fcHotelId.equals(innOtaDto.getWgHid())){
            hotelInfo.setFcHotelId(Integer.valueOf(innOtaDto.getWgHid()));
            DeleteHotelInfoRequest deleteHotelInfoRequest = new DeleteHotelInfoRequest();
            deleteHotelInfoRequest.setHotelList(list);
            Header header = new Header(RequestType.deleteHotelMapping, dto.getAppKey(),dto.getAppSecret());
            DeleteHotelMappingRequest deleteHotelMappingRequest = new DeleteHotelMappingRequest(header,deleteHotelInfoRequest);

            String xml = FcUtil.fcRequest(deleteHotelMappingRequest);
            log.info("房仓解除绑定xml:"+xml);
            String result = HttpClientUtil.httpPost(CommonApi.FcDeleteHotelMappingUrl, xml);
            log.info("fc result :"+result);
            Response response = XmlDeal.pareFcResult(result);
            if (Constants.FcResultNo.equals(response.getResultNo())){
                otaInnOtaDao.deletedOtaInnOtaById(innOtaDto.getId());
                priceModelDao.deletePriceModelByOtaInnOta(innOtaDto.getId());
                fcRoomTypeFqDao.deletedFcRoomTypeFqByInnIdAndCompanyId(innId,companyId,dto.getOtaInfoId());
            }else {
                throw  new Exception("房仓解除绑定失败:"+response.getResultMsg());
            }
            

        }
        //绑定操作
        hotelInfo.setFcHotelId(Integer.valueOf(fcHotelId));
        hotelInfo.setFcHotelName(hotelInfoDto.getHotelName());
        AddHotelMappingRequest hotelMappingRequest = new AddHotelMappingRequest();
        hotelMappingRequest.setHotelList(list);
        Header header = new Header(RequestType.addHotelMapping, dto.getAppKey(), dto.getAppSecret());
        AddHotelRequest hotelRequest = new AddHotelRequest(header, hotelMappingRequest);
        try {
            String xml = FcUtil.fcRequest(hotelRequest);
            log.info("房仓绑定xml:" + xml);
            String result = HttpClientUtil.httpPost(CommonApi.FcAddHotelMappingUrl, xml);
            log.info("fc result :" + result);
            Response response = XmlDeal.pareFcResult(result);
            if (Constants.FcResultNo.equals(response.getResultNo())) {
                OtaInnOtaDto otaInnOtaDto = otaInnOtaDao.selectOtaInnOtaByHid(Long.valueOf(fcHotelId), company.getId(), dto.getOtaInfoId());
                if (otaInnOtaDto == null) {
                    OtaInnOtaDto otaInnOta = OtaInnOtaDto.toFcDto(Long.valueOf(fcHotelId), bangInn.getInnName(), Integer.valueOf(innId), company, bangInn.getId(), dto.getOtaInfoId());
                    otaInnOtaDao.saveOtaInnOta(otaInnOta);
                    OtaPriceModelDto otaPriceModel = OtaPriceModelDto.toDto(otaInnOta.getUuid());
                    priceModelDao.savePriceModel(otaPriceModel);
                }
            } else {
                throw new Exception("绑定失败:" + response.getResultMsg());
            }

        } catch (Exception e) {
            throw new Exception("绑定失败:" + e.getMessage());
        }

    }

    @Override
    public List<FcRoomTypeInfo> finFcRoomTypeByHotelId(String fcHotelId) {
        return fcRoomTypeInfoDao.selectFcRoomTypeByHotelId(fcHotelId);

    }

    @Override
    public void excel(String companyId,List<BangInnDto> bangInns,HttpServletResponse response) throws Exception {
        Company company = companyDao.selectCompanyById(companyId);
        if (!CollectionUtils.isEmpty(bangInns)){
            List<FcInnInfoDto> fcHotelInfoList = new ArrayList<FcInnInfoDto>();
            for (BangInnDto bangInnDto:bangInns){
                String inn_info = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInnDto.getAccountId()), CommonApi.INN_INFO);
                String innInfoGet = HttpClientUtil.httpGets(inn_info, null);
                JSONObject jsonInn = JSONObject.fromObject(innInfoGet);
                //客栈
                if (TomsConstants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null) {
                    InnDto omsInnDto = JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
                    FcProvince fcProvince = fcProvinceDao.selectProvince(omsInnDto.getProvince());
                    FcCity city = fcCityDao.selectFcCityByName(omsInnDto.getCity());
                    FcInnInfoDto fcInnInfo = new FcInnInfoDto();
                    BeanUtils.copyProperties(omsInnDto,fcInnInfo);
                    fcInnInfo.setProvinceCode(fcProvince!=null?fcProvince.getProvinceCode():null);
                    fcInnInfo.setCityCode(city!=null?city.getCityCode():null);
                    fcInnInfo.setInnId(String.valueOf(bangInnDto.getInnId()));
                    List<OmsImg> imgList = omsInnDto.getImgList();
                    List<FcInnImg> fcInnImgList = new ArrayList<FcInnImg>();
                    for (OmsImg omsImg:imgList){
                        FcInnImg fcInnImg = new FcInnImg();
                        BeanUtils.copyProperties(omsImg,fcInnImg);
                        fcInnImg.setInnId(bangInnDto.getInnId());
                        fcInnImgList.add(fcInnImg);
                    }
                    fcInnInfo.setFcInnImgList(fcInnImgList);
                    List<RoomTypeInfo> roomTypeInfoList = otaRoomPriceService.obtOmsRoomInfoToFc(bangInnDto);
                    if (!CollectionUtils.isEmpty(roomTypeInfoList)){
                        List<FcRoomTypeDtoInfo> roomTypeInfoDtoList = new ArrayList<FcRoomTypeDtoInfo>();
                        for (RoomTypeInfo roomTypeInfo:roomTypeInfoList){
                            FcRoomTypeDtoInfo fcRoomTypeDtoInfo = new FcRoomTypeDtoInfo();
                            BeanUtils.copyProperties(roomTypeInfo,fcRoomTypeDtoInfo);
                            fcRoomTypeDtoInfo.setInnId(bangInnDto.getInnId());
                            if (roomTypeInfo.getBedWid()!=null && roomTypeInfo.getBedLen()!=null){
                                if (roomTypeInfo.getBedWid()>Constants.onlyBedWidBedBig){
                                    fcRoomTypeDtoInfo.setBedType(BedType.BigBed);
                                }else {
                                    fcRoomTypeDtoInfo.setBedType(BedType.onlyBed);
                                }
                            }else {
                                fcRoomTypeDtoInfo.setBedType(BedType.onlyBed);
                            }

                            roomTypeInfoDtoList.add(fcRoomTypeDtoInfo);

                        }
                        fcInnInfo.setRoomTypeInfoList(roomTypeInfoDtoList);
                    }

                    fcHotelInfoList.add(fcInnInfo);
                }
            }
            StringBuilder builder = new StringBuilder("未匹配列表_");
            builder.append(DateUtil.formatDateToString(new Date(),"yyyyMMddHHmmssSSS")).append(".xls");
            ExportExcelUtil.execlExport(fcHotelInfoList, response, builder.toString());


        }

    }


}
