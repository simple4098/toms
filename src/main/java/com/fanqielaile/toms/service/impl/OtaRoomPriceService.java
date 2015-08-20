package com.fanqielaile.toms.service.impl;

import com.fanqie.util.Constants;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.service.IOtaRoomPriceService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * DESC :房价管理
 * @author : 番茄木-ZLin
 * @data : 2015/8/19
 * @version: v1.0.0
 */
@Service
public class OtaRoomPriceService implements IOtaRoomPriceService {

    @Resource
    private CompanyDao companyDao;

    @Override
    public List<RoomTypeInfo> obtOmsRoomInfo(BangInn bangInn,String companyId) throws Exception {
        Company company = companyDao.selectCompanyById(companyId);
        String room_type = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.ROOM_TYPE);
        String roomTypeGets = HttpClientUtil.httpGets(room_type, null);
        JSONObject jsonObject = JSONObject.fromObject(roomTypeGets);
        if (Constants.SUCCESS.equals(jsonObject.get("status").toString()) && jsonObject.get("list")!=null){
            return JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
        }
        return  null;
    }
}
