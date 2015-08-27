package com.fanqielaile.toms.controller;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dto.OrderParamDto;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.enums.OrderMethod;
import com.fanqielaile.toms.enums.OrderStatus;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.JsonModel;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2015/7/6.
 * 订单管理
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(SystemController.class);
    @Resource
    private IOrderService orderService;

    /**
     * 订单列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping("find_orders")
    public String findOrder(Model model, @RequestParam(defaultValue = "1", required = false) int page, OrderParamDto orderParamDto) {
        try {
            UserInfo currentUser = getCurrentUser();
            //已处理订单
            orderParamDto.setOrderStatusString(OrderStatus.DEAL.name());
            List<OrderParamDto> orderParamDtos = this.orderService.findOrderByPage(currentUser.getCompanyId(), new PageBounds(page, defaultRows), orderParamDto);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, orderParamDtos);
            //封装分页信息
            Paginator paginator = ((PageList) orderParamDtos).getPaginator();
            model.addAttribute("pagination", PaginationHelper.toPagination(paginator));
            //分转查询条件
            model.addAttribute("order", orderParamDto);
            OrderParamDto paramDto = this.orderService.findOrders(currentUser.getCompanyId(), orderParamDto);
            model.addAttribute("orderPrice", paramDto);
            //渠道来源
            List<Order> orders = this.orderService.findOrderChancelSource(currentUser.getCompanyId());
            model.addAttribute("orderSource", orders);
        } catch (Exception e) {
            logger.error("查询订单列表失败", e);
            throw new TomsRuntimeException("查询订单列表失败");
        }
        return "/order/order_list";
    }

    /**
     * 待处理订单列表
     *
     * @param model
     * @param page
     * @param orderParamDto
     * @return
     */
    @RequestMapping("find_non_orders")
    public String findNonOrder(Model model, @RequestParam(defaultValue = "1", required = false) int page, OrderParamDto orderParamDto) {
        try {
            UserInfo currentUser = getCurrentUser();
            orderParamDto.setOrderStatusString(OrderStatus.NOT_DEAL.name());
            List<OrderParamDto> orderParamDtos = this.orderService.findOrderByPage(currentUser.getCompanyId(), new PageBounds(page, defaultRows), orderParamDto);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, orderParamDtos);
            //封装分页信息
            Paginator paginator = ((PageList) orderParamDtos).getPaginator();
            model.addAttribute("pagination", PaginationHelper.toPagination(paginator));
            //分转查询条件
            model.addAttribute("order", orderParamDto);
            OrderParamDto paramDto = this.orderService.findOrders(currentUser.getCompanyId(), orderParamDto);
            model.addAttribute("orderPrice", paramDto);
        } catch (Exception e) {
            logger.error("查询订单列表失败", e);
            throw new TomsRuntimeException("查询订单列表失败");
        }
        return "/order/order_non_list";
    }

    /**
     * 查询订单详细信息
     *
     * @param model
     * @param id
     */
    @RequestMapping("find_order")
    public void findOrderById(Model model, String id) {
        try {
            OrderParamDto order = this.orderService.findOrderById(id);
            if (order != null) {
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
                model.addAttribute("order", order);
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "没有此订单信息");
            }
        } catch (Exception e) {
            logger.error("根据ID查询订单出错", e);
            throw new TomsRuntimeException("根据ID查询订单出错");
        }
    }

    /**
     * 确认并执行下单
     *
     * @param model
     * @param id
     */
    @RequestMapping("confirm_make_order")
    public void confirMakeOrder(Model model, String id) {
        try {
            OrderParamDto order = this.orderService.findOrderById(id);
            if (order != null) {
                JsonModel jsonModel = this.orderService.confirmOrder(order);
                model.addAttribute(Constants.STATUS, jsonModel.isSuccess());
                model.addAttribute(Constants.MESSAGE, jsonModel.getMessage());
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "没有找到该订单信息，请检查参数");
            }
        } catch (Exception e) {
            logger.error("确认并执行下单失败" + e);
            throw new TomsRuntimeException("确认并执行下单失败" + e);
        }
    }

    /**
     * 直接拒绝订单
     *
     * @param model
     * @param id
     */
    @RequestMapping("refues_order")
    public void refueseOrder(Model model, String id) throws ApiException {
        OrderParamDto order = this.orderService.findOrderById(id);
        if (null != order) {
            JsonModel jsonModel = this.orderService.refuesOrder(order);
            model.addAttribute(Constants.STATUS, jsonModel.isSuccess());
            model.addAttribute(Constants.MESSAGE, jsonModel.getMessage());
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "没有找到该订单信息，请检查参数");
        }
    }

    /**
     * 确认但不执行下单
     *
     * @param model
     * @param id
     */
    @RequestMapping("confirm_no_order")
    public void confirmNoOrder(Model model, String id) throws ApiException {
        OrderParamDto order = this.orderService.findOrderById(id);
        if (null != order) {
            JsonModel jsonModel = this.orderService.confirmNoOrder(order);
            model.addAttribute(Constants.STATUS, jsonModel.isSuccess());
            model.addAttribute(Constants.MESSAGE, jsonModel.getMessage());
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "没有找到该订单信息，请检查参数");
        }
    }

    /**
     * 手动下单
     *
     * @param model
     * @param order
     */
    @RequestMapping("hand_make_order")
    public void handMakeOrder(Model model, Order order, String liveTimeString, String leaveTimeString) throws Exception {
        UserInfo userInfo = getCurrentUser();
        //检查参数
        Boolean param = OrderMethodHelper.checkHandMakeOrder(order, liveTimeString, leaveTimeString);
        order.setCompanyId(getCurrentUser().getCompanyId());
        if (param) {
            order.setLiveTime(DateUtil.parseDate(liveTimeString));
            order.setLeaveTime(DateUtil.parseDate(leaveTimeString));
            order.setId(order.getUuid());
            Map<String, Object> result = this.orderService.dealHandMakeOrder(order, userInfo);
            model.addAttribute("status", result.get("status"));
            model.addAttribute("message", result.get("message"));
        } else {
            model.addAttribute("status", false);
            model.addAttribute("message", "参数错误");
        }

    }

    /**
     * 手动下单时可选房型
     *
     * @param model
     * @param order
     * @param liveTimeString
     * @param leaveTimeString
     */
    @RequestMapping("find_room_type")
    public void findRoomType(Model model, Order order, String liveTimeString, String leaveTimeString) {
        UserInfo userInfo = getCurrentUser();
        if (StringUtils.isNotEmpty(liveTimeString)) {
            order.setLiveTime(DateUtil.parseDate(liveTimeString));
        }
        if (StringUtils.isNotEmpty(leaveTimeString)) {
            order.setLeaveTime(DateUtil.addDay(DateUtil.parseDate(leaveTimeString), -1));
        }
        try {
            List<RoomTypeInfoDto> roomTypeInfoDtos = this.orderService.findHandOrderRoomType(order, userInfo);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, roomTypeInfoDtos);
        } catch (Exception e) {
            logger.error("查询手动可选房型信息出错" + e.getMessage());
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "查询手动可选房型信息出错");
            throw new TomsRuntimeException("查询手动可选房型信息出错" + e.getMessage());
        }

    }

    /**
     * 获取房型最大库存量
     *
     * @param model
     * @param order
     * @param liveTimeString
     * @param leaveTimeString
     */
    @RequestMapping("find_room_num")
    public void findRoomNum(Model model, Order order, String liveTimeString, String leaveTimeString) {
        UserInfo userInfo = getCurrentUser();
        if (StringUtils.isNotEmpty(liveTimeString)) {
            order.setLiveTime(DateUtil.parseDate(liveTimeString));
        }
        if (StringUtils.isNotEmpty(leaveTimeString)) {
            order.setLeaveTime(DateUtil.addDay(DateUtil.parseDate(leaveTimeString), -1));
        }
        try {
            List<RoomTypeInfoDto> roomTypeInfoDtos = this.orderService.findHandOrderRoomType(order, userInfo);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, OrderMethodHelper.getMaxRoomNum(roomTypeInfoDtos, order));
        } catch (Exception e) {
            logger.error("获取房型最大库存量失败" + e.getMessage());
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "获取房型最大库存量失败");
            throw new TomsRuntimeException("获取房型最大库存量失败" + e.getMessage());
        }
    }
}
