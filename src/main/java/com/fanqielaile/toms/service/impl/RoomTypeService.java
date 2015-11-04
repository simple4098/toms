package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.*;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IRoomTypeService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/3
 * @version: v1.0.0
 */
@Service
public class RoomTypeService implements IRoomTypeService {
    private static  final Logger log = Logger.getLogger(RoomTypeService.class);
    @Resource
    private CompanyDao companyDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;
    @Resource
    private IOtaPriceModelDao priceModelDao;
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Override
    public RoomTypeInfoDto findRoomType(ParamDto paramDto,UserInfo userInfo)throws  Exception{
        Company company = companyDao.selectCompanyById(userInfo.getCompanyId());
        if (!StringUtils.isEmpty(paramDto.getAccountId())){
            BangInn bangInn = bangInnDao.selectBangInnById(paramDto.getAccountId());
            if (bangInn!=null && !paramDto.isMaiAccount()){
                paramDto.setAccountId(bangInn.getAccountIdDi()!=null?String.valueOf(bangInn.getAccountIdDi()):null);
            }else {
                paramDto.setAccountId(bangInn.getAccountId()!=null?String.valueOf(bangInn.getAccountId()):null);
            }
        }
        if (paramDto.getAccountId()!=null){
            String roomTypeUrl = DcUtil.roomTypeUrl(paramDto, company.getOtaId(), company.getUserAccount(), company.getUserPassword(), CommonApi.ROOM_TYPE);
            log.info("==============roomTypeUrl:" + roomTypeUrl);
            return  obtRoomTypeInfoDto(roomTypeUrl);
        }
        return null;

    }

    private RoomTypeInfoDto obtRoomTypeInfoDto(String url) throws IOException {
        String httpGets = HttpClientUtil.httpGets(url, null);
        JSONObject jsonObject = JSONObject.fromObject(httpGets);
        RoomTypeInfoDto roomTypeInfoDto = null;
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list") != null) {
            roomTypeInfoDto = new RoomTypeInfoDto();
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            List<RoomDetail> roomDetail = list.get(0).getRoomDetail();
            roomTypeInfoDto.setList(list);
            if (roomDetail != null) {
                List<String> dates = new ArrayList<String>();
                Date today = DateUtil.parseDate(DateUtil.formatDateToString(new Date(), "yyyy-MM-dd"));
                for (RoomDetail detail : roomDetail) {
                    Date parseDate = DateUtil.parseDate(detail.getRoomDate());
                    String dateToString = DateUtil.formatDateToString(parseDate, "MM-dd");
                    String t = "";
                    if (parseDate.equals(today)) {
                        t = TomsConstants.TODAY;
                    } else {
                        t = DcUtil.dayOfWeek(new DateTime(parseDate).getDayOfWeek());//String.valueOf(new DateTime(parseDate).getDayOfWeek());
                    }
                    String v = dateToString + t;
                    dates.add(v);
                }
                roomTypeInfoDto.setRoomDates(dates);
            }
            roomTypeInfoDto.setList(list);
            return roomTypeInfoDto;
        }
      return null;
    }
    private RoomTypeInfoDto obtRoomTypeInfoDto(BangInn bangInn,String url,String otaInfoId,String companyId,BigDecimal percentage) throws IOException {
        String httpGets = HttpClientUtil.httpGets(url, null);
        JSONObject jsonObject = JSONObject.fromObject(httpGets);
        RoomTypeInfoDto roomTypeInfoDto = null;
        OtaInnOtaDto otaInnOta = otaInnOtaDao.selectOtaInnOtaByBangId(bangInn.getId(),companyId,otaInfoId);
        OtaPriceModelDto priceModelDto = priceModelDao.findOtaPriceModelByWgOtaId(otaInnOta.getId());
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list") != null) {
            roomTypeInfoDto = new RoomTypeInfoDto();
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            List<RoomDetail> roomDetail = list.get(0).getRoomDetail();
            for (RoomTypeInfo r:list){
                OtaRoomPriceDto priceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(companyId, r.getRoomTypeId(), otaInfoId));
                Double value = null;
                Date startDate = null;
                Date endDate = null;
                if (priceDto!=null) {
                    value = priceDto.getValue() ;
                    startDate = priceDto.getStartDate();
                    endDate = priceDto.getEndDate();
                }
                for (RoomDetail d:r.getRoomDetail()){
                    double price = new BigDecimal(d.getRoomPrice()).multiply(priceModelDto.getPriceModelValue()).doubleValue();
                    if (percentage!=null){
                        BigDecimal bigDecimal = new BigDecimal(100);
                        BigDecimal subtract = bigDecimal.subtract(percentage);
                        Double divide = DcUtil.divide(subtract, bigDecimal);
                        Double costPrice = DcUtil.formatDouble(price * (divide));
                        d.setCostPrice(costPrice);
                    }
                    Date parseDate = DateUtil.parseDate(d.getRoomDate());
                    //在设定的范围内才对价格进行处理
                    if (priceDto!=null && parseDate.getTime() >= startDate.getTime() && endDate.getTime() >= parseDate.getTime()) {
                        price = price + value;
                        d.setPriceValue(value);
                    }
                    d.setRoomPrice(price);
                }
            }
            roomTypeInfoDto.setList(list);
            if (roomDetail != null) {
                List<String> dates = new ArrayList<String>();
                Date today = DateUtil.parseDate(DateUtil.formatDateToString(new Date(), "yyyy-MM-dd"));
                for (RoomDetail detail : roomDetail) {
                    Date parseDate = DateUtil.parseDate(detail.getRoomDate());
                    String dateToString = DateUtil.formatDateToString(parseDate, "MM-dd");
                    String t = "";
                    if (parseDate.equals(today)) {
                        t = TomsConstants.TODAY;
                    } else {
                        t = DcUtil.dayOfWeek(new DateTime(parseDate).getDayOfWeek());//String.valueOf(new DateTime(parseDate).getDayOfWeek());
                    }
                    String v = dateToString + t;
                    dates.add(v);
                }
                roomTypeInfoDto.setRoomDates(dates);
            }
            roomTypeInfoDto.setList(list);
            return roomTypeInfoDto;
        }
      return null;
    }


    @Override
    public RoomTypeInfoDto findRoomType(String otaInfoId,ParamDto paramDto,BangInn bangInn) throws Exception {
        Company company = companyDao.selectCompanyById(bangInn.getCompanyId());
        paramDto.setAccountId(String.valueOf(bangInn.getAccountId()));
        if (StringUtils.isEmpty(paramDto.getStartDate())  || StringUtils.isEmpty(paramDto.getEndDate())){
            DateTime startTime = DateUtil.addDate(0);
            DateTime endTime = DateUtil.addDate(30);
            paramDto.setStartDate( DateUtil.format(startTime.toDate()));
            paramDto.setEndDate(DateUtil.format(endTime.toDate()));
        }
        //OtaInfoRefDto otaInfoRefDto = otaInfoDao.findOtaInfoByOtaIdAndCompanyId(new OtaInfoRefDto(otaInfoId, company.getId()));
        String otaPercentUrl = DcUtil.proxyOtaPercentUrl(company.getOtaId(),CommonApi.proxyOtaPercent,"2");
        String httpGets = HttpClientUtil.httpGets(otaPercentUrl, null);
        JSONObject jsonObject = JSONObject.fromObject(httpGets);
        BigDecimal percentage = null;
        if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) ) {
            Double  d= (Double) jsonObject.get("percentage");
            percentage = new BigDecimal(d);
        }else {
            percentage = new BigDecimal(0);
        }
        String roomTypeUrl = DcUtil.roomTypeUrl(paramDto, company.getOtaId(), company.getUserAccount(), company.getUserPassword(), CommonApi.ROOM_TYPE);
        return  obtRoomTypeInfoDto(bangInn,roomTypeUrl,otaInfoId,company.getId(),percentage);
    }
}
