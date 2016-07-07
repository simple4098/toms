package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.model.MyselfChannel;
import com.fanqielaile.toms.service.IMyselfChannelService;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.xpath.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wangdayin on 2016/7/6.
 */
@Controller
@RequestMapping(value = "/myselfChannel")
public class MySelfChannelController extends BaseController{
    @Resource
    private IMyselfChannelService myselfChannelService;


    /**
     * 更新公司pms渠道开闭状态
     * @param model
     * @param status
     */
    @RequestMapping(value = "/update_company_pms_channel_status")
    public void updatePmsCompanyChannelStatus(Model model,Boolean status){
        if (null != status) {
            this.myselfChannelService.modifyPmsCompanyChannelStatus(getCurrentUser(), status);
            model.addAttribute(Constants.STATUS,true);
            model.addAttribute(Constants.MESSAGE,"修改成功");
        }else {
            model.addAttribute(Constants.STATUS,false);
            model.addAttribute(Constants.MESSAGE,"参数为空，请重试");
        }
    }

    /**
     * 更新公司自定义渠道开闭状态
     * @param model
     * @param status
     */
    @RequestMapping(value = "/update_company_channel_status")
    public void updateCompanyChannelStatus(Model model,Boolean status){
        if (null != status) {
            this.myselfChannelService.modifyCompanyChannelStatus(getCurrentUser(), status);
            model.addAttribute(Constants.STATUS,true);
            model.addAttribute(Constants.MESSAGE,"修改成功");
        }else {
            model.addAttribute(Constants.STATUS,false);
            model.addAttribute(Constants.MESSAGE,"参数为空，请重试");
        }
    }

    /**
     * 创建自定义渠道
     * @param model
     * @param myselfChannel
     */
    @RequestMapping("/create_myself_channel")
    public void createMyselfChannel(Model model,MyselfChannel myselfChannel){
        if (StringUtils.isNotEmpty(myselfChannel.getChannelName())){
            MyselfChannel myselfChannelByName = this.myselfChannelService.findMyselfChannelByName(myselfChannel.getChannelName());
            if (null == myselfChannelByName) {
                this.myselfChannelService.createMyselfChannel(myselfChannel, getCurrentUser());
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
                model.addAttribute(Constants.MESSAGE, "创建成功");
            }else {
                model.addAttribute(Constants.STATUS,Constants.ERROR);
                model.addAttribute(Constants.MESSAGE,"已经存在的渠道名称，请重新输入");
            }
        }else {
            model.addAttribute(Constants.STATUS,Constants.ERROR);
            model.addAttribute(Constants.MESSAGE,"参数为空，请重试!");
        }
    }

    /**
     * 修改自定义渠道
     * @param model
     * @param id
     * @param channelName
     */
    @RequestMapping(value = "/update_myself_channel")
    public void updateMyselfChannel(Model model,String id,String channelName){
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(channelName)){
            Map<String,Object> result = this.myselfChannelService.modifyMyselfChannel(id,channelName,getCurrentUser());
            model.addAttribute(Constants.STATUS,result.get(Constants.STATUS));
            model.addAttribute(Constants.MESSAGE,result.get(Constants.MESSAGE));
        }else {
            model.addAttribute(Constants.STATUS,Constants.ERROR);
            model.addAttribute(Constants.MESSAGE,"参数为空，请重试!");
        }
    }

    /**
     * 删除自定义渠道
     * @param model
     * @param id
     */
    @RequestMapping(value = "/delete_myself_channel")
    public void deleteMyselfChannel(Model model,String id){
        if (StringUtils.isNotEmpty(id)){
            this.myselfChannelService.removeMyselfChannelById(id);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.MESSAGE, "删除成功");
        }else {
            model.addAttribute(Constants.STATUS,Constants.ERROR);
            model.addAttribute(Constants.MESSAGE,"参数为空，请重试!");
        }
    }


}
