package com.fanqielaile.toms.service.jointwisdomService;

import com.fanqie.jw.enums.OrderRequestType;
import com.fanqielaile.toms.service.IJointWisdomOrderService;
import com.fanqielaile.toms.support.util.FcUtil;
import com.fanqielaile.toms.support.util.XmlJointWisdomUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Map;

/**
 * Created by wangdayin on 2016/1/13.
 */
@WebService(serviceName = "JoinWisdomCxfServiceImpl", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class JoinWisdomCxfServiceImpl implements IJointWisdomCxfService {
    private static Logger logger = LoggerFactory.getLogger(JoinWisdomCxfServiceImpl.class);
    @Resource
    private IJointWisdomOrderService jointWisdomOrderService;

    @Override
    @WebResult(name = "OTA_HotelAvailRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
    @WebMethod(operationName = "CheckAvailability", action = "http://htng.org/2014B/HTNG_SeamlessShopAndBookService#CheckAvailability")
    public JointWisdomAvailCheckOrderSuccessResponse CheckAvailability(@WebParam(name = "OTA_HotelAvailRQ", targetNamespace = "http://www.opentravel.org/OTA/2003/05") OTAHotelAvailRQ hotelAvailRQ) throws Exception {
        String xml = "";
        if (hotelAvailRQ != null) {
            xml = FcUtil.fcRequest(hotelAvailRQ);
            logger.info("对象转换为xml值为：" + xml);
        }
        if (StringUtils.isNotEmpty(xml)) {
            //解析xml获取请求
            Map<String, Object> map = jointWisdomOrderService.dealAvailCheckOrder(xml);
            logger.info("众荟试订单返回值：" + FcUtil.fcRequest(map.get("data")));
            if ((boolean) map.get("status")) {
                return (JointWisdomAvailCheckOrderSuccessResponse) map.get("data");
            } else {
                return (JointWisdomAvailCheckOrderSuccessResponse) map.get("data");
            }
        } else {
            logger.info("众荟传入xml为空");
            JointWisdomAvailCheckOrderSuccessResponse basicError = new JointWisdomAvailCheckOrderSuccessResponse().getBasicError("传入xml参数为空");
            return basicError;
//            return  null;
        }
    }

    @Override
    @WebResult(name = "OTA_HotelAvailRS", targetNamespace = "http://www.opentravel.org/OTA/2003/05")
    @WebMethod(operationName = "ProcessReservationRequest", action = "http://htng.org/2014B/HTNG_SeamlessShopAndBookService#ProcessReservationRequest")
    public JointWisdomAddOrderSuccessResponse ProcessReservationRequest(@WebParam(name = "OTA_HotelResRQ") OTAHotelResRQ otaHotelResRQ) throws Exception {
        String xml = "";
        if (null != otaHotelResRQ) {
            xml = FcUtil.fcRequest(otaHotelResRQ);
            logger.info("众荟订单流程传入参数：" + xml);
        }
        try {
            if (StringUtils.isNotEmpty(xml)) {
                //解析xml获取请求类型
                OrderRequestType orderRequestType = XmlJointWisdomUtil.getOrderRequestType(xml);
                //下单
                if (OrderRequestType.Commit.equals(orderRequestType)) {
                    Map<String, Object> map = this.jointWisdomOrderService.dealAddOrder(xml);
                    logger.info("众荟下单返回值：" + FcUtil.fcRequest(map.get("data")));
                    return (JointWisdomAddOrderSuccessResponse) map.get("data");
                } else if (OrderRequestType.Cancel.equals(orderRequestType)) {
                    //取消订单
                    Map<String, Object> map = this.jointWisdomOrderService.dealCancelOrder(xml);
                    logger.info("众荟取消订单返回值：" + FcUtil.fcRequest(map.get("data")));
                    return (JointWisdomAddOrderSuccessResponse) map.get("data");
                } else {
                    return new JointWisdomAddOrderSuccessResponse().getBasicError("订单流程中请求类型不存在");
                }
            } else {
                logger.info("众荟传入xml为空");
                return new JointWisdomAddOrderSuccessResponse().getBasicError("传入xml参数为空");
            }
        } catch (Exception e) {
            logger.info("处理携程订单流程异常" + e);
            return new JointWisdomAddOrderSuccessResponse().getBasicError("处理众荟订单流程异常" + e);
        }
    }
}
