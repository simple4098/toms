package com.fanqielaile.toms.controller;

import com.fanqie.bean.enums.CtripRequestType;
import com.fanqie.bean.order.*;
import com.fanqie.jw.enums.OrderRequestType;
import com.fanqie.jw.response.order.JointWisdomAvailCheckOrderErrorResponse;
import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.dto.fc.CancelHotelOrderResponse;
import com.fanqielaile.toms.dto.fc.CheckRoomAvailResponse;
import com.fanqielaile.toms.dto.fc.CreateHotelOrderResponse;
import com.fanqielaile.toms.dto.fc.GetOrderStatusResponse;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.OrderMethod;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.model.fc.FCcheckRoomAvailResponseResult;
import com.fanqielaile.toms.model.fc.FcCancelHotelOrderResponseResult;
import com.fanqielaile.toms.model.fc.FcCreateHotelOrderResponseResult;
import com.fanqielaile.toms.model.fc.FcGetOrderStatusResponseResult;
import com.fanqielaile.toms.service.ICtripOrderService;
import com.fanqielaile.toms.service.IJointWisdomOrderService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.util.Map;

/**
 * Created by wangdayin on 2015/6/19.
 * OTA对接接口
 */
@Controller
@RequestMapping("")
public class OTAManageController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(OTAManageController.class);
    @Resource
    private IOrderService orderService;
    @Resource
    private ICtripOrderService ctripOrderService;
    @Resource
    private IJointWisdomOrderService jointWisdomOrderService;
    /*@Resource
    private BusinLogClient businLogClient;
    private BusinLog businLog = new BusinLog();*/

    /**
     * 淘宝调用的接口
     *
     * @return
     */
    @RequestMapping("taobaoService")
    @ResponseBody
    public Object TBService(HttpServletRequest request) throws Exception {
        String xmlStr = HttpClientUtil.convertStreamToString(request.getInputStream());
        Result result = new Result();
        if (StringUtils.isNotEmpty(xmlStr)) {
//            businLog.setDescr("淘宝接口传入XML参数：" + xmlStr);
//            businLogClient.save(businLog);
            //接口调用验证用户
            UserInfo userNameAndPassword = OrderMethodHelper.getUserNameAndPassword(xmlStr);
            if (null != userNameAndPassword) {
                //验证用户密码
                if (Constants.TBUserName.equals(userNameAndPassword.getUserName()) && Constants.TBPassword.equals(userNameAndPassword.getPassword())) {
                    //得到跟节点
                    logger.info("xml参数：" + xmlStr);
                    String rootElementString = XmlDeal.getRootElementString(xmlStr);
                    //根据根节点判断执行的方法
                    if (rootElementString.equals(OrderMethod.BookRQ.name())) {
                        //创建订单
                        Order order = orderService.addOrder(xmlStr, ChannelSource.TAOBAO);
                        result.setResultCode("0");
                        result.setMessage(order.getId());
                    } else if (rootElementString.equals(OrderMethod.CancelRQ.name())) {
                        //取消订单
                        JsonModel jsonModel = orderService.cancelOrder(xmlStr, ChannelSource.TAOBAO);
                        if (jsonModel.isSuccess()) {
                            result.setResultCode("0");
                            result.setMessage("取消订单成功");
                        } else {
                            result.setResultCode("-209");
                            result.setMessage(jsonModel.getMessage());
                        }
                    } else if (rootElementString.equals(OrderMethod.PaySuccessRQ.name())) {
                        //付款成功回调
                        //1.付款成功回调执行一次拦截
                        JsonModel jsonModel = orderService.paymentSuccessCallBack(xmlStr, ChannelSource.TAOBAO);
                        if (jsonModel.isSuccess()) {
                            result.setResultCode("0");
                            result.setMessage("付款成功");
                        } else {
                            result.setResultCode("-400");
                            result.setMessage(jsonModel.getMessage());
                        }
                        //查询订单状态
                    } else if (rootElementString.equals(OrderMethod.QueryStatusRQ.name())) {
                        Map<String, String> orderStatus = orderService.findOrderStatus(xmlStr, ChannelSource.TAOBAO);
                        result.setMessage(orderStatus.get("message"));
                        result.setResultCode(orderStatus.get("code"));
                        if (StringUtils.isNotEmpty(orderStatus.get("status"))) {
                            result.setStatus(orderStatus.get("status"));
                        }
                    } else if (rootElementString.equals(OrderMethod.OrderRefundRQ.name())) {
                        Map<String, String> map = orderService.dealPayBackMethod(xmlStr, ChannelSource.TAOBAO);
                        result.setMessage(map.get("message"));
                        result.setResultCode(map.get("status"));
                        logger.info("付款成功回调返回值:" + result.toString());
                    } else {
                        logger.error("xml参数错误");
                    }
                } else {
                    logger.error("创建订单失败,验证用户不通过", userNameAndPassword);
                    result.setMessage("创建订单失败,验证用户不通过");
                    result.setResultCode("-400");
                }
            } else {
                logger.error("创建订单失败,用户不存在", userNameAndPassword);
                result.setMessage("创建订单失败,用户不存在");
                result.setResultCode("-400");
            }
        } else {
            logger.error("创建订单失败，原因：参数不正确", xmlStr);
            result.setMessage("创建订单失败，原因：参数不正确");
            result.setResultCode("-400");
        }
        logger.info("返回淘宝的xml值=>" + result.toString());
        return result;
    }

    /**
     * 淘宝调用测试的接口
     *
     * @return
     */
    @RequestMapping("taobaoService_test")
    @ResponseBody
    public Object TBTestService(String xml) throws Exception {
        String xmlStr = xml;
        Result result = new Result();
        if (StringUtils.isNotEmpty(xmlStr)) {
//            businLog.setDescr("淘宝接口传入XML参数：" + xmlStr);
//            businLogClient.save(businLog);
            //接口调用验证用户
            UserInfo userNameAndPassword = OrderMethodHelper.getUserNameAndPassword(xmlStr);
            if (null != userNameAndPassword) {
                //验证用户密码
                if (Constants.TBUserName.equals(userNameAndPassword.getUserName()) && Constants.TBPassword.equals(userNameAndPassword.getPassword())) {
                    //得到跟节点
                    logger.info("xml参数：" + xmlStr);
                    String rootElementString = XmlDeal.getRootElementString(xmlStr);
                    //根据根节点判断执行的方法
                    if (rootElementString.equals(OrderMethod.BookRQ.name())) {
                        //创建订单
                        Order order = orderService.addOrder(xmlStr, ChannelSource.TAOBAO);
                        result.setResultCode("0");
                        result.setMessage(order.getId());
                    } else if (rootElementString.equals(OrderMethod.CancelRQ.name())) {
                        //取消订单
                        JsonModel jsonModel = orderService.cancelOrder(xmlStr, ChannelSource.TAOBAO);
                        if (jsonModel.isSuccess()) {
                            result.setResultCode("0");
                            result.setMessage("取消订单成功");
                        } else {
                            result.setResultCode("-209");
                            result.setMessage(jsonModel.getMessage());
                        }
                    } else if (rootElementString.equals(OrderMethod.PaySuccessRQ.name())) {
                        //付款成功回调
                        //1.付款成功回调执行一次拦截
                        JsonModel jsonModel = orderService.paymentSuccessCallBack(xmlStr, ChannelSource.TAOBAO);
                        if (jsonModel.isSuccess()) {
                            result.setResultCode("0");
                            result.setMessage("付款成功");
                        } else {
                            result.setResultCode("-400");
                            result.setMessage(jsonModel.getMessage());
                        }
                        //查询订单状态
                    } else if (rootElementString.equals(OrderMethod.QueryStatusRQ.name())) {
                        Map<String, String> orderStatus = orderService.findOrderStatus(xmlStr, ChannelSource.TAOBAO);
                        result.setMessage(orderStatus.get("message"));
                        result.setResultCode(orderStatus.get("code"));
                        if (StringUtils.isNotEmpty(orderStatus.get("status"))) {
                            result.setStatus(orderStatus.get("status"));
                        }
                    } else if (rootElementString.equals(OrderMethod.OrderRefundRQ.name())) {
                        Map<String, String> map = orderService.dealPayBackMethod(xmlStr, ChannelSource.TAOBAO);
                        result.setMessage(map.get("message"));
                        result.setResultCode(map.get("status"));
                        logger.info("付款成功回调返回值:" + result.toString());
                    } else {
                        logger.error("xml参数错误");
                    }
                } else {
                    logger.error("创建订单失败,验证用户不通过", userNameAndPassword);
                    result.setMessage("创建订单失败,验证用户不通过");
                    result.setResultCode("-400");
                }
            } else {
                logger.error("创建订单失败,用户不存在", userNameAndPassword);
                result.setMessage("创建订单失败,用户不存在");
                result.setResultCode("-400");
            }
        } else {
            logger.error("创建订单失败，原因：参数不正确", xmlStr);
            result.setMessage("创建订单失败，原因：参数不正确");
            result.setResultCode("-400");
        }
        logger.info("返回淘宝的xml值=>" + result.toString());
        return result;
    }

    /**
     * 天下房仓试订单接口
     *
     * @param xml
     * @return
     */
    @RequestMapping("checkRoomAvail")
    @ResponseBody
    public Object checkRoomNum(String xml) throws Exception {
        FCcheckRoomAvailResponseResult result = new FCcheckRoomAvailResponseResult();
        if (StringUtils.isNotEmpty(xml)) {
            CheckRoomAvailResponse checkRoomAvailResponse = this.orderService.checkRoomAvail(xml);
            if (null != checkRoomAvailResponse) {
                result.setCheckRoomAvailResponse(checkRoomAvailResponse);
                result.setResultFlag("1");
                result.setResultMsg("success");
                return FcUtil.fcRequest(result);
            } else {
                result.setResultFlag("0");
                result.setResultMsg("查询出错!");
            }
        } else {
            result.setResultFlag("0");
            result.setResultMsg("xml参数错误");
        }
        logger.info("试订单接口返回值=>" + result.toString());
        return result;
    }

    /**
     * 天下房仓创建订单
     *
     * @param xml
     * @return
     */
    @RequestMapping("createHotelOrder")
    @ResponseBody
    public Object createhotelOrder(String xml) throws Exception {
        FcCreateHotelOrderResponseResult result = new FcCreateHotelOrderResponseResult();
        if (StringUtils.isNotEmpty(xml)) {
            Map<String, Object> map = this.orderService.createFcHotelOrder(xml);
            JsonModel jsonModel = (JsonModel) map.get("status");
            Order order = (Order) map.get("order");
            CreateHotelOrderResponse createHotelOrderResponse = new CreateHotelOrderResponse();
            if (jsonModel.isSuccess()) {
                createHotelOrderResponse.setFcOrderId(order.getChannelOrderCode());
                createHotelOrderResponse.setSpOrderId(order.getId());
                createHotelOrderResponse.setOrderStatus(1);
                result.setResultFlag("1");
                result.setResultMsg("创建订单成功");
            } else {
                createHotelOrderResponse.setFcOrderId(order.getChannelOrderCode());
                createHotelOrderResponse.setSpOrderId(order.getId());
                createHotelOrderResponse.setOrderStatus(2);
                result.setResultFlag("1");
                result.setResultMsg(jsonModel.getMessage());
            }
            if (null != createHotelOrderResponse) {
                result.setCreateHotelOrderResponse(createHotelOrderResponse);
            }
        } else {
            result.setResultFlag("0");
            result.setResultMsg("xml参数错误");
        }
        logger.info("创建订单接口返回值=>" + result.toString());
        return result;
    }

    /**
     * 天下房仓取消订单
     *
     * @param xml
     * @return
     */
    @RequestMapping("cancelHotelOrder")
    @ResponseBody
    public Object cancelHotelOrder(String xml) throws Exception {
        FcCancelHotelOrderResponseResult result = new FcCancelHotelOrderResponseResult();
        if (StringUtils.isNotEmpty(xml)) {
            CancelHotelOrderResponse cancelHotelOrderResponse = this.orderService.cancelFcHotelOrder(xml);
            if (null != cancelHotelOrderResponse) {
                result.setResultFlag("1");
                result.setResultMsg("success");
                result.setCancelHotelOrderResponse(cancelHotelOrderResponse);
            } else {
                result.setResultFlag("0");
                result.setResultMsg("没有找到此单");
            }

        } else {
            result.setResultFlag("0");
            result.setResultMsg("xml参数错误");
        }
        logger.info("取消订单接口返回值=>" + result.toString());
        return result;
    }

    /**
     * 天下房仓获取订单状态
     *
     * @param xml
     * @return
     */
    @RequestMapping("getOrderStatus")
    @ResponseBody
    public Object getFcOrderStatus(String xml) throws Exception {
        FcGetOrderStatusResponseResult result = new FcGetOrderStatusResponseResult();
        if (StringUtils.isNotEmpty(xml)) {
            GetOrderStatusResponse fcOrderStatus = this.orderService.getFcOrderStatus(xml);
            if (null != fcOrderStatus) {
                result.setResultMsg("success");
                result.setResultFlag("1");
                result.setGetOrderStatusResponse(fcOrderStatus);
            } else {
                result.setResultFlag("0");
                result.setResultMsg("查询错误");
            }
        } else {
            result.setResultFlag("0");
            result.setResultMsg("xml参数错误");
        }
        logger.info("查询订单状态接口返回值=>" + result.toString());
        return result;
    }

    /**
     * 携程订单对接处理方法
     *
     * @return
     */
    @RequestMapping("ctripOrder")
    @ResponseBody
    public Object ctripOrder(String xml) {
        CtripBaseResponse ctripBaseResponse = new CtripBaseResponse();
        try {
            if (StringUtils.isNotEmpty(xml)) {
                //1.判断接口调用者的用户信息是否正确
                boolean ctripUserPassword = this.ctripOrderService.checkCtripUserPassword(xml);
                if (ctripUserPassword) {
                    //2.解析xml得到请求类型
                    String requestType = XmlCtripUtil.getRequestType(xml);
                    if (CtripRequestType.DomesticCheckRoomAvail.name().equals(requestType)) {
                        //3.试订单请求
                        CtripCheckRoomAvailResponse ctripCheckRoomAvailResponse = this.ctripOrderService.dealCtripCheckRoomAvail(xml);
                        return ctripCheckRoomAvailResponse;
                    } else if (CtripRequestType.DomesticSubmitNewHotelOrder.name().equals(requestType)) {
                        //4.下单请求
                        CtripNewHotelOrderResponse ctripNewHotelOrderResponse = this.ctripOrderService.addOrder(xml);
                        return ctripNewHotelOrderResponse;
                    } else if (CtripRequestType.DomesticCancelHotelOrder.name().equals(requestType)) {
                        //5.取消订单
                        CtripCancelHotelOrderResponse ctripCancelHotelOrderResponse = this.ctripOrderService.cancelOrderMethod(xml);
                        return ctripCancelHotelOrderResponse;
                    } else if (CtripRequestType.DomesticGetOrderStatus.name().equals(requestType)) {
                        //6.获取订单状态
                        CtripGetOrderStatusResponse orderStatus = this.ctripOrderService.getOrderStatus(xml);
                        return orderStatus;

                    } else {
                        return ctripBaseResponse.getCtripBaseResponse("119", "请求类型requestType不存在");
                    }
                } else {
                    return ctripBaseResponse.getCtripBaseResponse("118", "用户名或密码验证出错!");
                }
            } else {
                return ctripBaseResponse.getCtripBaseResponse("119", "xml参数为空!");
            }
        } catch (Exception e) {
            logger.info("处理携程下单流程出错" + e, e);
            return ctripBaseResponse.getCtripBaseResponse("108", "处理携程对接异常");
        }
    }

    /**
     * 众荟试订单处理
     *
     * @return
     */
    @RequestMapping(value = "/jointWisdomAvailCheckOrder")
    @ResponseBody
    public Object jointWisdomOrder(String xml) throws JAXBException {
        logger.info("众荟对接传入xml=" + xml);
        try {
            if (StringUtils.isNotEmpty(xml)) {
                //解析xml获取请求
                Map<String, Object> map = jointWisdomOrderService.dealAvailCheckOrder(xml);
                return map.get("data");
            } else {
                logger.info("众荟传入xml为空");
                return new JointWisdomAvailCheckOrderErrorResponse().getBasicError("500", "error", "xml is null", "param xml is null");
            }
        } catch (Exception e) {
            logger.info("处理众荟下单流程异常" + e);
            return new JointWisdomAvailCheckOrderErrorResponse().getBasicError("500", "error", "joint winsdom exception", "exception" + e);
        }
    }

    @RequestMapping(value = "/jointWisdowOrder")
    @ResponseBody
    public Object jointWisdowMakeOrder(String xml) throws JAXBException {
        logger.info("众荟对接传入xml=" + xml);
        try {
            if (StringUtils.isNotEmpty(xml)) {
                //解析xml获取请求类型
                OrderRequestType orderRequestType = XmlJointWisdomUtil.getOrderRequestType(xml);
                //下单
                if (OrderRequestType.Commit.equals(orderRequestType)) {
                    Map<String, Object> map = this.jointWisdomOrderService.dealAddOrder(xml);
                    return map.get("data");
                } else if (OrderRequestType.Cancel.equals(orderRequestType)) {
                    //取消订单
                    Map<String, Object> map = this.jointWisdomOrderService.dealCancelOrder(xml);
                    return map.get("data");
                } else {
                    return new JointWisdomAvailCheckOrderErrorResponse().getBasicError("500", "error", "order request type is not exit", "order request type is not exit");
                }
            } else {
                logger.info("众荟传入xml为空");
                return new JointWisdomAvailCheckOrderErrorResponse().getBasicError("500", "error", "xml is null", "param xml is null ");
            }
        } catch (Exception e) {
            logger.info("处理众荟下单流程异常" + e);
            return new JointWisdomAvailCheckOrderErrorResponse().getBasicError("500", "error", "joint winsdom exception", "joint winsdom exception" + e);
        }
    }

}
