package com.fanqielaile.toms.controller;

import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.Pagination;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.*;
import com.fanqielaile.toms.service.impl.InnLabelService;
import com.fanqielaile.toms.support.decorator.FrontendPagerDecorator;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.TomsUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private IOtaRoomPriceService otaRoomPriceService;
    @Resource
    private IRoomTypeService roomTypeService;


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
    public String orderConfig(Model model, String innLabelId,String keywords,@RequestParam(defaultValue = "1", required = false) int page) {
        UserInfo currentUser = getCurrentUser();
        currentUser.setInnLabelId(innLabelId);
        currentUser.setKeywords(keywords);
        List<OtaInfoRefDto> list = otaInfoService.findOtaInfoListByCompanyId(currentUser.getCompanyId());
        List<OrderConfigDto> orderConfigDtoList = orderConfigService.findOrderConfigByCompanyId(list,currentUser, new PageBounds(page, defaultRows));
        List<InnLabel> innLabels = this.innLabelService.findLabelsByCompanyId(currentUser.getCompanyId());
        model.addAttribute("labels", innLabels);
        //分页对象
        Paginator paginator = ((PageList) orderConfigDtoList).getPaginator();
        Pagination pagination = PaginationHelper.toPagination(paginator);
        FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
        model.addAttribute("pagination",pagination);
        model.addAttribute("pageDecorator",pageDecorator);
        //保存查询条件
        model.addAttribute("innLabel", innLabelId);
        model.addAttribute("orderConfigDtoList",orderConfigDtoList);
        model.addAttribute("otaList", list);
        model.addAttribute("keywords", keywords);
        return "/distribution/order_inn_config_list";
    }

    //客栈接单配置
    @RequestMapping("/ajax/orderConfigDetail")
    public String orderConfigDetail(Model model,String innId){
        UserInfo currentUser = getCurrentUser();
        BangInnDto bangInnDto = bangInnService.findBangInnByInnIdCompanyId(innId,currentUser.getCompanyId());
        model.addAttribute("inn",bangInnDto);
        List<OrderConfigDto> list = orderConfigService.findOrderConfigByCompanyIdAndInnId(currentUser.getCompanyId(), innId);
        model.addAttribute("orderConfigList",list);
        return "/distribution/inn_config_detail";
    }
    //保存客栈接单哦配置
    @RequestMapping("/ajax/saveConfig")
    public void saveConfig(Model model,String innId,String[] otaInfoId,HttpServletRequest request){
        UserInfo currentUser = getCurrentUser();
        List<OrderConfigDto> list = TomsUtil.orderConfig(innId, otaInfoId, currentUser, request);
        try {
            orderConfigService.saveOrderConfig(list,innId, currentUser.getCompanyId());
            model.addAttribute(Constants.STATUS,Constants.SUCCESS);

        } catch (Exception e) {
            model.addAttribute(Constants.STATUS,Constants.ERROR);
            log.error("收单设置异常"+e.getMessage());
        }
    }

    //房价管理-列表
    @RequestMapping("/fangPrice")
    public String fangPrice(Model model, String innLabelId, String keywords,@RequestParam(defaultValue = "1", required = false) int page){
        UserInfo currentUser = getCurrentUser();
        currentUser.setInnLabelId(innLabelId);
        currentUser.setKeywords(keywords);
        List<OtaInfoRefDto> list = otaInfoService.findOtaInfoListByCompanyId(currentUser.getCompanyId());
        List<OrderConfigDto> orderConfigDtoList = orderConfigService.findFangPriceConfigByCompanyId(list,currentUser, new PageBounds(page, defaultRows));
        List<InnLabel> innLabels = innLabelService.findLabelsByCompanyId(currentUser.getCompanyId());
        model.addAttribute("labels", innLabels);
        //分页对象
        Paginator paginator = ((PageList) orderConfigDtoList).getPaginator();
        Pagination pagination = PaginationHelper.toPagination(paginator);
        FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
        model.addAttribute("pagination",pagination);
        model.addAttribute("pageDecorator",pageDecorator);
        //保存查询条件
        model.addAttribute("innLabel", innLabelId);
        model.addAttribute("keywords", keywords);
        model.addAttribute("orderConfigDtoList",orderConfigDtoList);
        model.addAttribute("otaList", list);
        return "/distribution/fang_price";
    }

    //房价管理-客栈房型信息
    @RequestMapping("/fangPriceDetail")
    public String fangPriceDetail(Model model, ParamDto paramDto, String innId, String otaInfoId){
        UserInfo currentUser = getCurrentUser();
        if (currentUser!=null){
            BangInn bangInn = bangInnService.findBangInnByCompanyIdAndInnId(currentUser.getCompanyId(), Integer.valueOf(innId));
            try {
                RoomTypeInfoDto roomType = roomTypeService.findRoomType(otaInfoId,paramDto, bangInn);
                model.addAttribute("otaInfoId",otaInfoId);
                model.addAttribute("innId",innId);
                model.addAttribute("bangInn",bangInn);
                model.addAttribute("roomType",roomType);
            } catch (Exception e) {
                log.error("房价设置异常"+e.getMessage());
            }
            return "/distribution/fang_price_detail_new";
        }else {
            return "/error";
        }

    }

   //房价管理-客栈房型信息
    @RequestMapping("/addFangPrice")
    public String addFangPrice(Model model, String innId, String otaInfoId){
        UserInfo currentUser = getCurrentUser();
        BangInn bangInn = bangInnService.findBangInnByCompanyIdAndInnId(currentUser.getCompanyId(), Integer.valueOf(innId));
        try {
            List<RoomTypeInfo> list = otaRoomPriceService.obtOmsRoomInfo(bangInn);
            model.addAttribute("list",list);
            model.addAttribute("otaInfoId",otaInfoId);
            model.addAttribute("innId",innId);
            model.addAttribute("bangInn",bangInn);
        } catch (Exception e) {
           log.error("房价设置异常"+e.getMessage());
        }
        return "/distribution/fang_price_detail_new_tian";
    }

    /*//房价管理-展示房价设置
    @RequestMapping(value = "/ajax/room_price_detail",method = RequestMethod.POST)
    public String roomPriceDetail(Model model,OtaRoomPriceDto roomPriceDto,String roomTypeName){
        UserInfo currentUser = getCurrentUser();
        roomPriceDto.setCompanyId(currentUser.getCompanyId());
        OtaRoomPriceDto otaRoomPriceDto = otaRoomPriceService.findRoomPrice(roomPriceDto);
        model.addAttribute("roomPrice",otaRoomPriceDto);
        model.addAttribute("param",roomPriceDto);
        model.addAttribute("roomTypeName",roomTypeName);
        return "/distribution/room_price_detail";
    }
    //房价管理-保存房价设置
    @RequestMapping(value = "/ajax/saveRoomPrice",method = RequestMethod.POST)
    public void saveRoomPriceDetail(Model model,OtaRoomPriceDto roomPriceDto){
        UserInfo currentUser = getCurrentUser();
        roomPriceDto.setCompanyId(currentUser.getCompanyId());
        roomPriceDto.setModifierId(currentUser.getId());
        BangInn bangInn = bangInnService.findBangInnByCompanyIdAndInnId(currentUser.getCompanyId(), roomPriceDto.getInnId());
        roomPriceDto.setAccountId(bangInn.getAccountId());
        try {
            otaRoomPriceService.saveRoomPriceDto(roomPriceDto,bangInn);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
        } catch (Exception e) {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, e.getMessage());
        }
    }*/


    //房价管理-同步房型价格到卖房网站
    @RequestMapping(value = "/ajax/sync",method = RequestMethod.POST)
    @ResponseBody
    public Object tpPrice(String innId,String otaInfoId,String json){
        Result result = new Result();
        UserInfo currentUser = getCurrentUser();
        if (currentUser!=null){
            String companyId = currentUser.getCompanyId();
            String userId = currentUser.getId();
            OtaInfoRefDto infoRefDto = otaInfoService.findOtaInfoByCompanyIdAndOtaInnOtaId(companyId, otaInfoId);
            try {
                ITPService service = infoRefDto.getOtaType().create();
                service.updateRoomTypePrice(infoRefDto, innId, companyId, userId,json);
                result.setStatus(Constants.SUCCESS200);
            } catch (Exception e) {
                log.error("同步价格失败:" + e.getMessage());
                result.setMessage("同步价格失败:" + e.getMessage());
                result.setStatus(Constants.ERROR400);
            }
        }
        return result;
    }



}
