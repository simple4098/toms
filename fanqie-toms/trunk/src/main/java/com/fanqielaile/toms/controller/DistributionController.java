package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.OrderConfigDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.IOrderConfigService;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.service.impl.InnLabelService;
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
    @Resource
    private IOrderConfigService orderConfigService;
    @Resource
    private InnLabelService innLabelService;
    @Resource
    private IBangInnService bangInnService;


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

    //接单设置-客栈列表
    @RequestMapping("/orderConfig")
    public String orderConfig(Model model, String innLabelId, String keywords,@RequestParam(defaultValue = "1", required = false) int page) {
        UserInfo currentUser = getCurrentUser();
        currentUser.setInnLabelId(innLabelId);
        List<OtaInfoRefDto> list = otaInfoService.findOtaInfoListByCompanyId(currentUser.getCompanyId());
        List<OrderConfigDto> orderConfigDtoList = orderConfigService.findOrderConfigByCompanyId(list,currentUser, new PageBounds(page, defaultRows));
        List<InnLabel> innLabels = this.innLabelService.findLabelsByCompanyId(currentUser.getCompanyId());
        model.addAttribute("labels", innLabels);
        //分页对象
        Paginator paginator = ((PageList) orderConfigDtoList).getPaginator();
        model.addAttribute("pagination", PaginationHelper.toPagination(paginator));
        //保存查询条件
        model.addAttribute("innLabel", innLabelId);
        model.addAttribute("keywords", keywords);
        model.addAttribute("orderConfigDtoList",orderConfigDtoList);
        model.addAttribute("otaList", list);
        return "/distribution/order_inn_config_list";
    }

    @RequestMapping("/ajax/orderConfigDetail")
    public String orderConfigDetail(Model model,String innId){
        UserInfo currentUser = getCurrentUser();
        BangInnDto bangInnDto = bangInnService.findBangInnById(innId);
        model.addAttribute("inn",bangInnDto);
        List<OrderConfigDto> list = orderConfigService.findOrderConfigByCompanyIdAndInnId(currentUser.getCompanyId(), innId);
        model.addAttribute("orderConfigList",list);
        return "/distribution/inn_config_detail";
    }
}
