package com.fanqielaile.toms.service.impl;

import com.fanqie.bean.order.*;
import com.fanqie.qunar.enums.ResultStatus;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.dto.orderLog.OrderLogData;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.ICtripOrderService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.service.IRoomTypeService;
import com.fanqielaile.toms.support.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/21.
 */
@Service
public class CtripOrderService implements ICtripOrderService {
    private static Logger logger = LoggerFactory.getLogger(CtripOrderService.class);
    @Resource
    private OrderDao orderDao;
    @Resource
    private DailyInfosDao dailyInfosDao;
    @Resource
    private DictionaryDao dictionaryDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private OrderGuestsDao orderGuestsDao;
    @Resource
    private CompanyDao companyDao;
    @Resource
    private IOtaBangInnRoomDao bangInnRoomDao;
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private IOtaInnRoomTypeGoodsDao otaInnRoomTypeGoodsDao;
    @Resource
    private IRoomTypeService roomTypeService;
    @Resource
    private IOrderConfigDao orderConfigDao;
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;
    @Resource
    private OrderOperationRecordDao orderOperationRecordDao;
    @Resource
    private IOtaCommissionPercentDao otaCommissionPercentDao;
    @Resource
    private IOrderService orderService;
    @Resource
    private CtripRoomTypeMappingDao ctripRoomTypeMappingDao;

    @Override
    public boolean checkCtripUserPassword(String xml) throws Exception {

        //1.解析xml
        Map<String, String> userAndPassword = XmlCtripUtil.getUserAndPassword(xml);
        //判断用户名和密码是否正确
        String userName = ResourceBundleUtil.getString("ctrip.username");
        String password = ResourceBundleUtil.getString("ctrip.password");
        if (userName.equals(userAndPassword.get("userName")) && password.equals(userAndPassword.get("password"))) {
            return true;
        }
        return false;
    }

    @Override
    public CtripCheckRoomAvailResponse dealCtripCheckRoomAvail(String xml) throws Exception {
        logger.info("携程试订单传入xml=>" + xml);
        CtripCheckRoomAvailResponse ctripCheckRoomAvailResponse = null;
        CtripCheckRoomAvailRequest ctripCheckAvailRequest = XmlCtripUtil.getCtripCheckAvailRequest(xml);
        logger.info("携程试订单转换为对象参数CtripCheckRoomAvailRequest=>" + ctripCheckAvailRequest.toString());
        MessageCenterUtils.savePushTomsOrderLog(ctripCheckAvailRequest.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.XC, null, null, null, null, null, null, xml, null, ctripCheckAvailRequest.getInnId(), null, "携程试订单请求参数"));
        if (null != ctripCheckAvailRequest) {
            //1.将携程试订单请求对象转换为toms订单对象
            //1.1查询otainfo信息
            OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectOtaInfoByType(OtaType.XC.name());
            //1.1 查询公司信息和绑定客栈信息
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByHidAndOtaInfoId(Long.valueOf(ctripCheckAvailRequest.getHotel()), otaInfoRefDto.getId());
            //查询公司信息
            Company company = this.companyDao.selectCompanyById(otaInnOtaDto.getCompanyId());
            ctripCheckAvailRequest.setInnId(Integer.valueOf(otaInnOtaDto.getInnId()));
            //封装oms房型id
            CtripRoomTypeMapping ctripRoomTypeMapping = this.ctripRoomTypeMappingDao.selectRoomTypeByHotelIdAndRoomTypeId(ctripCheckAvailRequest.getHotel(), ctripCheckAvailRequest.getRoom());
            ctripCheckAvailRequest.setRoomTypeId(Integer.valueOf(ctripRoomTypeMapping.getTomRoomTypeId()));
            Order order = XmlCtripUtil.getCheckAvailOrder(ctripCheckAvailRequest);
            Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.CHECK_ORDER.name());
            logger.info("携程试订单oms接口传递参数=>" + order.toRoomAvail(company, order).toString());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(order.getChannelSource(), null, null, null, null, null, null, order.toRoomAvail(company, order).toString(), null, order.getInnId(), order.getInnCode(), "携程试订单请求，oms请求参数"));
            String response = HttpClientUtil.httpGetRoomAvail(dictionary.getUrl(), order.toRoomAvail(company, order));
            JSONObject jsonObject = JSONObject.fromObject(response);
            logger.info("携程试订单oms接口返回值=>" + response.toString());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(order.getChannelSource(), null, null, null, null, null, null, order.toRoomAvail(company, order).toString(), response.toString(), order.getInnId(), order.getInnCode(), "携程试订单请求，oms返回值"));
            if (jsonObject.get("status").equals(200)) {
                //查询当前的价格模式
                BigDecimal percent = BigDecimal.ZERO;
                //2.判断当前公司使用什么价格模式
                OtaInfoRefDto otaInfo = this.otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(company.getId(), otaInfoRefDto.getId());
                if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel())) {
                    //查询价格比例
                    OtaCommissionPercentDto commission = this.otaCommissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), otaInfo.getUsedPriceModel().name()));
                    percent = TomsUtil.getPercent(BigDecimal.valueOf(commission.getCommissionPercent()));
                }
                List<RoomDetail> roomDetails = (List<RoomDetail>) JSONArray.toList(jsonObject.getJSONArray("data"), RoomDetail.class);
                //转换oms房型信息为toms的每日入住信息
                List<DailyInfos> dailyInfos = OrderMethodHelper.toDailyInfos(roomDetails);
                //封装返回对象
                List<CtripAvailRequestRoomPrice> ctripAvailRequestRoomPriceList = new ArrayList<>();
                List<AvailRoomQuantity> availRoomQuantityList = new ArrayList<>();
                //判断是否可预订
                boolean isBook = true;
                BigDecimal checkAvailTotalPrice = BigDecimal.ZERO;
                if (ArrayUtils.isNotEmpty(dailyInfos.toArray())) {
                    for (DailyInfos dailyInfo : dailyInfos) {
                        //组装可用房量信息
                        AvailRoomQuantity availRoomQuantity = new AvailRoomQuantity();
                        availRoomQuantity.setEffectDate(TomsUtil.getDateStringFormat(dailyInfo.getDay()));
                        availRoomQuantity.setAvailQuantity(dailyInfo.getRoomNum());
                        availRoomQuantityList.add(availRoomQuantity);
                        //组装每日价格信息
                        CtripAvailRequestRoomPrice ctripAvailRequestRoomPrice = new CtripAvailRequestRoomPrice();
                        //设置早餐数量
                        ctripAvailRequestRoomPrice.setBreakFast(0);
                        //设置日期
                        ctripAvailRequestRoomPrice.setEffectDate(TomsUtil.getDateStringFormat(dailyInfo.getDay()));

                        OtaRoomPriceDto otaRoomPriceDto = this.otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(order.getRoomTypeId()), otaInfo.getOtaInfoId()));
                        //判断当前公司是什么价格模式，组装返回的价格 判断是否执行加减价
                        //将所有小数全部五入
                        dailyInfo.setPrice(BigDecimal.valueOf(TomsUtil.getPriceRoundUp(dailyInfo.getPrice().doubleValue())));
                        if (UsedPriceModel.MAI.equals(otaInfo.getUsedPriceModel())) {
                            ctripAvailRequestRoomPrice.setPrice(BigDecimal.valueOf(TomsUtil.getPriceRoundUp(dailyInfo.getPrice().doubleValue())));
                            ctripAvailRequestRoomPrice.setcNYCost(BigDecimal.valueOf(TomsUtil.getPriceRoundUp(ctripAvailRequestRoomPrice.getPrice().doubleValue())));
                            ctripAvailRequestRoomPrice.setCost(BigDecimal.ZERO);
                            ctripAvailRequestRoomPrice.setcNYCost(BigDecimal.ZERO);
                            //设置加减价
                            if (null != otaRoomPriceDto && dailyInfo.getDay().getTime() >= otaRoomPriceDto.getStartDate().getTime() && dailyInfo.getDay().getTime() <= otaRoomPriceDto.getEndDate().getTime()) {
                                ctripAvailRequestRoomPrice.setPrice(ctripAvailRequestRoomPrice.getPrice().add(BigDecimal.valueOf(otaRoomPriceDto.getValue())));
                                ctripAvailRequestRoomPrice.setcNYPrice(ctripAvailRequestRoomPrice.getPrice());
                            }
                            //设置试订单总价
                            checkAvailTotalPrice = checkAvailTotalPrice.add(ctripAvailRequestRoomPrice.getPrice());

                        } else if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel())) {
                            ctripAvailRequestRoomPrice.setPrice(BigDecimal.ZERO);
                            ctripAvailRequestRoomPrice.setcNYPrice(BigDecimal.ZERO);
                            ctripAvailRequestRoomPrice.setCost(BigDecimal.valueOf(TomsUtil.getPriceRoundUp(dailyInfo.getPrice().multiply((new BigDecimal(1).subtract(percent))).doubleValue())));
                            ctripAvailRequestRoomPrice.setcNYCost(ctripAvailRequestRoomPrice.getCost());
                            //设置加减价
                            if (null != otaRoomPriceDto && dailyInfo.getDay().getTime() >= otaRoomPriceDto.getStartDate().getTime() && dailyInfo.getDay().getTime() <= otaRoomPriceDto.getEndDate().getTime()) {
                                ctripAvailRequestRoomPrice.setCost(ctripAvailRequestRoomPrice.getCost().add(BigDecimal.valueOf(otaRoomPriceDto.getValue())));
                                ctripAvailRequestRoomPrice.setcNYCost(ctripAvailRequestRoomPrice.getCost());
                            }
                            //设置试订单总价
                            checkAvailTotalPrice = checkAvailTotalPrice.add(ctripAvailRequestRoomPrice.getCost());

                        } else {
                            ctripAvailRequestRoomPrice.setPrice(BigDecimal.ZERO);
                            ctripAvailRequestRoomPrice.setcNYPrice(BigDecimal.ZERO);
                            ctripAvailRequestRoomPrice.setCost(BigDecimal.valueOf(TomsUtil.getPriceRoundUp(dailyInfo.getPrice().doubleValue())));
                            ctripAvailRequestRoomPrice.setcNYCost(ctripAvailRequestRoomPrice.getCost());
                            //设置加减价
                            if (null != otaRoomPriceDto && dailyInfo.getDay().getTime() >= otaRoomPriceDto.getStartDate().getTime() && dailyInfo.getDay().getTime() <= otaRoomPriceDto.getEndDate().getTime()) {
                                ctripAvailRequestRoomPrice.setCost(ctripAvailRequestRoomPrice.getCost().add(BigDecimal.valueOf(otaRoomPriceDto.getValue())));
                                ctripAvailRequestRoomPrice.setcNYCost(ctripAvailRequestRoomPrice.getCost());
                            }
                            //设置试订单总价
                            checkAvailTotalPrice = checkAvailTotalPrice.add(ctripAvailRequestRoomPrice.getCost());
                        }


                        if (dailyInfo.getRoomNum() > order.getHomeAmount()) {
                            if (isBook) {
                                isBook = true;
                            }
                        } else {
                            if (isBook) {
                                isBook = false;
                            }
                        }
                        ctripAvailRequestRoomPriceList.add(ctripAvailRequestRoomPrice);
                    }
                }
                //组装试订单DomesticCheckRoomAvailResponse返回对象
                DomesticCheckRoomAvailResponse domesticCheckRoomAvailResponse = new DomesticCheckRoomAvailResponse();
                //是否可预订
                for (DailyInfos dailyInfos1 : dailyInfos) {
                    if (dailyInfos1.getRoomNum() < order.getHomeAmount()) {
                        isBook = false;
                        break;
                    } else {
                        isBook = true;
                    }
                }
                domesticCheckRoomAvailResponse.setIsBookable(isBook ? 1 : 0);
                //试订单总价
                domesticCheckRoomAvailResponse.setInterFaceAmount(checkAvailTotalPrice);
                //可用房量
                domesticCheckRoomAvailResponse.setAvailRoomQuantities(availRoomQuantityList);
                //每日价格
                domesticCheckRoomAvailResponse.setCtripAvailRequestRoomPrices(ctripAvailRequestRoomPriceList);
                domesticCheckRoomAvailResponse.setRoom(ctripRoomTypeMapping.getCtripChildRoomTypeId());
                CtripCheckRoomAvailResponseResult ctripCheckRoomAvailResponseResult = new CtripCheckRoomAvailResponseResult();
                ctripCheckRoomAvailResponseResult.setMessage("获取试订单信息成功");
                ctripCheckRoomAvailResponseResult.setResultCode("0");
                ctripCheckRoomAvailResponseResult.setDomesticCheckRoomAvailResponse(domesticCheckRoomAvailResponse);
                ctripCheckRoomAvailResponse = new CtripCheckRoomAvailResponse();
                ctripCheckRoomAvailResponse.setCtripCheckRoomAvailResponseResult(ctripCheckRoomAvailResponseResult);

            }
        } else {
            logger.info("解析携程试订单对象为空");
        }
        MessageCenterUtils.savePushTomsOrderLog(ctripCheckAvailRequest.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.XC, xml, JacksonUtil.obj2json(ctripCheckRoomAvailResponse)));
        return ctripCheckRoomAvailResponse;
    }

    @Override
    public CtripCancelHotelOrderResponse cancelOrderMethod(String xml) throws Exception {
        logger.info("携程取消订单传入xml" + xml);
        //1.解析携程取消订单传入参数
        Order cancelOrder = XmlCtripUtil.getCancelOrder(xml);
        logger.info("携程取消订单号为：" + cancelOrder.getChannelOrderCode());
        //2.判断订单是否在toms存在
        CtripCancelHotelOrderResponse ctripCancelHotelOrderResponse = new CtripCancelHotelOrderResponse();
        CtripCancelHotelOrderResponseResult ctripCancelHotelOrderResponseResult = new CtripCancelHotelOrderResponseResult();
        DomesticCancelHotelOrderResponse domesticCancelHotelOrderResponse = new DomesticCancelHotelOrderResponse();
        if (null != cancelOrder) {
            Order order = this.orderDao.selectOrderByChannelOrderCodeAndSource(cancelOrder);
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CANCEL_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, null, order.getInnId(), order.getInnCode(), "携程取消订单"));
            if (null != order) {
                //3.oms取消订单处理业务方法
                JsonModel jsonModel = this.orderService.cancelOrderMethod(order);
                domesticCancelHotelOrderResponse.setInterFaceConfirmNO(cancelOrder.getInterFaceConfirmNO());
                domesticCancelHotelOrderResponse.setOrderId(order.getChannelOrderCode());
                domesticCancelHotelOrderResponse.setReturnCode("0");
                //判断取消是否成功
                if (jsonModel.isSuccess()) {
                    domesticCancelHotelOrderResponse.setOrderStatus(3);
                    domesticCancelHotelOrderResponse.setReturnDescript("取消成功");
                } else {
                    domesticCancelHotelOrderResponse.setOrderStatus(4);
                    domesticCancelHotelOrderResponse.setReturnDescript("酒店拒绝取消");
                }
            }
        }
        ctripCancelHotelOrderResponseResult.setResultCode(String.valueOf(0));
        ctripCancelHotelOrderResponseResult.setMessage("取消订单响应成功");
        ctripCancelHotelOrderResponseResult.setDomesticCancelHotelOrderResponse(domesticCancelHotelOrderResponse);
        ctripCancelHotelOrderResponse.setCtripCancelHotelOrderResponseResult(ctripCancelHotelOrderResponseResult);
        logger.info("携程取消订单返回值:" + ctripCancelHotelOrderResponse.toString());
        MessageCenterUtils.savePushTomsOrderLog(cancelOrder.getInnId(), OrderLogDec.CANCEL_ORDER, new OrderLogData(ChannelSource.XC, JacksonUtil.obj2json(ctripCancelHotelOrderResponse), "携程取消订单返回值"));
        return ctripCancelHotelOrderResponse;
    }

    @Override
    public CtripGetOrderStatusResponse getOrderStatus(String xml) throws Exception {
        logger.info("携程获取订单状态传入参数xml" + xml);
        CtripGetOrderStatusResponse ctripGetOrderStatusResponse = new CtripGetOrderStatusResponse();
        CtripGetOrderStatusResponseResult ctripGetOrderStatusResponseResult = new CtripGetOrderStatusResponseResult();
        DomesticGetOrderStatusResponse domesticGetOrderStatusResponse = new DomesticGetOrderStatusResponse();
        List<OrderEntity> orderEntityList = new ArrayList<>();
        //1.解析xml装换为order对象
        List<Order> orderList = XmlCtripUtil.getOrderStatus(xml);
        if (ArrayUtils.isNotEmpty(orderList.toArray())) {
            for (Order order : orderList) {
                logger.info("携程获取定案状态订单号:" + order.getChannelOrderCode());
                OrderEntity orderEntity = new OrderEntity();
                //查询toms本地订单信息
                Order orderDb = this.orderDao.selectOrderByChannelOrderCodeAndSource(order);
                MessageCenterUtils.savePushTomsOrderLog(orderDb.getInnId(), OrderLogDec.SEARCH_ORDER, new OrderLogData(orderDb.getChannelSource(), xml, "携程获取订单状态请求参数"));
                if (null != orderDb) {
                    //调用获取oms订单状态接口
                    String jsonString = this.orderService.getOrderStatusMethod(orderDb);
                    //格式化json
                    JSONObject jsonObject = JSONObject.fromObject(jsonString);
                    String orderStatus = (String) jsonObject.get("orderStatus");
                    if (!jsonObject.get("status").equals(200)) {
                        orderEntity.setOrderStatus(2);
                    } else {
                        //对应订单状态值
                        if (orderStatus.equals("0")) {
                            orderEntity.setOrderStatus(0);
                        } else if (orderStatus.equals("1")) {
                            orderEntity.setOrderStatus(3);
                        } else if (orderStatus.equals("2")) {
                            orderEntity.setOrderStatus(4);
                        } else if (orderStatus.equals("3")) {
                            orderEntity.setOrderStatus(4);
                        } else if (orderStatus.equals("4")) {
                            orderEntity.setOrderStatus(4);
                        } else if (orderStatus.equals("5")) {
                            orderEntity.setOrderStatus(3);
                        } else {
                            orderEntity.setOrderStatus(5);
                        }
                        orderEntity.setOrderId(orderDb.getChannelOrderCode());

                    }
                } else {
                    //订单不存在
                    orderEntity.setOrderStatus(6);
                }
                orderEntity.setInterFaceSendID(order.getInterFaceSendID());
                orderEntity.setInterfaceConfirmNo(order.getInterFaceConfirmNO());
                orderEntity.setReturnCode("0");
                orderEntity.setReturnDescript("接收成功");
                orderEntityList.add(orderEntity);
            }
        }
        //封装返回对象
        domesticGetOrderStatusResponse.setOrderEntityList(orderEntityList);
        ctripGetOrderStatusResponseResult.setResultCode("0");
        ctripGetOrderStatusResponseResult.setMessage("携程获取订单状态相应成功");
        ctripGetOrderStatusResponseResult.setDomesticGetOrderStatusResponse(domesticGetOrderStatusResponse);
        ctripGetOrderStatusResponse.setCtripGetOrderStatusResponseResult(ctripGetOrderStatusResponseResult);
        logger.info("携程获取订单状态返回值：" + ctripGetOrderStatusResponse.toString());
        MessageCenterUtils.savePushTomsOrderLog(null, OrderLogDec.SEARCH_ORDER, new OrderLogData(ChannelSource.XC, JacksonUtil.obj2json(ctripGetOrderStatusResponse), "携程获取订单状态返回值"));
        return ctripGetOrderStatusResponse;
    }

    @Override
    public CtripNewHotelOrderResponse addOrder(String xml) throws Exception {
        logger.info("携程下单传入xml=>" + xml);
        //解析xml成order对象
        Order order = XmlCtripUtil.getNewOrder(xml);
        logger.info("携程下单的订单号=>" + order.getChannelOrderCode());
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, null, order.getInnId(), order.getInnCode(), "携程创建订单，传入参数"));
        CtripRoomTypeMapping ctripRoomTypeMapping = this.ctripRoomTypeMappingDao.selectRoomTypeByHotelIdAndRoomTypeId(order.getOTAHotelId(), order.getOTARoomTypeId());
        order.setInnId(Integer.parseInt(ctripRoomTypeMapping.getInnId()));
        order.setRoomTypeId(ctripRoomTypeMapping.getTomRoomTypeId());
        //1.创建toms本地订单
        order.setXmlData(xml);
        //验证订单是否存在
        //验证订单是否存在
        JsonModel jsonModel = null;
        Order checkOrder = this.orderDao.selectOrderByChannelOrderCodeAndSource(order);
        if (null != checkOrder) {
            String orderStatusMethod = this.orderService.getOrderStatusMethod(checkOrder);
            //解析返回值
            if (StringUtils.isNotEmpty(orderStatusMethod)) {
                net.sf.json.JSONObject jsonObjectOmsSearchOrder = net.sf.json.JSONObject.fromObject(orderStatusMethod);
                if (jsonObjectOmsSearchOrder.get("status").equals(200)) {
                    String omsOrderStatus = (String) jsonObjectOmsSearchOrder.get("orderStatus");
                    if (omsOrderStatus.equals("1")) {
                        //下单成功
                        jsonModel = new JsonModel(true, "付款成功");
                    } else if (omsOrderStatus.equals("0")) {
                        checkOrder.setOrderStatus(OrderStatus.CANCEL_ORDER);
                        orderService.cancelOrderMethod(checkOrder);
                        jsonModel = new JsonModel(false, "下单失败,取消订单");
                    } else {
                        jsonModel = new JsonModel(false, "下单失败");
                    }
                } else {
                    //调用oms取消订单
                    checkOrder.setOrderStatus(OrderStatus.CANCEL_ORDER);
                    //调用渠道，oms取消订单接口
                    orderService.cancelOrderMethod(checkOrder);
                    jsonModel = new JsonModel(false, "下单失败");
                }
            } else {
                checkOrder.setOrderStatus(OrderStatus.CANCEL_ORDER);
                orderService.cancelOrderMethod(checkOrder);
                jsonModel = new JsonModel(false, "下单失败,取消订单");
            }
        } else {
            this.orderService.createOrderMethod(order.getChannelSource(), order);
            //携程创建订单，同步oms
            //查询当前公司设置的下单是自动或者手动
            //1.判断当前订单客栈属于哪个公司，查找公司设置的下单规则
            OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectOtaInfoByType(OtaType.XC.name());
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByHidAndOtaInfoId(Long.valueOf(order.getOTAHotelId()), otaInfoRefDto.getId());
            OrderConfig orderConfig = new OrderConfig(otaInnOtaDto.getOtaInfoId(), otaInnOtaDto.getCompanyId(), Integer.valueOf(order.getInnId()));
            OrderConfigDto orderConfigDto = orderConfigDao.selectOrderConfigByOtaInfoId(orderConfig);
            if (null == orderConfigDto || 0 == orderConfigDto.getStatus()) {
                //自动下单
                //设置订单状态为：接受
                order.setOrderStatus(OrderStatus.ACCEPT);
                jsonModel = this.orderService.payBackDealMethod(order, new UserInfo(), OtaType.XC.name());
            } else {
                //手动下单,手动下单修改订单状态为待处理
                order.setOrderStatus(OrderStatus.NOT_DEAL);
                //待处理订单写入付款金额和付款码
                order.setFeeStatus(FeeStatus.PAID);
                this.orderDao.updateOrderStatusAndFeeStatus(order);
                this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.NOT_DEAL, "手动下单", ChannelSource.FC.name()));
                jsonModel = new JsonModel(true, "付款成功");
            }
        }
        //封装返回对象
        CtripNewHotelOrderResponse ctripNewHotelOrderResponse = new CtripNewHotelOrderResponse();
        CtripNewHotelOrderResponseResult ctripNewHotelOrderResponseResult = new CtripNewHotelOrderResponseResult();
        DomesticSubmitNewHotelOrderResponse domesticSubmitNewHotelOrderResponse = new DomesticSubmitNewHotelOrderResponse();
        domesticSubmitNewHotelOrderResponse.setInterFaceConfirmNO(order.getInterFaceSendID());
        domesticSubmitNewHotelOrderResponse.setInterFaceAmount(order.getBasicTotalPrice());
        domesticSubmitNewHotelOrderResponse.setHotelConfirmNo("0");

        if (jsonModel.isSuccess()) {
            //付款成功返回
            domesticSubmitNewHotelOrderResponse.setOrderStatus(3);
            domesticSubmitNewHotelOrderResponse.setReturnCode("0");
            domesticSubmitNewHotelOrderResponse.setReturnDescript("预定成功");
            ctripNewHotelOrderResponseResult.setResultCode("0");
            ctripNewHotelOrderResponseResult.setMessage("预定成功");
        } else {
            //付款失败返回
            domesticSubmitNewHotelOrderResponse.setOrderStatus(4);
            domesticSubmitNewHotelOrderResponse.setReturnCode("101");
            domesticSubmitNewHotelOrderResponse.setReturnDescript("预定失败");
            ctripNewHotelOrderResponseResult.setResultCode("101");
            ctripNewHotelOrderResponseResult.setMessage("预定失败");
        }
        ctripNewHotelOrderResponseResult.setDomesticSubmitNewHotelOrderResponse(domesticSubmitNewHotelOrderResponse);
        ctripNewHotelOrderResponse.setCtripNewHotelOrderResponseResult(ctripNewHotelOrderResponseResult);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, JacksonUtil.obj2json(ctripNewHotelOrderResponse), order.getInnId(), order.getInnCode(), "携程创建订单返回值"));
        return ctripNewHotelOrderResponse;
    }


}
