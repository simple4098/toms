package com.fanqielaile.toms.controller;

import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.Pagination;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.MyselfChannel;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IMyselfChannelService;
import com.fanqielaile.toms.service.IRoomTypeService;
import com.fanqielaile.toms.support.decorator.FrontendPagerDecorator;
import com.fanqielaile.toms.support.util.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
    private static Logger logger = LoggerFactory.getLogger(RoomTypeController.class);
    @Resource
    private IRoomTypeService roomTypeService;
    @Resource
    private IMyselfChannelService myselfChannelService;

    @RequestMapping("/obtRoomType")
    public String roomType() {
        return "/room/room_type";
    }


    @RequestMapping("/inn/obtRoomType")
    public ModelAndView newRoomType(ParamDto paramDto, @RequestParam(defaultValue = "1", required = false) int page) {
        ModelAndView mav = new ModelAndView("/room/room_type_inn");
        UserInfo userInfo = getCurrentUser();
        List<BangInn> list = roomTypeService.findRoomTypeByName(paramDto.getTagId(), paramDto.getAccountId(), userInfo, new PageBounds(page, defaultRows));
        mav.addObject(Constants.DATA, list);
        //分页对象
        Paginator paginator = ((PageList) list).getPaginator();
        Pagination pagination = PaginationHelper.toPagination(paginator);
        FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
        mav.addObject("pagination", pagination);
        mav.addObject("pageDecorator", pageDecorator);
        mav.addObject("paramDto", paramDto);
        return mav;
    }

    @RequestMapping("/ajax/obtRoomType")
    public String ajaxRoomType(Model model, ParamDto paramDto) {
        UserInfo userInfo = getCurrentUser();
        paramDto.setCompanyId(userInfo.getCompanyId());
        paramDto.setUserId(userInfo.getId());
        try {
            RoomTypeInfoDto roomType = roomTypeService.findRoomType(paramDto, userInfo);
            model.addAttribute("roomType",roomType);
            model.addAttribute("paramDto",paramDto);
            //查询自定义渠道
            List<MyselfChannel> myselfChannelList = this.myselfChannelService.findMyselfChannelList(userInfo);
            model.addAttribute("myselfChannelList",myselfChannelList);
        } catch (Exception e) {
            logger.error("房态房量获取失败",e);
        }
        return "/room/room_type_ajax";
    }
}
