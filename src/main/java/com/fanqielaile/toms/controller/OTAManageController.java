package com.fanqielaile.toms.controller;

import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.dto.fc.CancelHotelOrderResponse;
import com.fanqielaile.toms.dto.fc.CheckRoomAvailResponse;
import com.fanqielaile.toms.dto.fc.CreateHotelOrderResponse;
import com.fanqielaile.toms.dto.fc.GetOrderStatusResponse;
import com.fanqielaile.toms.enums.BreakfastType;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.CurrencyType;
import com.fanqielaile.toms.enums.OrderMethod;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.model.fc.SaleItem;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.JsonModel;
import com.fanqielaile.toms.support.util.XmlDeal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
     * 天下房仓试订单接口
     *
     * @param xml
     * @return
     */
    @RequestMapping("checkRoomAvail")
    @ResponseBody
    public Object checkRoomNum(String xml) throws Exception {
        CheckRoomAvailResponse result = new CheckRoomAvailResponse();
        if (StringUtils.isNotEmpty(xml)) {
            CheckRoomAvailResponse checkRoomAvailResponse = this.orderService.checkRoomAvail(xml);
            checkRoomAvailResponse.setResultFlag("1");
            checkRoomAvailResponse.setResultMsg("success");
            List<SaleItem> saleItemList = new ArrayList<>();
            SaleItem saleItem = new SaleItem();
            //无早
            saleItem.setBreakfastType(BreakfastType.ZERO);
            //配额数量
            saleItem.setAvailableQuotaNum(0);
            //早餐数量
            saleItem.setBreakfastNum(0);
            //货币类型
            saleItem.setCurrencyType(CurrencyType.CNY);
            saleItem.setDayCanBook(1);
            saleItem.setRoomStatus(1);
            //是否可超，0否，1是
            saleItem.setOverDraft(0);
            //价格是否待查，0否，1是
            saleItem.setPriceNeedCheck(0);
            saleItem.setSaleDate(new Date());
            saleItem.setSalePrice(BigDecimal.valueOf(11));
            saleItemList.add(saleItem);
            checkRoomAvailResponse.setSaleItems(saleItemList);
            return FcUtil.fcRequest(checkRoomAvailResponse);
        } else {
            result.setResultFlag("0");
            result.setResultMsg("xml参数错误");
        }
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
        CreateHotelOrderResponse result = new CreateHotelOrderResponse();
        if (StringUtils.isNotEmpty(xml)) {
            Map<String, Object> map = this.orderService.createFcHotelOrder(xml);
            JsonModel jsonModel = (JsonModel) map.get("status");
            Order order = (Order) map.get("order");
            if (jsonModel.isSuccess()) {
                result.setFcOrderId(order.getChannelOrderCode());
                result.setSpOrderId(order.getId());
                result.setResultFlag("1");
                result.setResultMsg("创建订单成功");
            } else {
                result.setResultFlag("0");
                result.setResultMsg(jsonModel.getMessage());
            }

        } else {
            result.setResultFlag("0");
            result.setResultMsg("xml参数错误");
        }
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
        CancelHotelOrderResponse result = new CancelHotelOrderResponse();
        if (StringUtils.isNotEmpty(xml)) {
            result = this.orderService.cancelFcHotelOrder(xml);
        } else {
            result.setResultFlag("0");
            result.setResultMsg("xml参数错误");
        }
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
        GetOrderStatusResponse result = new GetOrderStatusResponse();
        if (StringUtils.isNotEmpty(xml)) {
            result = this.orderService.getFcOrderStatus(xml);
        } else {
            result.setResultFlag("1");
            result.setResultMsg("xml参数错误");
        }
        return result;
    }


}
