package com.fanqielaile.toms.service.impl;

import com.fanqie.jw.enums.OrderRequestType;
import com.fanqie.jw.response.order.JointWisdomAvailCheckOrderErrorResponse;
import com.fanqielaile.toms.service.IJointWisdomCxfService;
import com.fanqielaile.toms.service.IJointWisdomOrderService;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlJointWisdomUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by wangdayin on 2016/1/13.
 */
@WebService(endpointInterface = "com.fanqielaile.toms.service.IJointWisdomCxfService", targetNamespace = "urn:IJointWisdomCxfServicewsdl")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED)
public class JoinWisdomCxfServiceImpl implements IJointWisdomCxfService {
    private static Logger logger = LoggerFactory.getLogger(JoinWisdomCxfServiceImpl.class);
    @Resource
    private IJointWisdomOrderService jointWisdomOrderService;

    @Override
    public String CheckAvailability(@WebParam(name = "xml") String xml) throws Exception {
        if (StringUtils.isNotEmpty(xml)) {
            //解析xml获取请求
            String checkOrder = jointWisdomOrderService.dealAvailCheckOrder(xml);
            logger.info("众荟试订单返回值：" + checkOrder);
            return checkOrder;
        } else {
            logger.info("众荟传入xml为空");
            return FcUtil.fcRequest(new JointWisdomAvailCheckOrderErrorResponse().getBasicError("500", "error", "传入xml参数为空", "传入xml参数为空"));
        }
    }

    @Override
    public String ProcessReservationRequest(@WebParam(name = "xml") String xml) throws Exception {
        if (StringUtils.isNotEmpty(xml)) {
            //解析xml获取请求类型
            OrderRequestType orderRequestType = XmlJointWisdomUtil.getOrderRequestType(xml);
            //下单
            if (OrderRequestType.Commit.equals(orderRequestType)) {
                String result = this.jointWisdomOrderService.dealAddOrder(xml);
                logger.info("众荟下单返回值：" + result);
                return result;
            } else if (OrderRequestType.Cancel.equals(orderRequestType)) {
                //取消订单
                String cancelResult = this.jointWisdomOrderService.dealCancelOrder(xml);
                logger.info("众荟取消订单返回值：" + cancelResult);
                return cancelResult;
            } else {
                return FcUtil.fcRequest(new JointWisdomAvailCheckOrderErrorResponse().getBasicError("500", "error", "订单流程中请求类型不存在", "订单流程中请求类型不存在"));
            }
        } else {
            logger.info("众荟传入xml为空");
            return FcUtil.fcRequest(new JointWisdomAvailCheckOrderErrorResponse().getBasicError("500", "error", "传入xml参数为空", "传入xml参数为空"));
        }
    }
}
