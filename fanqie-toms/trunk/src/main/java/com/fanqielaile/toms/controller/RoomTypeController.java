package com.fanqielaile.toms.controller;

import com.fanqie.util.HttpClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;

/**
 * DESC : 房态房量
 * @author : 番茄木-ZLin
 * @data : 2015/6/2
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/oms")
public class RoomTypeController extends BaseController {

    @RequestMapping("/getRoomType")
    public String roomType(Model model){
        String v = new Date().getTime()+"";
        String url ="http://192.168.1.158:8888/tomatoOmsOtaRoomtype/getRoomtype?time="+v+"&accountId=17297&from=2015-06-02&to=2015-07-02";
        try {
            String httpGets = HttpClientUtil.httpGets(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/room/room_type";
    }
}
