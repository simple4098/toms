package com.fanqielaile.toms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fanqie.jw.enums.OrderResponseType;
import com.fanqie.jw.enums.Version;
import com.fanqie.qunar.enums.*;
import com.fanqie.qunar.model.*;
import com.fanqie.qunar.model.BedType;
import com.fanqie.qunar.request.PriceRequest;
import com.fanqie.qunar.response.BookingResponse;
import com.fanqie.qunar.response.QunarCancelOrderResponse;
import com.fanqie.qunar.response.QunarGetRoomTypeInfoResponse;
import com.fanqie.qunar.response.QunarWrapperOrderQueryResponse;
import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.orderLog.OrderLogData;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.IQunarOrderService;
import com.fanqielaile.toms.service.jointwisdomService.JointWisdomAddOrderSuccessResponse;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.JsonModel;
import com.fanqielaile.toms.support.util.MessageCenterUtils;
import com.fanqielaile.toms.support.util.QunarUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2016/4/15.
 */
@Service
public class QunarOrderService implements IQunarOrderService {
    private static Logger logger = LoggerFactory.getLogger(QunarOrderService.class);

    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private IOtaCommissionPercentDao otaCommissionPercentDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private CompanyDao companyDao;
    @Resource
    private IOtaRoomPriceDao otaRoomPriceDao;
    @Resource
    private OrderService orderService;
    @Resource
    private IOrderConfigDao orderConfigDao;
    @Resource
    private OrderOperationRecordDao orderOperationRecordDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private ExceptionOrderDao exceptionOrderDao;

    @Override
    public QunarGetRoomTypeInfoResponse findRoomTypeInfo(String xml) {
        QunarGetRoomTypeInfoResponse result = new QunarGetRoomTypeInfoResponse();
        //解析xml
        PriceRequest priceRequest = QunarUtil.getPriceRequest(xml);
        try {
            //通过hotelid查询公司信息
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(priceRequest.getHotelId());
            //查询绑定客栈信息
            BangInnDto bangInnDto = this.bangInnDao.selectBangInnById(otaInnOtaDto.getBangInnId());
            //查询公司信息
            Company company = this.companyDao.selectCompanyById(bangInnDto.getCompanyId());
            //查询公司上架模式
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(otaInnOtaDto.getCompanyId(), otaInnOtaDto.getOtaInfoId());
            //设置acountid
            if (!UsedPriceModel.MAI.equals(otaInfo.getUsedPriceModel())) {
                bangInnDto.setAccountId(bangInnDto.getAccountIdDi());
            }
            //oms房态请求参数
            String roomStatus = "";
            String roomType = "";
            //判断房型id是否为空
            if (StringUtils.isNotEmpty(priceRequest.getRoomId())) {
                //获取oms房态信息
                roomStatus = DcUtil.omsTbRoomTYpeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(), bangInnDto.getInnId(), Integer.valueOf(priceRequest.getRoomId()), CommonApi.roomStatus, priceRequest.getCheckIn(), DateUtil.format(DateUtil.addDay(DateUtil.parseDate(priceRequest.getCheckOut(), "yyyy-MM-dd"), -1), "yyyy-MM-dd"), bangInnDto.getAccountId());
                roomType = DcUtil.omsTbRoomTYpeUrl(company.getUserAccount(), company.getUserPassword(), company.getOtaId(), bangInnDto.getInnId(), Integer.valueOf(priceRequest.getRoomId()), CommonApi.ROOM_TYPE, priceRequest.getCheckIn(), DateUtil.format(DateUtil.addDay(DateUtil.parseDate(priceRequest.getCheckOut(), "yyyy-MM-dd"), -1), "yyyy-MM-dd"), bangInnDto.getAccountId());
            } else {
                roomStatus = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInnDto.getAccountId()), CommonApi.roomStatus, priceRequest.getCheckIn(), DateUtil.format(DateUtil.addDay(DateUtil.parseDate(priceRequest.getCheckOut(), "yyyy-MM-dd"), -1), "yyyy-MM-dd"));
                roomType = DcUtil.omsRoomTYpeUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInnDto.getAccountId()), CommonApi.ROOM_TYPE, priceRequest.getCheckIn(), DateUtil.format(DateUtil.addDay(DateUtil.parseDate(priceRequest.getCheckOut(), "yyyy-MM-dd"), -1), "yyyy-MM-dd"));
            }
            List<RoomTypeInfo> list = InnRoomHelper.getRoomTypeInfo(roomType);
            if (StringUtils.isNotEmpty(priceRequest.getRoomId())) {
                list = QunarUtil.dealRoomTypeList(list, priceRequest.getRoomId());
            }
            List<RoomStatusDetail> statusDetails = InnRoomHelper.getRoomStatus(roomStatus);
            InnRoomHelper.updateRoomTypeInfo(list, statusDetails);

            //组装参数
            result.setHotelId(priceRequest.getHotelId());
            result.setCheckIn(priceRequest.getCheckIn());
            result.setCheckOut(priceRequest.getCheckOut());
            result.setCurrencyCode(CurrencyCode.CNY.name());
            result.setHotelAddress(bangInnDto.getAddress());
            result.setHotelCity(bangInnDto.getQunarCityCode());
            result.setHotelName(bangInnDto.getInnName());
            result.setHotelPhone(bangInnDto.getMobile());
            List<Room> roomList = new ArrayList<>();
            if (null != list && ArrayUtils.isNotEmpty(list.toArray())) {
                for (RoomTypeInfo roomDetail : list) {
                    Room room = new Room();
                    room.setId(String.valueOf(roomDetail.getRoomTypeId()));
                    //查询加减价
                    //查询价格模式（toms的增减价）
                    OtaRoomPriceDto otaRoomPriceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(otaInnOtaDto.getCompanyId(), roomDetail.getRoomTypeId(), otaInnOtaDto.getOtaInfoId()));
                    room.setName(roomDetail.getRoomTypeName());
                    room.setPayType(PaymentType.PREPAY.name());
                    room.setBroadband(FeeMode.UNKNOWN.name());
                    room.setWifi(FeeMode.UNKNOWN.name());
                    room.setMaxOccupancy(roomDetail.getBedTypeValue());
                    room.setOccupancyNumber(roomDetail.getBedTypeValue());
                    room.setCheckinTime("12:30");
                    room.setCheckoutTime("12:00");
                    room.setArea(String.valueOf(roomDetail.getRoomArea()));
                    //设置价格，房间状态等
                    String price = "";
                    String status = "";
                    String counts = "";
                    String roomRate = "";
                    String taxAndFee = "";
                    String instantConfirmRoomCount = "";
                    String mealCount = "";
                    String mealDesc = "无";
                    if (null != roomDetail && ArrayUtils.isNotEmpty(roomDetail.getRoomDetail().toArray())) {
                        for (RoomDetail roomD : roomDetail.getRoomDetail()) {
                            //设置房型的加减价
                            if (null != otaRoomPriceDto) {
                                if (DateUtil.isBetween(roomD.getRoomDate(), DateUtil.format(otaRoomPriceDto.getStartDate(), "yyyy-MM-dd"), DateUtil.format(otaRoomPriceDto.getEndDate(), "yyyy-MM-dd"))) {
                                    roomD.setRoomPrice(roomD.getRoomPrice() + otaRoomPriceDto.getValue());
                                }
                            }
                            price += roomD.getRoomPrice() + "|";
                            if (roomD.getRoomNum() >= priceRequest.getNumberOfRooms()) {
                                status = status + RoomStatus.ACTIVE.name() + "|";
                            } else {
                                status = status + RoomStatus.DISABLED.name() + "|";
                            }
                            counts += roomD.getRoomNum() + "|";
                            roomRate += roomD.getRoomPrice() + "|";
                            taxAndFee += 0 + "|";
                            instantConfirmRoomCount += 0 + "|";
                            mealCount += 0 + "|";

                        }
                    }
                    //早餐meal
                    Meal meal = new Meal();
                    Breakfast breakfast = new Breakfast();
                    breakfast.setCount(mealCount.substring(0, mealCount.length() - 1));
                    breakfast.setDesc(mealDesc);
                    meal.setBreakfast(breakfast);
                    Lunch lunch = new Lunch();
                    lunch.setCount(mealCount.substring(0, mealCount.length() - 1));
                    lunch.setDesc(mealDesc);
                    meal.setLunch(lunch);
                    Dinner dinner = new Dinner();
                    dinner.setCount(mealCount.substring(0, mealCount.length() - 1));
                    dinner.setDesc(mealDesc);
                    meal.setDinner(dinner);
                    room.setMeal(meal);
                    //退订规则
                    Refund refund = new Refund();
                    refund.setReturnable(true);
                    refund.setTimeZone("GMT+8");
                    refund.setRefundRuleList(refund.getDefaultRule());
                    List<NonRefundableRange> nonRefundableRangeList = new ArrayList<>();
                    NonRefundableRange nonRefundableRange = new NonRefundableRange();
                    nonRefundableRange.setFromDate(priceRequest.getCheckIn());
                    nonRefundableRange.setToDate(priceRequest.getCheckOut());
                    nonRefundableRangeList.add(nonRefundableRange);
                    refund.setNonRefundableRangeList(nonRefundableRangeList);
                    room.setRefund(refund);
                    room.setPrices(price.substring(0, price.length() - 1));
                    room.setStatus(status.substring(0, status.length() - 1));
                    room.setCounts(counts.substring(0, counts.length() - 1));
                    room.setRoomRate(roomRate.substring(0, roomRate.length() - 1));
                    room.setTaxAndFee(taxAndFee.substring(0, taxAndFee.length() - 1));
                    room.setInstantConfirmRoomCount(instantConfirmRoomCount.substring(0, instantConfirmRoomCount.length() - 1));
                    BedType bedType = new BedType();
                    bedType.setRelation("OR");
                    List<Bed> bedList = new ArrayList<>();
                    Bed bed = new Bed();
                    bed.setCode(bed.dealCodeMethod(Integer.valueOf(roomDetail.getBedTypeValue())));
                    bed.setSeq(1);
                    bed.setCount(roomDetail.getBedNum());
                    bed.setDesc(roomDetail.getBedType());
                    bed.setSize(roomDetail.getBedLen() + "cm*" + roomDetail.getBedWid() + "cm");
                    bedList.add(bed);
                    bedType.setBeds(bedList);
                    room.setBedType(bedType);
                    roomList.add(room);
                }
            }
            result.setRoomList(roomList);

        } catch (IOException e) {
            logger.info("去哪儿获取酒店房型信息出错" + e);
        }
        return result;
    }

    @Override
    public BookingResponse createOrderByQunar(String xml) {
        BookingResponse result = new BookingResponse();
        //解析xml
        Order order = QunarUtil.getOrderDto(xml);
        try {
            //根据otahotelid查询信息
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
            //查询公司信息
            Company company = this.companyDao.selectCompanyById(otaInnOtaDto.getCompanyId());
            //查询绑定客栈信息
            BangInnDto bangInnDto = this.bangInnDao.selectBangInnById(otaInnOtaDto.getBangInnId());
            order.setInnId(bangInnDto.getInnId());
            order.setCompanyId(otaInnOtaDto.getCompanyId());
            order.setOrderInnName(bangInnDto.getInnName());
            //查询公司signkey
            OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(otaInnOtaDto.getCompanyId(), otaInnOtaDto.getOtaInfoId());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, null, order.getInnId(), order.getInnCode(), "去哪儿创建订单传入参数"));
            //1.创建toms本地订单
            order.setXmlData(xml);
            this.orderService.createOrderMethod(order.getChannelSource(), order);
            //查询当前公司设置的下单是自动或者手动
            //1.判断当前订单客栈属于哪个公司，查找公司设置的下单规则
            OrderConfig orderConfig = new OrderConfig(otaInnOtaDto.getOtaInfoId(), company.getId(), Integer.valueOf(order.getInnId()));
            OrderConfigDto orderConfigDto = orderConfigDao.selectOrderConfigByOtaInfoId(orderConfig);
            JsonModel jsonModel = new JsonModel();
            if (null == orderConfigDto || 0 == orderConfigDto.getStatus()) {
                //自动下单
                //设置订单状态为：接受
                order.setOrderStatus(OrderStatus.ACCEPT);
                jsonModel = this.orderService.payBackDealMethod(order, new UserInfo(), OtaType.QUNAR.name());
            } else {
                //手动下单,手动下单修改订单状态为待处理
                order.setOrderStatus(OrderStatus.NOT_DEAL);
                //待处理订单写入付款金额和付款码
                order.setFeeStatus(FeeStatus.PAID);
                this.orderDao.updateOrderStatusAndFeeStatus(order);
                this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.NOT_DEAL, "去哪儿预定", ChannelSource.QUNAR.name()));
                jsonModel = new JsonModel(true, "付款成功");
            }
            String response = "";
            //判断下单到oms是否成功
            if (jsonModel.isSuccess()) {
                //下单到oms成功
                //组装返回参数
                result.setOrderId(order.getId());
                result.setMsg(ResultStatus.SUCCESS.getText());
                result.setQunarOrderNum(order.getChannelOrderCode());
                result.setResult(ResultStatus.SUCCESS.name());
                MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, JacksonUtil.obj2json(result), order.getInnId(), order.getInnCode(), "去哪儿创建订单,toms返回值"));
                //调用去哪儿订单修改接口
                response = HttpClientUtil.httpPostQunarOrderOpt(CommonApi.qunarOrderOpt, order.getChannelOrderCode(), OptCode.CONFIRM_ROOM_SUCCESS.name(), otaInfoRefDto.getSessionKey(), BigDecimal.ZERO);

            } else {
                //下单到oms失败
                result.setOrderId(order.getId());
                result.setMsg(ResultStatus.FAILURE.getText());
                result.setQunarOrderNum(order.getChannelOrderCode());
                result.setResult(ResultStatus.FAILURE.name());
                MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, JacksonUtil.obj2json(new JointWisdomAddOrderSuccessResponse().getBasicError(jsonModel.getMessage() + "  预定失败", Version.v1003.getText(), OrderResponseType.Committed.name())), order.getInnId(), order.getInnCode(), "去哪儿创建订单,toms返回值"));
                // 调用去哪儿订单修改接口
                response = HttpClientUtil.httpPostQunarOrderOpt(CommonApi.qunarOrderOpt, order.getChannelOrderCode(), OptCode.CONFIRM_ROOM_FAILURE.name(), otaInfoRefDto.getSessionKey(), BigDecimal.ZERO);
            }
            //判断返回是否失败，记录异常订单
            JSONObject jsonObject = JSONObject.parseObject(response);
            logger.info("调用去哪儿订单操作接口返回值：orderCode" + order.getChannelOrderCode() + response);
            if (!(Boolean) jsonObject.get("ret")) {
                this.exceptionOrderDao.insertExceptionOrder(order.getExceptionOrderListByOrder(order));
                //发送微信
                MessageCenterUtils.sendWeiXin("去哪儿预定异常，请联系相关人员，订单号：" + order.getChannelOrderCode());
            }
        } catch (Exception e) {
            logger.info("去哪儿创建订单出错,orderCode:" + order.getChannelOrderCode());
            result.setOrderId(order.getId());
            result.setMsg(ResultStatus.FAILURE.getText());
            result.setQunarOrderNum(order.getChannelOrderCode());
            result.setResult(ResultStatus.FAILURE.name());
            //异常返回失败
            this.exceptionOrderDao.insertExceptionOrder(order.getExceptionOrderListByOrder(order));
            //发送微信
            MessageCenterUtils.sendWeiXin("去哪儿预定异常，请联系相关人员，订单号：" + order.getChannelOrderCode());
        }
        return result;
    }

    @Override
    public QunarCancelOrderResponse cancelOrderMethod(String xml) throws Exception {
        QunarCancelOrderResponse result = new QunarCancelOrderResponse();
        //解析xml
        Order cancelOrderParam = QunarUtil.getCancelOrderParam(xml);
        OrderParamDto orderParamDto = this.orderDao.selectOrderById(cancelOrderParam.getId());
        if (null != orderParamDto) {
            //判断当前时间离入住时间是否超过48个小时
            if (DateUtil.getDifferDay(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), DateUtil.format(orderParamDto.getLiveTime(), "yyyy-MM-dd HH:mm:ss")) * 24 >= 48) {
                //如果大于48个小时离入住时间,同意退订
                //取消订单，同步
                JsonModel jsonModel = this.orderService.cancelOrderMethod(orderParamDto);
                if (jsonModel.isSuccess()) {
                    //取消成功成功
                    result.setResult(ResultStatus.SUCCESS.name());
                    result.setMsg("取消订单成功");
                    MessageCenterUtils.savePushTomsOrderLog(orderParamDto.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(orderParamDto.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), orderParamDto.getOrderStatus(), orderParamDto.getOrderStatus(), orderParamDto.getFeeStatus(), xml, JacksonUtil.obj2json(result), orderParamDto.getInnId(), orderParamDto.getInnCode(), "去哪儿取消订单返回值"));
                    //修改订单状态
                    orderParamDto.setReason(cancelOrderParam.getReason());
                    orderParamDto.setOrderStatus(OrderStatus.CANCEL_ORDER);
                    this.orderDao.updateOrderStatusAndReason(orderParamDto);
                } else {
                    result.setResult(ResultStatus.FAILURE.name());
                    result.setMsg("取消订单失败，酒店拒绝取消");
                    MessageCenterUtils.savePushTomsOrderLog(orderParamDto.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(orderParamDto.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), orderParamDto.getOrderStatus(), orderParamDto.getOrderStatus(), orderParamDto.getFeeStatus(), xml, JacksonUtil.obj2json(new JointWisdomAddOrderSuccessResponse().getBasicError("酒店拒绝取消订单", Version.v1003.getText(), OrderResponseType.Cancelled.name())), orderParamDto.getInnId(), orderParamDto.getInnCode(), "去哪儿取消订单返回值"));
                    OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectOtaInfoByType(OtaType.QUNAR.name());
                    OtaInfoRefDto otaInfo = this.otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(orderParamDto.getCompanyId(), otaInfoRefDto.getOtaInfoId());
                    //调用订单操作接口
                    String response = HttpClientUtil.httpPostQunarOrderOpt(CommonApi.qunarOrderOpt, cancelOrderParam.getChannelOrderCode(), OptCode.REFUSE_UNSUBSCRIBE.name(), otaInfo.getSessionKey(), BigDecimal.ZERO);
                    //判断返回是否失败，记录异常订单
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    if (!(Boolean) jsonObject.get("ret")) {
                        this.exceptionOrderDao.insertExceptionOrder(orderParamDto.getExceptionOrderListByOrder(orderParamDto));
                        //发送微信
                        MessageCenterUtils.sendWeiXin("去哪儿预定异常，请联系相关人员，订单号：" + orderParamDto.getChannelOrderCode());
                    }
                }
                result.setQunarOrderNum(cancelOrderParam.getChannelOrderCode());
                result.setOrderId(cancelOrderParam.getId());
            } else {
                //如果在48个小时之内，需要运营人员手动确定是否可以取消该订单
                //修改订单状态为待处理
                logger.info("去哪儿取消订单，此单已超出取消规则，订单号为：" + orderParamDto.getChannelOrderCode());
                orderParamDto.setOrderStatus(OrderStatus.PAY_BACK);
                orderParamDto.setReason(cancelOrderParam.getReason());
                this.orderDao.updateOrderStatusAndReason(orderParamDto);
                result.setMsg("正在处理中");
                result.setOrderId(cancelOrderParam.getId());
                result.setQunarOrderNum(cancelOrderParam.getChannelOrderCode());
                result.setResult(ResultStatus.PROCESSING.name());
            }
        } else {
            result.setResult(ResultStatus.FAILURE.name());
            result.setOrderId(cancelOrderParam.getId());
            result.setQunarOrderNum(cancelOrderParam.getChannelOrderCode());
            result.setMsg("没有此订单信息");
        }
        return result;
    }

    @Override
    public QunarWrapperOrderQueryResponse queryOrderStatus(String xml) {
        QunarWrapperOrderQueryResponse result = new QunarWrapperOrderQueryResponse();
        try {
            //解析xml
            Order orderParam = QunarUtil.getQueryOrder(xml);
            Order order = this.orderDao.selectOrderByChannelOrderCodeAndSource(orderParam);
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.SEARCH_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, null, order.getInnId(), order.getInnCode(), "去哪儿查询订单传入参数"));
            if (null == order) {
                return result;
            } else {
                //获取订单状态
                String respose = this.orderService.getOrderStatusMethod(order);
                JSONObject jsonObject = JSONObject.parseObject(respose);
                if (!jsonObject.get("status").equals(200)) {
                    return result;
                } else {
                    OrderInfo orderInfo = new OrderInfo();
                    if (jsonObject.get("orderStatus").equals("0")) {
                        orderInfo.setStatus(QunarOrderStatus.NEW_ORDER.name());
                    } else if (jsonObject.get("orderStatus").equals("1")) {
                        orderInfo.setStatus(QunarOrderStatus.CONFIRMED_SUCCESS.name());

                    } else if (jsonObject.get("orderStatus").equals("2")) {
                        orderInfo.setStatus(QunarOrderStatus.CONFIRMED_FAILURE.name());

                    } else if (jsonObject.get("orderStatus").equals("3")) {
                        orderInfo.setStatus(QunarOrderStatus.CANCELED.name());

                    } else if ((jsonObject.get("orderStatus").equals("4"))) {
                        orderInfo.setStatus(QunarOrderStatus.CONFIRMED_FAILURE.name());

                    } else {
                        throw new TomsRuntimeException("OMS内部错误");
                    }
                    //组装返回参数
                    orderInfo.setOrderId(order.getId());
                    orderInfo.setOrderNum(order.getChannelOrderCode());
                    orderInfo.setPayType(order.getPaymentType().name());
                    QunarUtil.dealQueryOrder(orderInfo, order.getXmlData());
                    result.setOrderInfo(orderInfo);
                }
            }
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.SEARCH_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, result.toString(), order.getInnId(), order.getInnCode(), "去哪儿查询订单，toms返回值"));
        } catch (Exception e) {
            logger.info("去哪儿查询订单异常" + e);
            return result;
        }
        return result;
    }
}
