package com.fanqielaile.toms.controller;

import com.fanqielaile.toms.service.ICtripHomeStayConnService;
import com.fanqielaile.toms.vo.ctrip.homestay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Create by jame
 * Date: 2016/9/6 9:54
 * Version: 1.0
 * Description: 携程民宿对接api接口
 * 1	FetchRoom	房源获取
 * 2	GetRoomStatus	房态获取
 * 3	FetchReview	点评获取
 * 4	BookingCheck	可预定检查
 * 5	SubmitOrder	提交订单
 * 6	GetOrders	获取订单信息
 * 7	CancelOrder	取消订单
 */
@Controller
@RequestMapping("/homestay/")
public class CtripHomeStayConnController {

    @Autowired
    private ICtripHomeStayConnService ctripHomeStayConnService;

    @RequestMapping("SubmitOrder")
    @ResponseBody
    public SubmitOrderReturnVo submitOrder(@RequestBody SubmitOrderRequestVo submitOrderParamVo) {
        return ctripHomeStayConnService.submitOrder(submitOrderParamVo);
    }

    @RequestMapping("GetOrders")
    @ResponseBody
    public GetOrderReturnVo getOrders(@RequestBody Map map){
        return ctripHomeStayConnService.getOrder(map);
    }


    @RequestMapping("CancelOrder")
    @ResponseBody
    public CancelOrderReturnVo cancelOrder(@RequestBody CancelOrderRequestVo cancelOrderRequestVo){
        return ctripHomeStayConnService.cancelOrder(cancelOrderRequestVo);
    }
}
