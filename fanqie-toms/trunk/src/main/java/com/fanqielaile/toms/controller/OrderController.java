package com.fanqielaile.toms.controller;

import com.fanqie.util.DateUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.Pagination;
import com.fanqielaile.toms.dto.HotelOrderPay;
import com.fanqielaile.toms.dto.HotelOrderStatus;
import com.fanqielaile.toms.dto.OrderParamDto;
import com.fanqielaile.toms.dto.OrderStatisticsDto;
import com.fanqielaile.toms.dto.PmsCancelOrderParam;
import com.fanqielaile.toms.dto.RoomTypeInfoDto;
import com.fanqielaile.toms.enums.OrderSource;
import com.fanqielaile.toms.enums.OrderStatus;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.helper.PaginationHelper;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderOtherPrice;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.decorator.FrontendPagerDecorator;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.thread.LocalThread;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.JsonModel;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.util.HashMap;
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
    @Resource
    private IBangInnService bangInnService;
    

    /**
     * 订单导出功能
     */
    @RequestMapping("order_export")
    @ResponseBody
    public void orderExport(OrderParamDto orderParamDto,@RequestParam(defaultValue = "",required = false) String operatorsJson,@RequestParam(defaultValue = "",required = false) String selectedOperators,@RequestParam(defaultValue = "",required = false) String selectStatusString, HttpServletResponse response) {
        try {
            this.orderService.dealOrderExport(getCurrentUser(), orderParamDto, response, operatorsJson, selectedOperators,selectStatusString);
        } catch (Exception e) {
        	e.printStackTrace();
            logger.info("导出订单列表出错" + e);
        }
    }

    /**
     * 订单列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping("find_orders")
    public String findOrder(Model model, @RequestParam(defaultValue = "1", required = false) int page,@RequestParam(defaultValue = "",required = false) String operatorsJson,@RequestParam(defaultValue = "",required = false) String selectStatusString,@RequestParam(defaultValue = "",required = false) String selectedOperators, OrderParamDto orderParamDto) {
        try {
            UserInfo currentUser = getCurrentUser();
            logger.info("++++++++++++++++++++++++++++++传入参数：operatorsJson："+operatorsJson+"+++++selectStatusString："+selectStatusString);
            //初始化查询已处理订单属性
        	orderService.initFindOrderParam(orderParamDto,currentUser,operatorsJson,selectedOperators,selectStatusString);
            List<OrderParamDto> orderParamDtos = this.orderService.findOrderByPage(currentUser.getCompanyId(), new PageBounds(page, defaultRows), orderParamDto);
            //对订单相关数据进行统计
            OrderStatisticsDto orderStatisticsDto = orderService.statisticsOrder(currentUser.getCompanyId(), orderParamDto);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, orderParamDtos);
            model.addAttribute("count", orderStatisticsDto);
            //封装分页信息
            Paginator paginator = ((PageList) orderParamDtos).getPaginator();
            Pagination pagination = PaginationHelper.toPagination(paginator);
            FrontendPagerDecorator pageDecorator = new FrontendPagerDecorator(pagination);
            model.addAttribute("pagination", pagination);
            model.addAttribute("pageDecorator", pageDecorator);
            //分转查询条件
            model.addAttribute("order", orderParamDto.getOrderByDealTime(orderParamDto));
            
            /*OrderParamDto paramDto = this.orderService.findOrders(currentUser.getCompanyId(), orderParamDto);
            model.addAttribute("orderPrice", paramDto);*/
            //渠道来源
            List<Order> orders = this.orderService.findOrderChancelSource(currentUser.getCompanyId());
            model.addAttribute("orderSource", orders);
            //操作人相关
            orderService.searchOperatorsInfo(orderParamDto);
            model.addAttribute("operators", orderParamDto.getOperators());
            model.addAttribute("operatorsJson", operatorsJson);
            model.addAttribute("selectedOperators", selectedOperators);
            //订单状态相关
            model.addAttribute("selectStatusString",orderService.handleOrderStatusString(selectStatusString) );
            //酒店相关
			List<BangInn> inns = bangInnService.findBangInnByCompanyId(orderParamDto.getCompanyId());
			model.addAttribute("inns", inns);
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
            /*model.addAttribute("order", orderParamDto);
           /* model.addAttribute("order", orderParamDto);
            OrderParamDto paramDto = this.orderService.findOrders(currentUser.getCompanyId(), orderParamDto);
            model.addAttribute("orderPrice", paramDto);*/
        } catch (Exception e) {
            logger.error("查询未处理订单列表失败", e);
            throw new TomsRuntimeException("查询未处理订单列表失败");
        }
        return "/order/order_non_list";
    }

    /**
     * 查询退款申请订单列表
     *
     * @param model
     * @param page
     * @param orderParamDto
     * @return
     */
    @RequestMapping("find_pay_back_orders")
    public String findPayBackOrders(Model model, @RequestParam(defaultValue = "1", required = false) int page, OrderParamDto orderParamDto) {
        try {
            UserInfo currentUser = getCurrentUser();
            orderParamDto.setOrderStatus(OrderStatus.PAY_BACK);
            List<OrderParamDto> orderParamDtos = this.orderService.findOrderByPage(currentUser.getCompanyId(), new PageBounds(page, defaultRows), orderParamDto);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, orderParamDtos);
            //封装分页信息
            Paginator paginator = ((PageList) orderParamDtos).getPaginator();
            model.addAttribute("pagination", PaginationHelper.toPagination(paginator));
            //分转查询条件
            /*model.addAttribute("order", orderParamDto);
           /* model.addAttribute("order", orderParamDto);
            OrderParamDto paramDto = this.orderService.findOrders(currentUser.getCompanyId(), orderParamDto);
            model.addAttribute("orderPrice", paramDto);*/
        } catch (Exception e) {
            logger.error("查询退款订单列表失败", e);
            throw new TomsRuntimeException("查询退款订单列表失败");
        }
        return "/order/order_pay_back_list";
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
            //统计该订单的其他消费成本
            List<OrderOtherPrice> otherTotalCost = orderService.statisticsOrderOtherPrice(id);
            BigDecimal profit = orderService.countOrderProfit(order, otherTotalCost);
            if (order != null) {
            	model.addAttribute("profit", profit);
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
                model.addAttribute("order", order);
                model.addAttribute("otherTotalCost", otherTotalCost);
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
                JsonModel jsonModel = this.orderService.confirmOrder(order, getCurrentUser());
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
    public void refueseOrder(Model model, String id) throws Exception {
        OrderParamDto order = this.orderService.findOrderById(id);
        if (null != order) {
            JsonModel jsonModel = this.orderService.refuesOrder(order, getCurrentUser());
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
    public void confirmNoOrder(Model model, String id) throws Exception {
        OrderParamDto order = this.orderService.findOrderById(id);
        if (null != order) {
            JsonModel jsonModel = this.orderService.confirmNoOrder(order, getCurrentUser());
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
        order.setUserId(userInfo.getId());
        order.setOrderSource(OrderSource.HAND);
        if (param) {
            order.setLiveTime(DateUtil.parseDate(liveTimeString));
            order.setLeaveTime(DateUtil.parseDate(leaveTimeString));
            order.setId(order.getUuid());
			//单房型手动下单
//            Map<String, Object> result = this.orderService.dealHandMakeOrder(order, userInfo);
            //多方手动下单
            Map<String, Object> result = this.orderService.dealHandMakeOrderRoomTypes(order, userInfo);
            if((Boolean)result.get("status")){
            	//下单成功才异步更新订单归属地信息
            	LocalThread thread = new LocalThread(order);
            	thread.start();
            }
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
     * 取消手动下单
     *
     * @param orderId
     */
    @RequestMapping("cancel_hand_order")
    public void cancelHandOrder(String orderId, Model model) throws Exception {
        OrderParamDto orderParamDto = this.orderService.findOrderById(orderId);
        if (null != orderParamDto) {
            JsonModel jsonModel = this.orderService.cancelHandOrder(orderParamDto);
            model.addAttribute(Constants.STATUS, jsonModel.isSuccess());
            model.addAttribute(Constants.MESSAGE, jsonModel.getMessage());
        } else {
            model.addAttribute(Constants.STATUS, Constants.ERROR);
            model.addAttribute(Constants.MESSAGE, "查询订单出错，没有此订单");
        }
    }

    /**
     * 获取房型最小库存量
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

    /**
     * 同意退款
     *
     * @param model
     * @param id
     * @throws Exception
     */
    @RequestMapping("agree_pay_back")
    public void agreePayBack(Model model, String id) {
        try {
            OrderParamDto order = this.orderService.findOrderById(id);
            if (null != order) {
                JsonModel jsonModel = this.orderService.agreePayBackOrder(order, getCurrentUser());
                model.addAttribute(Constants.STATUS, jsonModel.isSuccess());
                model.addAttribute(Constants.MESSAGE, jsonModel.getMessage());
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "没有找到该订单信息，请检查参数");
            }
        } catch (Exception e) {
            logger.info("同意退款系统错误" + e);
        }
    }

    /**
     * 拒绝退款
     *
     * @param model
     * @param id
     */
    @RequestMapping("refuse_pay_back")
    public void refusePayBack(Model model, String id) {
        try {
            OrderParamDto order = this.orderService.findOrderById(id);
            if (null != order) {
                JsonModel jsonModel = this.orderService.refusePayBackOrder(order, getCurrentUser());
                model.addAttribute(Constants.STATUS, jsonModel.isSuccess());
                model.addAttribute(Constants.MESSAGE, jsonModel.getMessage());
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "没有找到该订单信息，请检查参数");
            }
        } catch (Exception e) {
            logger.info("拒绝退款系统错误" + e);
        }
    }

    /**
     * 下单到oms
     *
     * @param model
     * @param id
     */
    @RequestMapping("create_order_oms")
    public void createOrderOms(Model model, String id) {
        try {
            OrderParamDto orderParamDto = this.orderService.findOrderById(id);
            if (null != orderParamDto) {
                JsonModel orderOmsMethod = this.orderService.createOrderOmsMethod(orderParamDto, getCurrentUser());
                model.addAttribute(Constants.STATUS, orderOmsMethod.isSuccess());
                model.addAttribute(Constants.MESSAGE, orderOmsMethod.getMessage());
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "没有找到该订单信息，请检查参数");
            }
        } catch (Exception e) {
            logger.info("下单到oms异常");
        }
    }

    /**
     * 取消订单同步oms
     *
     * @param model
     * @param id
     */
    @RequestMapping("cancel_order_oms")
    public void cancelOrderOms(Model model, String id) {
        try {
            OrderParamDto orderParamDto = this.orderService.findOrderById(id);
            if (null != orderParamDto) {
                JsonModel jsonModel = this.orderService.cancelOrderOmsMethod(orderParamDto, getCurrentUser());
                model.addAttribute(Constants.STATUS, Constants.SUCCESS);
                model.addAttribute(Constants.MESSAGE, jsonModel.getMessage());
            } else {
                model.addAttribute(Constants.STATUS, Constants.ERROR);
                model.addAttribute(Constants.MESSAGE, "没有找到该订单信息，请检查参数");
            }
        } catch (Exception e) {
            logger.info("oms取消订单异常");
        }
    }

    /**
     * 酒店订单状态同步
     */
    @RequestMapping(value = "/hotel_order_status")
    @ResponseBody
    public Map<String, Object> hotelOrderStatusMethod(HotelOrderStatus hotelOrderStatus) {
        Map<String, Object> result = new HashMap<>();
        logger.info("同步订单状态oms传入参数" + JacksonUtil.obj2json(hotelOrderStatus));
        result.put("status", 200);
        result.put("message", "同步订单状态成功");
        try {
            JsonModel jsonModel = this.orderService.pushHotelOrderStatus(hotelOrderStatus);
            result.put("status", jsonModel.isSuccess() ? 200 : 500);
            result.put("message", jsonModel.getMessage());
        } catch (Exception e) {
            logger.info("淘宝信用住，系统处理异常" + JacksonUtil.obj2json(hotelOrderStatus));
            result.put("status", 500);
            result.put("message", "系统异常");
        }
        logger.info("淘宝信用住，同步订单状态返回值：" + result.toString());
        return result;
    }

    /**
     * 阿里信用住结账
     */
    @RequestMapping(value = "/hotel_order_pay")
    @ResponseBody
    public Map<String, Object> hotelOrderPayMethod(HotelOrderPay hotelOrderPay) {
        Map<String, Object> result = new HashMap<>();
        logger.info("淘宝信用住结账传入参数:" + JacksonUtil.obj2json(hotelOrderPay));
        try {
            JsonModel jsonModel = this.orderService.dealOrderPay(hotelOrderPay);
            result.put("status", jsonModel.isSuccess() ? 200 : 500);
            result.put("message", jsonModel.getMessage());
        } catch (Exception e) {
            logger.info("淘宝信用住，系统处理异常" + JacksonUtil.obj2json(hotelOrderPay));
            result.put("status", 500);
            result.put("message", "系统异常");
        }
        logger.info("淘宝信用住，结账返回值：" + result.toString());
        return result;
    }

    /*
     * pms取消淘宝信用住订单操作接口
     */
	@RequestMapping("cancel_order")
	public Map<String, Object> pmsCancelOrder(@Valid PmsCancelOrderParam pmsCancelOrderParam, BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<>();
		logger.info(
				"pms cancel the order operation parameters: omsOrderCode = " + pmsCancelOrderParam.getOmsOrderCode());
		if (bindingResult.hasErrors()) {
			result.put("status", Constants.ERROR400_NUMBER);
			result.put("message", bindingResult.getAllErrors().get(0).getDefaultMessage());
			return result;
		}
		try {
			JsonModel jsonModel = this.orderService.pmsCancelOrderOperate(pmsCancelOrderParam);
			result.put("status", jsonModel.isSuccess() ? Constants.SUCCESS_NUMBER : Constants.ERROR400_NUMBER);
			result.put("message", jsonModel.getMessage());
		} catch (Exception e) {
			logger.error("pms cancel the order operation, System anomaly. parameters: omsOrderCode = "
					+ pmsCancelOrderParam.getOmsOrderCode(), e);
			result.put("status", Constants.ERROR500_NUMBER);
			result.put("message", "系统异常");
		}
		logger.info("pms cancel the order operation result：" + result.toString());
		return result;
	}
	
	/**
     * 查询退款申请订单列表
     *
     * @param model
     * @param page
     * @param orderParamDto
     * @return
     */
    @RequestMapping("find_apply_cancel_orders")
    public String findApplyCancelOrders(Model model, @RequestParam(defaultValue = "1", required = false) int page, OrderParamDto orderParamDto) {
        try {
            UserInfo currentUser = getCurrentUser();
            orderParamDto.setOrderStatus(OrderStatus.CANCEL_APPLY);
            List<OrderParamDto> orderParamDtos = this.orderService.findOrderByPage(currentUser.getCompanyId(), new PageBounds(page, defaultRows), orderParamDto);
            model.addAttribute(Constants.STATUS, Constants.SUCCESS);
            model.addAttribute(Constants.DATA, orderParamDtos);
            //封装分页信息
            Paginator paginator = ((PageList) orderParamDtos).getPaginator();
            model.addAttribute("pagination", PaginationHelper.toPagination(paginator));
//            logger.info(JacksonUtil.obj2json(model.asMap().get("data")));
        } catch (Exception e) {
            logger.error("Query to apply for cancellation of the order list of failure", e);
            throw new TomsRuntimeException("查询申请取消订单列表失败");
        }
        return "/order/order_apply_cancel_list";
    }
    
    /*
     * 同意取消订单
     */
	@RequestMapping("agree_cancel_order")
	public Map<String, Object> agreeCancelOrder(PmsCancelOrderParam pmsCancelOrderParam) {
		Map<String, Object> result = new HashMap<>();
		logger.info("agree to cancel the order parameters: id = " + pmsCancelOrderParam.getId());
		if (pmsCancelOrderParam.getId() == null) {
			result.put("status", Constants.ERROR400_NUMBER);
			result.put("message", "订单id不能为空！");
			return result;
		}
		try {
			JsonModel jsonModel = this.orderService.agreeCancelOrderOperate(pmsCancelOrderParam);
			result.put("status", jsonModel.isSuccess() ? Constants.SUCCESS_NUMBER : Constants.ERROR400_NUMBER);
			result.put("message", jsonModel.getMessage());
		} catch (Exception e) {
			logger.error("agree to cancel the order, System anomaly. parameters: id = " + pmsCancelOrderParam.getId(),
					e);
			result.put("status", Constants.ERROR500_NUMBER);
			result.put("message", "更新toms订单状态失败！");
		}
		logger.info("agree to cancel the order result：" + result.toString());
		return result;
	}
	
	/*
	 * 拒绝取消订单
	 */
	@RequestMapping("refuse_cancel_order")
	public Map<String, Object> refuseCancelOrder(PmsCancelOrderParam pmsCancelOrderParam, BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<>();
		logger.info("refuse to cancel the order operation parameters: id = " + pmsCancelOrderParam.getId());
		if (bindingResult.hasErrors()) {
			result.put("status", Constants.ERROR400_NUMBER);
			result.put("message", bindingResult.getAllErrors().get(0).getDefaultMessage());
			return result;
		}
		try {
			JsonModel jsonModel = this.orderService.refuseCancelOrderOperate(pmsCancelOrderParam);
			result.put("status", jsonModel.isSuccess() ? Constants.SUCCESS_NUMBER : Constants.ERROR400_NUMBER);
			result.put("message", jsonModel.getMessage());
		} catch (Exception e) {
			logger.error("refuse to cancel the order operation, System anomaly. parameters: omsOrderCode = "
					+ pmsCancelOrderParam.getOmsOrderCode(), e);
			result.put("status", Constants.ERROR500_NUMBER);
			result.put("message", "拒绝取消订单操作执行异常！");
		}
		logger.info("refuse to cancel the order operation result：" + result.toString());
		return result;
	}
}
