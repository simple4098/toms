package com.fanqielaile.toms.controller;

import com.fanqie.core.dto.ParamDto;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IRoomTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * DESC : 房态房量
 * @author : 番茄木-ZLin
 * @data : 2015/6/2
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/oms")
public class RoomTypeController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(RoomTypeController.class);
    @Resource
    private IRoomTypeService roomTypeService;

    @RequestMapping("/obtRoomType")
    public String roomType(Model model,ParamDto paramDto){
        return "/room/room_type";
    }
    @RequestMapping("/ajax/obtRoomType")
    public String ajaxRoomType(Model model,ParamDto paramDto){
        UserInfo userInfo = getCurrentUser();
        paramDto.setCompanyId(userInfo.getCompanyId());
        paramDto.setUserId(userInfo.getId());
        paramDto.setStartDate("2015-04-02");
        paramDto.setEndDate("2015-05-01");
        paramDto.setAccountId("14339");
        try {
            RoomTypeInfoDto roomType = roomTypeService.findRoomType(paramDto, userInfo);
            model.addAttribute("roomType",roomType);
            model.addAttribute("paramDto",paramDto);
        } catch (Exception e) {
            logger.error("房态房量获取失败",e);
        }
        return "/room/room_type_ajax";
    }
}
