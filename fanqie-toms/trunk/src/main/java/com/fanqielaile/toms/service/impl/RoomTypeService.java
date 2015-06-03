package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IRoomTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

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
    public void findRoomType(ParamDto paramDto,UserInfo userInfo) {
        Company company = companyDao.selectCompanyById(userInfo.getCompanyId());
        String roomTypeUrl = DcUtil.roomTypeUrl(paramDto, company.getOtaId(), company.getUserAccount(), company.getUserPassword(), CommonApi.ROOM_TYPE);
        System.out.println("=========:"+roomTypeUrl);
        String s = String.valueOf(new Date().getTime());
       /* String signature = DcUtil.obtMd5("105" + s + "MT" + "mt123456");
        String url ="http://192.168.1.158:8888/api/getRoomType?timestamp="+s+"&otaId="+105+"&accountId=15521&from=2015-04-02&to=2015-05-01"+"&signature="+signature;
        try {
            String httpGets = HttpClientUtil.httpGets(url);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
