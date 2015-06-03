package com.fanqielaile.toms.controller;

import com.fanqie.core.domain.OrderSource;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.ICompanyService;
import com.fanqielaile.toms.service.IRoomTypeService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DESC : 房态房量
 * @author : 番茄木-ZLin
 * @data : 2015/6/2
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/oms")
public class RoomTypeController extends BaseController {
    @Resource
    private IRoomTypeService roomTypeService;

    @RequestMapping("/getRoomType")
    public String roomType(Model model,ParamDto paramDto){
        UserInfo userInfo = getCurrentUser();
        paramDto.setCompanyId(userInfo.getCompanyId());
        paramDto.setUserId(userInfo.getId());
        paramDto.setStartDate("2015-04-02");
        paramDto.setEndDate("2015-05-01");
        paramDto.setAccountId("15521");
        roomTypeService.findRoomType(paramDto,userInfo);
        //Company company = companyService.findCompanyById(userInfo.getCompanyId());

        String s = String.valueOf(new Date().getTime());
        String signature = DcUtil.obtMd5("105"+s+"MT"+"mt123456");
        String url ="http://192.168.1.158:8888/api/getRoomType?timestamp="+s+"&otaId="+105+"&accountId=15521&from=2015-04-02&to=2015-05-01"+"&signature="+signature;
        try {
            String httpGets = HttpClientUtil.httpGets(url);
            JSONObject jsonObject = JSONObject.fromObject(httpGets);
            ArrayList<RoomTypeInfo> list = (ArrayList<RoomTypeInfo>)JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
            System.out.println(list.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/room/room_type";
    }
}
