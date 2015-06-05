package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.*;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IRoomTypeService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
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
    @Resource
    private CompanyDao companyDao;
    @Override
    public RoomTypeInfoDto findRoomType(ParamDto paramDto,UserInfo userInfo)throws  Exception{
        Company company = companyDao.selectCompanyById(userInfo.getCompanyId());
        String roomTypeUrl = DcUtil.roomTypeUrl(paramDto, company.getOtaId(), company.getUserAccount(), company.getUserPassword(), CommonApi.ROOM_TYPE);
        System.out.println("=========:"+roomTypeUrl);
        String s = String.valueOf(new Date().getTime());
        String signature = DcUtil.obtMd5("105"+s+"MT"+"mt123456");
        String url ="http://192.168.1.158:8888/api/getRoomType?timestamp="+s+"&otaId="+105+"&accountId="+paramDto.getAccountId()+"&from=2015-04-02&to=2015-05-01"+"&signature="+signature;
        String httpGets = HttpClientUtil.httpGets(roomTypeUrl,null);
        JSONObject jsonObject = JSONObject.fromObject(httpGets);
        RoomTypeInfoDto roomTypeInfoDto = null;
        if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            roomTypeInfoDto = new RoomTypeInfoDto();
            List<RoomTypeInfo> list = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            List<RoomDetail> roomDetail = list.get(0).getRoomDetail();
            roomTypeInfoDto.setList(list);
            if (roomDetail!=null){
                List<Date> dates = new ArrayList<Date>();
                for (RoomDetail detail:roomDetail){
                    dates.add(DateUtil.parseDate(detail.getRoomDate()));
                }
                roomTypeInfoDto.setRoomDates(dates);
            }
            roomTypeInfoDto.setList(list);
            return  roomTypeInfoDto ;
        }
        return null;

    }
}
