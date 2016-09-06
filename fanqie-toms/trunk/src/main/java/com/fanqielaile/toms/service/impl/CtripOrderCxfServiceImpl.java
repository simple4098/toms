package com.fanqielaile.toms.service.impl;

import com.fanqie.bean.enums.CtripRequestType;
import com.fanqie.bean.order.*;
import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.dto.orderLog.OrderLogData;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.OrderLogDec;
import com.fanqielaile.toms.service.ICtripOrderCxfService;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.MessageCenterUtils;
import com.fanqielaile.toms.support.util.XmlCtripUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.JAXBException;


/**
 * Created by wangdayin on 2016/1/4.
 */
@WebService(endpointInterface = "com.fanqielaile.toms.service.ICtripOrderCxfService", targetNamespace = "urn:CtripOrderCxfServiceImplwsdl")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED)
public class CtripOrderCxfServiceImpl implements ICtripOrderCxfService {
    private static Logger logger = LoggerFactory.getLogger(CtripOrderCxfServiceImpl.class);
    @Resource
    private CtripOrderService ctripOrderService;

    @Override
    public String Invoke(@WebParam(name = "xml") String xml, @WebParam(name = "Invoketype") String Invoketype) throws Exception {
        CtripBaseResponse ctripBaseResponse = new CtripBaseResponse();
        try {
            if (StringUtils.isNotEmpty(xml)) {
                //1.判断接口调用者的用户信息是否正确
                boolean ctripUserPassword = this.ctripOrderService.checkCtripUserPassword(xml);
                if (ctripUserPassword) {
                    //2.解析xml得到请求类型
                    String requestType = XmlCtripUtil.getRequestType(xml);
                    MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.REQUEST_PARAM, new OrderLogData(ChannelSource.XC, xml, "携程请求参数"));
                    if (CtripRequestType.DomesticCheckRoomAvail.name().equals(requestType)) {
                        //3.试订单请求
                        CtripCheckRoomAvailResponse ctripCheckRoomAvailResponse = this.ctripOrderService.dealCtripCheckRoomAvail(xml);
                        logger.info("返回值携程下单流程值=>" + FcUtil.fcRequest(ctripCheckRoomAvailResponse));
                        MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.RESPONSE_RETURN, new OrderLogData(ChannelSource.XC, FcUtil.fcRequest(ctripCheckRoomAvailResponse), "携程试订单请求返回值"));
                        return FcUtil.fcRequest(ctripCheckRoomAvailResponse);
                    } else if (CtripRequestType.DomesticSubmitNewHotelOrder.name().equals(requestType)) {
                        //4.下单请求
                        CtripNewHotelOrderResponse ctripNewHotelOrderResponse = this.ctripOrderService.addOrder(xml);
                        logger.info("返回值携程下单流程值=>" + FcUtil.fcRequest(ctripNewHotelOrderResponse));
                        MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.RESPONSE_RETURN, new OrderLogData(ChannelSource.XC, FcUtil.fcRequest(ctripNewHotelOrderResponse), "携程创建订单请求返回值"));
                        return FcUtil.fcRequest(ctripNewHotelOrderResponse);

                    } else if (CtripRequestType.DomesticCancelHotelOrder.name().equals(requestType)) {
                        //5.取消订单
                        CtripCancelHotelOrderResponse ctripCancelHotelOrderResponse = this.ctripOrderService.cancelOrderMethod(xml);
                        logger.info("返回值携程下单流程值=>" + FcUtil.fcRequest(ctripCancelHotelOrderResponse));
                        MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.RESPONSE_RETURN, new OrderLogData(ChannelSource.XC, FcUtil.fcRequest(ctripCancelHotelOrderResponse), "携程取消订单请求返回值"));

                        return FcUtil.fcRequest(ctripCancelHotelOrderResponse);
                    } else if (CtripRequestType.DomesticGetOrderStatus.name().equals(requestType)) {
                        //6.获取订单状态
                        CtripGetOrderStatusResponse orderStatus = this.ctripOrderService.getOrderStatus(xml);
                        logger.info("返回值携程下单流程值=>" + FcUtil.fcRequest(orderStatus));
                        MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.RESPONSE_RETURN, new OrderLogData(ChannelSource.XC, FcUtil.fcRequest(orderStatus), "携程获取订单状态请求返回值"));
                        return FcUtil.fcRequest(orderStatus);

                    } else {
                        logger.info("返回值携程下单流程值=>" + FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("119", "请求类型requestType不存在")));
                        MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.RESPONSE_RETURN, new OrderLogData(ChannelSource.XC, FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("119", "请求类型requestType不存在")), "携程订单请求返回值"));
                        return FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("119", "请求类型requestType不存在"));
                    }
                } else {
                    logger.info("返回值携程下单流程值=>" + FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("118", "用户名或密码验证出错!")));
                    MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.RESPONSE_RETURN, new OrderLogData(ChannelSource.XC, FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("118", "用户名或密码验证出错!")), "携程订单请求返回值"));
                    return FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("118", "用户名或密码验证出错!"));
                }
            } else {
                logger.info("返回值携程下单流程值=>" + FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("119", "xml参数为空!")));
                MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.RESPONSE_RETURN, new OrderLogData(ChannelSource.XC, FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("119", "xml参数为空!")), "携程订单请求返回值"));
                return FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("119", "xml参数为空!"));
            }
        } catch (Exception e) {
            logger.info("处理携程下单流程出错" + e, e);
            MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.RESPONSE_RETURN, new OrderLogData(ChannelSource.XC, FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("108", "处理携程对接异常")), "携程订单请求返回值"));
            return FcUtil.fcRequest(ctripBaseResponse.getCtripBaseResponse("108", "处理携程对接异常"));
        }
    }
}
