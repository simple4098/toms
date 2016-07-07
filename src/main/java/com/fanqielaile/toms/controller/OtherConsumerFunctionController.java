package com.fanqielaile.toms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fanqielaile.toms.dto.OtherConsumerInfoDto;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.IMyselfChannelService;
import com.fanqielaile.toms.service.IOtherConsumerInfoService;
import com.fanqielaile.toms.service.IPmsChannelNameService;
import com.fanqielaile.toms.support.tag.AuthorizeTagConsumer;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.JsonModel;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/3/25
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/personality/info")
public class OtherConsumerFunctionController extends BaseController {
    private static final Logger log = Logger.getLogger(OtherConsumerFunctionController.class);
    @Resource
    private IOtherConsumerInfoService otherConsumerInfoService;
    @Resource
    private IMyselfChannelService myselfChannelService;
    @Resource
    private IPmsChannelNameService pmsChannelNameService;

    @RequestMapping("/otherConsumer")
    public String orderExport(Model model) {
        UserInfo currentUser = getCurrentUser();
        /*WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext context = webApplicationContext.getServletContext();
        String contextPath = context.getContextPath();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthorizeTagConsumer authorizeTagConsumer = new AuthorizeTagConsumer();
        boolean b = authorizeTagConsumer.isAllow(contextPath, "/personality/otherConsumer", "GEI", authentication);
        if (b && currentUser!=null){
            List<OtherConsumerInfoDto> otherConsumerList = otherConsumerInfoService.findOtherConsumerInfo(currentUser.getCompanyId(),null);
            boolean function = otherConsumerInfoService.findOtherConsumerFunction(currentUser.getCompanyId());
            model.addAttribute(Constants.DATA,otherConsumerList);
            model.addAttribute(Constants.STATUS,function);
        }else {
            return "/other_consumer/info_error";
        }*/
        List<OtherConsumerInfoDto> otherConsumerList = otherConsumerInfoService.findOtherConsumerInfo(currentUser.getCompanyId(), null);
        OtherConsumerFunction function = otherConsumerInfoService.findOtherConsumerFunction(currentUser.getCompanyId());
        model.addAttribute(Constants.DATA, otherConsumerList);
        model.addAttribute(Constants.STATUS, null == function ? false : function.getStatus());
        return "/other_consumer/info";
    }

    @RequestMapping("/editView")
    @ResponseBody
    public Object editView(Model model, String consumerInfoId) {
        JsonModel jsonModel = new JsonModel();
        UserInfo currentUser = getCurrentUser();
        List<OtherConsumerInfoDto> otherConsumerList = otherConsumerInfoService.findOtherConsumerInfo(currentUser.getCompanyId(), consumerInfoId);
        if (!CollectionUtils.isEmpty(otherConsumerList)) {
            jsonModel.put(Constants.DATA, otherConsumerList.get(0));
            jsonModel.put(Constants.STATUS, Constants.SUCCESS);
        } else {
            jsonModel.put(Constants.STATUS, Constants.ERROR);
        }
        return jsonModel;
    }

    @RequestMapping("/updateConsumerInfo")
    @ResponseBody
    public Object updateView(String json) {
        JsonModel jsonModel = new JsonModel();
        UserInfo currentUser = getCurrentUser();
        OtherConsumerInfoDto priceRecordJsonBeans = JSON.parseObject(json, new TypeReference<OtherConsumerInfoDto>() {
        });
        try {
            otherConsumerInfoService.updateOtherConsumerInfo(priceRecordJsonBeans, currentUser);
            jsonModel.put(Constants.STATUS, Constants.SUCCESS);
        } catch (Exception e) {
            log.error("异常", e);
            jsonModel.put(Constants.STATUS, Constants.ERROR);
            jsonModel.put(Constants.MESSAGE, e.getMessage());
        }

        return jsonModel;
    }

    @RequestMapping("/addConsumerInfo")
    @ResponseBody
    public Object addConsumerInfo(String json) {
        JsonModel jsonModel = new JsonModel();
        UserInfo currentUser = getCurrentUser();
        OtherConsumerInfoDto priceRecordJsonBeans = JSON.parseObject(json, new TypeReference<OtherConsumerInfoDto>() {
        });
        priceRecordJsonBeans.setCompanyId(currentUser.getCompanyId());
        try {
            otherConsumerInfoService.saveOtherConsumerInfo(priceRecordJsonBeans, currentUser);
            jsonModel.put(Constants.STATUS, Constants.SUCCESS);
        } catch (Exception e) {
            jsonModel.put(Constants.STATUS, Constants.ERROR);
            jsonModel.put(Constants.MESSAGE, e.getMessage());
        }
        return jsonModel;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String consumerInfoId) {
        JsonModel jsonModel = new JsonModel();
        Integer count = 0;
        try {
            count = otherConsumerInfoService.getOrderRecordCount(consumerInfoId);
        } catch (Exception e) {
            log.error("获取其他消费项目订单记录条数异常", e);
            jsonModel.put(Constants.STATUS, Constants.ERROR400);
            jsonModel.put(Constants.MESSAGE, e);
            return jsonModel;
        }
        if (count > 0) {
            jsonModel.put(Constants.STATUS, Constants.ERROR400);
            jsonModel.put(Constants.MESSAGE, "该消费项目已经存在订单记录了，不能删除");
            return jsonModel;
        }
        try {
            otherConsumerInfoService.deleteOtherConsumerInfo(consumerInfoId);
            jsonModel.put(Constants.STATUS, Constants.SUCCESS200);
        } catch (Exception e) {
            log.error("删除其他消费异常", e);
            jsonModel.put(Constants.STATUS, Constants.ERROR400);
            jsonModel.put(Constants.MESSAGE, e);
        }

        return jsonModel;
    }

    @RequestMapping("/manual")
    @ResponseBody
    public Object manual() {
        UserInfo currentUser = getCurrentUser();
        JsonModel jsonModel = new JsonModel();
        try {
            OtherConsumerFunction otherConsumerFunction = otherConsumerInfoService.findOtherConsumerFunction(currentUser.getCompanyId());
            if (null != otherConsumerFunction && otherConsumerFunction.getStatus()) {
                OtherConsumerInfoDto otherConsumerInfoDto = otherConsumerInfoService.findChildOtherConsumerInfo(currentUser.getCompanyId());
                jsonModel.put(Constants.DATA, otherConsumerInfoDto);
                jsonModel.put(Constants.STATUS, Constants.SUCCESS);
            } else {
                jsonModel.put(Constants.STATUS, Constants.ERROR);
            }
        } catch (Exception e) {
            log.error("删除其他消费异常", e);
            jsonModel.put(Constants.STATUS, Constants.ERROR400);
            jsonModel.put(Constants.MESSAGE, e);
        }

        return jsonModel;
    }

    @RequestMapping("/updateFunction")
    @ResponseBody
    public Object updateFunction(String status) {
        UserInfo currentUser = getCurrentUser();
        JsonModel jsonModel = new JsonModel();
        try {
            otherConsumerInfoService.updateFunction(currentUser.getCompanyId(), status);
            jsonModel.put(Constants.STATUS, Constants.SUCCESS);
        } catch (Exception e) {
            log.error("修改其他消费状态异常", e);
            jsonModel.put(Constants.STATUS, Constants.ERROR400);
            jsonModel.put(Constants.MESSAGE, e);
        }

        return jsonModel;
    }

    /**
     * 跳转到自定义渠道页面
     * @param model
     * @return
     */
    @RequestMapping("/myself_channel_page")
    public String toMyselfChannelPage(Model model){
        UserInfo currentUser = getCurrentUser();
        List<MyselfChannel> myselfChannelList = this.myselfChannelService.findMyselfChannelList(currentUser);
        OtherConsumerFunction function = otherConsumerInfoService.findOtherConsumerFunction(currentUser.getCompanyId());
        model.addAttribute(Constants.DATA, myselfChannelList);
        model.addAttribute(Constants.STATUS, null == function ? false : function.getMyselfChannelStatus());
        return "/other_consumer/myself_channel";
    }


    /**
     * 跳转到pms渠道名页面
     * @return
     */
    @RequestMapping(value = "/pms_channel_name_page")
    public String toPmsChannelNamePage(Model model){
        UserInfo currentUser = getCurrentUser();
        PmsChannelNameBean pmsChannelNameBean = this.pmsChannelNameService.findPmsChannelNameByCompanyId(currentUser);
        OtherConsumerFunction function = otherConsumerInfoService.findOtherConsumerFunction(currentUser.getCompanyId());
        model.addAttribute(Constants.DATA, pmsChannelNameBean);
        model.addAttribute(Constants.STATUS, null == function ? false : function.getPmsChannelNameStatus());
        return "/other_consumer/pms_channel_name";
    }
}
