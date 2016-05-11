package com.fanqielaile.toms.controller;


import com.fanqie.core.domain.OperateTrend;
import com.fanqie.core.dto.CustomerDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.Pagination;
import com.fanqielaile.toms.dto.xl.CustomerAnalysisDto;
import com.fanqielaile.toms.dto.xl.CustomerParamDto;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOperateTrendService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.decorator.FrontendPagerDecorator;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.Order.Direction;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * DESC : 趋势报表
 * @author : 番茄木-ZLin
 * @data : 2015/4/22
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/operate")
public class OperateTrendController extends BaseController  {
    private static Logger logger = LoggerFactory.getLogger(OperateTrendController.class);

    @Resource
    private IOperateTrendService operateTrendService;
    @Resource
    private IOrderService orderService;


    //运营趋势页面
    @RequestMapping("/qs")
    public String qs(){
        return "/operate/qs";
    }



    //运营趋势折线图
    @RequestMapping("/qsDetail")
    public void qsDetail(Model model,ParamDto paramDto){
        try {
            UserInfo currentUser = getCurrentUser();
            Map<String, Object> qsDetail = operateTrendService.findQsDetail(paramDto, currentUser);
            model.addAttribute("result",qsDetail);
            OperateTrend trend = operateTrendService.findOperateTrend(paramDto, currentUser);
            model.addAttribute("operateTrend",trend);
        } catch (Exception e) {
            logger.error("趋势报表异常",e);
        }

    }

    /**
     * 客服资料分析
     * @param paramDto  查询参数
     */
    @RequestMapping("/kf")
    public String kf(Model model,ParamDto paramDto){
        try {
            UserInfo currentUser = getCurrentUser();
            CustomerDto customer = operateTrendService.findCustomer(paramDto,currentUser);
            model.addAttribute("customer",customer);
            model.addAttribute("paramDto",paramDto);
        } catch (Exception e) {
            logger.error("客户资料异常",e);
        }
        return "/operate/kf";
    }
    /**
     * 客服资料分析
     * @param paramDto  查询参数
     */
    @RequestMapping("/customer_analysis")
    public String customerAnalysis(Model model,CustomerParamDto customerParamDto){
    	try {
    		UserInfo currentUser = getCurrentUser();
    		operateTrendService.initCustomerParam(customerParamDto);
    		List<CustomerAnalysisDto> provinceAnalysisList = operateTrendService.selectProvinceGuestNumList(customerParamDto,currentUser,new PageBounds(customerParamDto.getPage(), defaultRows,Order.formString("province_guest_count.desc")));
    		model.addAttribute("provinceAnalysisList",provinceAnalysisList);
    		Paginator paginator = ((PageList) provinceAnalysisList).getPaginator();
            Pagination pagination = PaginationHelper.toPagination(paginator);
            FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
    	    model.addAttribute("pagination",pagination);
            model.addAttribute("pageDecorator",pageDecorator);
    		List<CustomerAnalysisDto> cityAnalysisList = operateTrendService.selectCityGuestNumList(customerParamDto,currentUser,new PageBounds(customerParamDto.getCityPage(), defaultRows,Order.formString("percent.desc")));
    		model.addAttribute("cityAnalysisList",cityAnalysisList);
    		Paginator paginatorCity = ((PageList) cityAnalysisList).getPaginator();
            Pagination paginationCity = PaginationHelper.toPagination(paginatorCity);
            FrontendPagerDecorator pageDecoratorCity = new FrontendPagerDecorator(paginationCity);
    	    model.addAttribute("paginationCity",paginationCity);
            model.addAttribute("pageDecoratorCity",pageDecoratorCity);
            Integer totalGuestCount = operateTrendService.getTotalGuestCount(customerParamDto,currentUser);
            model.addAttribute("totalGuestCount",totalGuestCount);
            model.addAttribute("customerParamDto",customerParamDto);
    	} catch (Exception e) {
    		logger.error("客户分析执行异常",e);
    	}
    	return "/operate/customer_analysis";
    }

    /**
     * 订单来源页面
     */
    @RequestMapping("/order")
    public String orderView(){

        return "/operate/order";
    }


    /**
     * 订单来源详情/概况
     * @param paramDto 查询参数
     */
    @RequestMapping("/ajax/orderDetail")
    public void orderD(Model model,ParamDto paramDto){
        try {
            UserInfo userInfo =getCurrentUser();
            Map<String, Object> orderSourceDetail = orderService.findOrderSourceDetail(paramDto,userInfo);
            model.addAttribute("data",orderSourceDetail.get("data"));
            model.addAttribute("list",orderSourceDetail.get("list"));
            model.addAttribute("orderSource",orderSourceDetail.get("orderSource"));
        } catch (Exception e) {
            logger.error("订单来源详情/概况",e);
        }
    }


}
