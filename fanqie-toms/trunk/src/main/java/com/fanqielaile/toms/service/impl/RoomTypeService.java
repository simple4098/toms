package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.*;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
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
    @Override
    public RoomTypeInfoDto findRoomType(ParamDto paramDto,UserInfo userInfo)throws  Exception{
        Company company = companyDao.selectCompanyById(userInfo.getCompanyId());
        if (!StringUtils.isEmpty(paramDto.getAccountId())){
            BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndAccountId(userInfo.getCompanyId(), Integer.valueOf(paramDto.getAccountId()));
            if (!paramDto.isMaiAccount()){
                paramDto.setAccountId(bangInn.getAccountIdDi()!=null?String.valueOf(bangInn.getAccountIdDi()):null);
            }
        }
        if (paramDto.getAccountId()!=null){
            String roomTypeUrl = DcUtil.roomTypeUrl(paramDto, company.getOtaId(), company.getUserAccount(), company.getUserPassword(), CommonApi.ROOM_TYPE);
            log.info("==============roomTypeUrl:" + roomTypeUrl);
            String httpGets = HttpClientUtil.httpGets(roomTypeUrl, null);
            JSONObject jsonObject = JSONObject.fromObject(httpGets);
            RoomTypeInfoDto roomTypeInfoDto = null;
            if (TomsConstants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
                roomTypeInfoDto = new RoomTypeInfoDto();
                List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
                List<RoomDetail> roomDetail = list.get(0).getRoomDetail();
                roomTypeInfoDto.setList(list);
                if (roomDetail!=null){
                    List<String> dates = new ArrayList<String>();
                    Date today = DateUtil.parseDate(DateUtil.formatDateToString(new Date(), "yyyy-MM-dd"));
                    for (RoomDetail detail:roomDetail){
                        Date parseDate = DateUtil.parseDate(detail.getRoomDate());
                        String dateToString = DateUtil.formatDateToString(parseDate, "MM-dd");
                        String t="";
                        if (parseDate.equals(today)){
                            t= TomsConstants.TODAY;
                        }else {
                            t= DcUtil.dayOfWeek(new DateTime(parseDate).getDayOfWeek());//String.valueOf(new DateTime(parseDate).getDayOfWeek());
                        }
                        String v = dateToString+t;
                        dates.add(v);
                    }
                    roomTypeInfoDto.setRoomDates(dates);
                }
                roomTypeInfoDto.setList(list);
                return  roomTypeInfoDto ;
            }
        }
        return null;

    }
}
