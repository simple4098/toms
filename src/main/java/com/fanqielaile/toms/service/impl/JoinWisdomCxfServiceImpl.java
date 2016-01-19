package com.fanqielaile.toms.service.impl;

import com.fanqie.jw.request.availCheckOrder.OTAHotelAvailRQ;
import com.fanqie.jw.response.order.JointWisdomAvailCheckOrderErrorResponse;
import com.fanqielaile.toms.service.IJointWisdomCxfService;
import com.fanqielaile.toms.service.IJointWisdomOrderService;
import com.fanqielaile.toms.support.util.FcUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Map;

/**
 * Created by wangdayin on 2016/1/13.
 */
@WebService(serviceName = "JoinWisdomCxfServiceImpl", targetNamespace = "http://htng.org/2014B/HTNG_SeamlessShopAndBookService/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class JoinWisdomCxfServiceImpl implements IJointWisdomCxfService {
    private static Logger logger = LoggerFactory.getLogger(JoinWisdomCxfServiceImpl.class);
    @Resource
    private IJointWisdomOrderService jointWisdomOrderService;

    @Override
    @WebMethod(operationName = "CheckAvailability", action = "http://htng.org/2014B/HTNG_SeamlessShopAndBookService#CheckAvailability")
    public String CheckAvailability(@WebParam(name = "OTA_HotelAvailRQ") OTAHotelAvailRQ hotelAvailRQ) throws Exception {
        String xml = "";
        if (hotelAvailRQ != null) {
            xml = FcUtil.fcRequest(hotelAvailRQ);
            logger.info("对象转换为xml值为：" + xml);
        }
        if (StringUtils.isNotEmpty(xml)) {
            //解析xml获取请求
            Map<String, Object> map = jointWisdomOrderService.dealAvailCheckOrder(xml);
            logger.info("众荟试订单返回值：" + FcUtil.fcRequest(map.get("data")));
            return FcUtil.fcRequest(map.get("data"));
        } else {
            logger.info("众荟传入xml为空");
            return FcUtil.fcRequest(new JointWisdomAvailCheckOrderErrorResponse().getBasicError("传入xml参数为空"));
        }
    }

//    @Override
//    public String ProcessReservationRequest(@WebParam(name = "xml") String xml) throws Exception {
//        if (StringUtils.isNotEmpty(xml)) {
//            //解析xml获取请求类型
//            OrderRequestType orderRequestType = XmlJointWisdomUtil.getOrderRequestType(xml);
//            //下单
//            if (OrderRequestType.Commit.equals(orderRequestType)) {
//                Map<String, Object> map = this.jointWisdomOrderService.dealAddOrder(xml);
//                logger.info("众荟下单返回值：" + FcUtil.fcRequest(map.get("data")));
//                return FcUtil.fcRequest(map.get("data"));
//            } else if (OrderRequestType.Cancel.equals(orderRequestType)) {
//                //取消订单
//                Map<String, Object> map = this.jointWisdomOrderService.dealCancelOrder(xml);
//                logger.info("众荟取消订单返回值：" + FcUtil.fcRequest(map.get("data")));
//                return FcUtil.fcRequest(map.get("data"));
//            } else {
//                return FcUtil.fcRequest(new JointWisdomAvailCheckOrderErrorResponse().getBasicError("订单流程中请求类型不存在"));
//            }
//        } else {
//            logger.info("众荟传入xml为空");
//            return FcUtil.fcRequest(new JointWisdomAvailCheckOrderErrorResponse().getBasicError("传入xml参数为空"));
//        }
//    }
}
