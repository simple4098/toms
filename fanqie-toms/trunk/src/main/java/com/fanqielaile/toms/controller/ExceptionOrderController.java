package com.fanqielaile.toms.controller;

import com.fanqie.util.Pagination;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.service.IExceptionOrderService;
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

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/28.
 */
@Controller
@RequestMapping(value = "/exceptionOrder")
public class ExceptionOrderController extends BaseController {
    Logger logger = LoggerFactory.getLogger(ExceptionOrderController.class);
    @Resource
    private IExceptionOrderService exceptionOrderService;

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
}
