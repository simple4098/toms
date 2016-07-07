package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.PmsChannelNameBean;
import com.fanqielaile.toms.service.IPmsChannelNameService;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2016/7/6.
 */
@Controller
@RequestMapping(value = "/pmsChannelName")
public class PmsChannelNameController extends BaseController{

    @Resource
    private IPmsChannelNameService pmsChannelNameService;

    /**
     * 创建pms渠道名
     * @param model
     * @param pmsChannelNameBean
     */
    @RequestMapping(value = "/create_pms_channel_name")
    public void createPmsChannelName(Model model,PmsChannelNameBean pmsChannelNameBean){
        if (null != pmsChannelNameBean && StringUtils.isNotEmpty(pmsChannelNameBean.getPmsChannelName())){
            this.pmsChannelNameService.createPmsChannelName(pmsChannelNameBean,getCurrentUser());
            model.addAttribute(Constants.STATUS,true);
            model.addAttribute(Constants.MESSAGE,"创建成功");
        }else {
            model.addAttribute(Constants.STATUS,false);
            model.addAttribute(Constants.MESSAGE,"参数为空，请重试");
        }
    }
}
