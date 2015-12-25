package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.service.impl.CtripHotelInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2015/12/24.
 * 携程控制层
 */
@Controller
@RequestMapping(value = "/ctrip")
public class CtripHotelController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CtripHotelController.class);
    @Resource
    private CtripHotelInfoServiceImpl ctripHotelService;

    /**
     * 同步携程酒店信息
     *
     * @return
     */
    @RequestMapping(value = "/get_hotel_info")
    @ResponseBody
    public void getHotelInfo(Model model) {
        try {
            logger.info("处理更新携程酒店、房型信息方法开始======>");
            this.ctripHotelService.getHotelInfo();
            model.addAttribute("status", true);
            model.addAttribute("message", "处理成功");
            logger.info("处理更新携程酒店、房型信息方法结束=====>");
        } catch (Exception e) {
            logger.info("处理更新携程酒店、房型信息方法出错" + e, e);
        }
    }
}
