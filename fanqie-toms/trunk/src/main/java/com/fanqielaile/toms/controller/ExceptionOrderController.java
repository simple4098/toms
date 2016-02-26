package com.fanqielaile.toms.controller;

import com.fanqie.util.Pagination;
import com.fanqielaile.toms.dto.OrderParamDto;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.service.IExceptionOrderService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.decorator.FrontendPagerDecorator;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.JsonModel;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2016/1/28.
 */
@Controller
@RequestMapping(value = "/exceptionOrder")
public class ExceptionOrderController extends BaseController {
    Logger logger = LoggerFactory.getLogger(ExceptionOrderController.class);
    @Resource
    private IExceptionOrderService exceptionOrderService;
    @Resource
    private IOrderService orderService;

    /**
     * 查询所有异常订单
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/find_exceptionOrders")
    public String findAllExceptionOrder(Model model, @RequestParam(defaultValue = "1", required = false) int page) {
        try {
            List<Order> allExceptionOrder = this.exceptionOrderService.findAllExceptionOrder(new PageBounds(page, defaultRows));
            model.addAttribute(Constants.DATA, allExceptionOrder);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            //封装分页信息
            Paginator paginator = ((PageList) allExceptionOrder).getPaginator();
            Pagination pagination = PaginationHelper.toPagination(paginator);
            FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
            model.addAttribute("pagination", pagination);
            model.addAttribute("pageDecorator", pageDecorator);
        } catch (Exception e) {
            logger.info("查询异常订单出错" + e);
        }
        return "/order/exception_orders";
    }

    /**
     * 关闭订单
     *
     * @param model
     * @param id
     */
    @RequestMapping(value = "/close_order")
    @ResponseBody
    public void closeOrder(Model model, String id) {
        try {
            OrderParamDto orderParamDto = this.orderService.findOrderById(id);
            if (null != orderParamDto) {
                JsonModel jsonModel = this.exceptionOrderService.dealCloseOrder(orderParamDto);
                model.addAttribute(Constants.STATUS, jsonModel.isSuccess());
                model.addAttribute(Constants.MESSAGE, jsonModel.getMessage());
            } else {
                model.addAttribute(Constants.STATUS, false);
                model.addAttribute(Constants.MESSAGE, "没有找到订单信息");
            }
        } catch (Exception e) {
            logger.info("处理关闭订单出错" + e);
        }
    }

    /**
     * 获取oms订单状态
     *
     * @param model
     * @param id
     */
    @RequestMapping(value = "/find_oms_order_status")
    private void findOmsOrderStatus(Model model, String id) {
        try {
            OrderParamDto orderParamDto = this.orderService.findOrderById(id);
            if (null != orderParamDto) {
                Map<String, Object> map = this.exceptionOrderService.findOmsOrderStatus(orderParamDto);
                model.addAttribute(Constants.STATUS, map.get("status"));
                model.addAttribute(Constants.MESSAGE, map.get("orderStatus"));
            } else {
                model.addAttribute(Constants.STATUS, false);
                model.addAttribute(Constants.MESSAGE, "没有找到订单信息");
            }
        } catch (Exception e) {
            logger.info("获取oms订单状态异常" + e);
        }
    }

    /**
     * 获取pms订单状态
     *
     * @param model
     * @param id
     */
    @RequestMapping(value = "/find_pms_order_status")
    public void findPmsOrderStatus(Model model, String id) {
        try {
            OrderParamDto orderParamDto = this.orderService.findOrderById(id);
            if (null != orderParamDto) {
                Map<String, Object> map = this.exceptionOrderService.finPmsOrderStatus(orderParamDto);
                model.addAttribute(Constants.STATUS, map.get("status"));
                model.addAttribute(Constants.MESSAGE, map.get("orderStatus"));
            } else {
                model.addAttribute(Constants.STATUS, false);
                model.addAttribute(Constants.MESSAGE, "没有找到订单信息");
            }
        } catch (Exception e) {
            logger.info("获取pms订单状态异常" + e);
        }
    }
}
