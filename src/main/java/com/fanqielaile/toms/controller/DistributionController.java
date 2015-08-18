package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.support.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC : 分销管理
 * @author : 番茄木-ZLin
 * @data : 2015/8/17
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/distribution")
public class DistributionController extends BaseController{
    private static  final Logger log = LoggerFactory.getLogger(DistributionController.class);
    @Resource
    private IOtaInfoService otaInfoService;

    //渠道列表
    @RequestMapping("/otaList")
    public String otaList(Model model){
        UserInfo currentUser = getCurrentUser();
        List<OtaInfoRefDto> list = otaInfoService.findOtaInfoListByCompanyId(currentUser.getCompanyId());
        model.addAttribute("otaList", list);
        return "/distribution/ota_list";
    }
    //申请开通
    @RequestMapping("/otaOpen")
    public void otaOpen(Model model,OtaInfoRefDto otaInfoRefDto){
        UserInfo currentUser = getCurrentUser();
        otaInfoRefDto.setCompanyId(currentUser.getCompanyId());
        try {
            otaInfoService.saveOtaInfo(otaInfoRefDto);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } catch (Exception e) {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE,e.getMessage());
            log.debug("开通渠道异常:"+e);
        }
    }
}
