package com.fanqielaile.toms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DcUtil;
import com.fanqielaile.toms.bo.ctrip.homestay.ChildOrder;
import com.fanqielaile.toms.bo.ctrip.homestay.OmsOrder;
import com.fanqielaile.toms.bo.ctrip.homestay.OrderPerson;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.exception.CtripHomeStayConnException;
import com.fanqielaile.toms.helper.InnRoomHelper;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.model.Dictionary;
import com.fanqielaile.toms.service.ICtripHomeStayConnService;
import com.fanqielaile.toms.service.ITPService;
import com.fanqielaile.toms.support.holder.TPHolder;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.HttpClientUtil;
import com.fanqielaile.toms.support.util.JodaTimeUtil;
import com.fanqielaile.toms.support.util.JsonModel;
import com.fanqielaile.toms.util.CommonUtil;
import com.fanqielaile.toms.util.PassWordUtil;
import com.fanqielaile.toms.vo.ctrip.homestay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Create by jame
 * Date: 2016/9/6 10:27
 * Version: 1.0
 * Description: 阐述
 */
@Service
public class CtripHomeStayConnServiceImpl implements ICtripHomeStayConnService, ITPService {

    @Resource
    private TPHolder tpHolder;

    @Resource
    private CompanyDao companyDao;

    @Autowired
    private ICtripHomeStayRoomRefDao ctripHomeStayRoomRefDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    DictionaryDao dictionaryDao;

    private static final String HOMESTAY_COMPANY_CODE = "45470283";
    private static final String USERCODE = "XCMS";
    private static final String PASSWORD = "XCMS654";

    private static final Logger logger = LoggerFactory.getLogger(CtripHomeStayConnServiceImpl.class);

    @Override
    public SubmitOrderReturnVo submitOrder(SubmitOrderRequestVo submitOrderParamVo) {
        logger.info("携程民宿，提交订单请求，参数：" + JSON.toJSONString(submitOrderParamVo));
        SubmitOrderReturnVo submitOrderReturnVo = new SubmitOrderReturnVo();
        Order tomsOrder = new Order();
        try {
            Integer accountId, innId;
            String roomTypeName;
            try {
                CtripHomeStayRoomRef ctripHomeStayRoomRef = new CtripHomeStayRoomRef();
                ctripHomeStayRoomRef.setOtaRoomTypeId(Integer.parseInt(String.valueOf(submitOrderParamVo.getRoomId())));
                List<CtripHomeStayRoomRef> list = ctripHomeStayRoomRefDao.query(ctripHomeStayRoomRef);//通过otaRoomTypeId查找accounId
                accountId = list.get(0).getAccountId();
                innId = list.get(0).getInnId();
                roomTypeName = list.get(0).getRoomTypeName();
            } catch (Exception e) {
                throw new CtripHomeStayConnException("该房型对应的客栈没有设置携程民宿渠道，请联系管理员");
            }
            tomsOrder = getTomsOrder(submitOrderParamVo, accountId, innId, roomTypeName);
            OmsOrder omsOrder = convertOrderModel(submitOrderParamVo, accountId);
            Map param = new HashMap();
            param.put("order", JSON.toJSONString(omsOrder));
            param.put("timestamp", submitOrderParamVo.getTimestamp());
            param.put("otaId", submitOrderParamVo.getOtaId());
            param.put("signature", submitOrderParamVo.getSignature());
            // 查询调用的url
            Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.CREATE_ORDER.name());
            logger.debug("请求oms下单URL：" + dictionary.getUrl());
            logger.debug("请求oms下单参数：" + JSON.toJSONString(param));
            String result = HttpClientUtil.httpKvPost(dictionary.getUrl(), param);
            logger.debug("请求oms下单返回值：" + result);

            com.fanqielaile.toms.vo.oms.SubmitOrderReturnVo omsVo = JSON.parseObject(result, com.fanqielaile.toms.vo.oms.SubmitOrderReturnVo.class);
            if (omsVo.getStatus().equals(Integer.parseInt(Constants.SUCCESS200))) {
                submitOrderReturnVo.setStatusId(1);
                submitOrderReturnVo.setOrderId(Long.parseLong(omsVo.getOrderNo()));
                submitOrderReturnVo.setResultCode(CtripHomeStayResultCodeEnum.SUCCESS.getValue());
                tomsOrder.setOmsOrderCode(String.valueOf(submitOrderReturnVo.getOrderId()));
                tomsOrder.setOrderStatus(OrderStatus.CONFIM_AND_ORDER);
            } else {
                submitOrderReturnVo.setResultCode(CtripHomeStayResultCodeEnum.SYSTEM_ERROR.getValue());
                submitOrderReturnVo.setResultMessage(omsVo.getMessage());
                tomsOrder.setOrderStatus(OrderStatus.REFUSE);
                tomsOrder.setReason(omsVo.getMessage());
            }
        } catch (Exception e) {
            submitOrderReturnVo.setResultCode(CtripHomeStayResultCodeEnum.SYSTEM_ERROR.getValue());
            submitOrderReturnVo.setResultMessage("提交订单异常，" + e.getMessage());
            tomsOrder.setReason(submitOrderReturnVo.getResultMessage());
            tomsOrder.setOrderStatus(OrderStatus.REFUSE);
            logger.error(submitOrderReturnVo.getResultMessage(), e);
        }
        tomsOrder.setId(tomsOrder.getUuid());
        orderDao.insertOrder(tomsOrder);
        return submitOrderReturnVo;
    }

    private Order getTomsOrder(SubmitOrderRequestVo submitOrderParamVo, Integer accountId, Integer innId, String roomTypeName) {
        Order tomsOrder = new Order();
        try {
            tomsOrder.setVersion(0);
            tomsOrder.setCreatedDate(new Date());
            tomsOrder.setDeleted(0);
            tomsOrder.setChannelSource(ChannelSource.CTRIP_HOMESTAY);
            tomsOrder.setChannelOrderCode(String.valueOf(submitOrderParamVo.getCtripOrderId()));
            tomsOrder.setAccountId(accountId);
            tomsOrder.setInnId(innId);
            tomsOrder.setRoomTypeId(String.valueOf(submitOrderParamVo.getRoomId()));
            tomsOrder.setHomeAmount(submitOrderParamVo.getQuantity());
            tomsOrder.setLiveTime(JodaTimeUtil.parse(submitOrderParamVo.getCheckIn()));
            tomsOrder.setLeaveTime(JodaTimeUtil.parse(submitOrderParamVo.getCheckOut()));
            tomsOrder.setTotalPrice(BigDecimal.valueOf(submitOrderParamVo.getTotalAmount()).divide(new BigDecimal(100)));
            tomsOrder.setOrderTime(new Date());
            tomsOrder.setOTARoomTypeId(String.valueOf(submitOrderParamVo.getRoomId()));
            tomsOrder.setCurrency(CurrencyCode.CNY);
            tomsOrder.setFeeStatus(FeeStatus.PAID);
            if (submitOrderParamVo.getDeposit() != null) {
                tomsOrder.setPaymentType(submitOrderParamVo.getDeposit().getType() == 1 ? PaymentType.PREPAY : PaymentType.NOW_PAY);
            }
            if (CommonUtil.isListNotEmpty(submitOrderParamVo.getGuests())) {
                List<SubmitOrderGuestsVo> guests = submitOrderParamVo.getGuests();
                String guestName = "";
                for (SubmitOrderGuestsVo guest : guests) {
                    guestName += guest.getName() + ",";
                }
                guestName = CommonUtil.substrLastPatten(guestName);
                tomsOrder.setGuestName(guestName);
                tomsOrder.setPerson(submitOrderParamVo.getGuests().size());
            }
            tomsOrder.setPayment(BigDecimal.valueOf(submitOrderParamVo.getTotalAmount()).divide(new BigDecimal(100)));
            Company company = new Company();
            company.setUserAccount(USERCODE);
            company.setUserPassword(PASSWORD);
            company = companyDao.selectByUser(company);
            tomsOrder.setCompanyId(company.getId());
            tomsOrder.setOrderCode(OrderMethodHelper.getOrderCode());
            tomsOrder.setUsedPriceModel(UsedPriceModel.MAI);
            tomsOrder.setBasicTotalPrice(BigDecimal.valueOf(submitOrderParamVo.getTotalAmount()).divide(new BigDecimal(100)));
//        tomsOrder.setOrderInnName //
            tomsOrder.setRoomTypeName(roomTypeName);
            tomsOrder.setJsonData(JSON.toJSONString(submitOrderParamVo));
            tomsOrder.setOrderSource(OrderSource.SYSTEM);
        } catch (Exception e) {
            logger.error("提交订单toms对象转换异常，method=getTomsOrder", e);
            throw new CtripHomeStayConnException("提交订单toms对象转换异常，请检查参数是否正确", e);
        }
        return tomsOrder;
    }


    private OmsOrder convertOrderModel(SubmitOrderRequestVo submitOrderParamVo, Integer accountId) throws CtripHomeStayConnException {
        OmsOrder omsOrder = new OmsOrder();
        try {
            omsOrder.setAccountId(accountId);
            omsOrder.setOtaOrderNo(String.valueOf(submitOrderParamVo.getCtripOrderId()));
            omsOrder.setRoomTypeNum(submitOrderParamVo.getQuantity());
            omsOrder.setTotalPrice((BigDecimal.valueOf(submitOrderParamVo.getTotalAmount()).divide(new BigDecimal(100))).doubleValue());
            omsOrder.setPaidAmount(BigDecimal.valueOf(submitOrderParamVo.getOnlineAmount()).divide(new BigDecimal(100)).doubleValue());
            omsOrder.setContact(submitOrderParamVo.getContacts().getMobile());
            omsOrder.setUserName(submitOrderParamVo.getContacts().getName());
            omsOrder.setOtaId(EnumOta.ctrip_homestay.getValue());

            List<ChildOrder> childOrders = new ArrayList<>();
            ChildOrder childOrder = new ChildOrder();
            childOrder.setRoomTypeId(Integer.parseInt(String.valueOf(submitOrderParamVo.getRoomId())));
            childOrder.setCheckInAt(submitOrderParamVo.getCheckIn());
            childOrder.setCheckOutAt(submitOrderParamVo.getCheckOut());
            childOrder.setBookRoomPrice(0d);
            childOrders.add(childOrder);
            omsOrder.setChildOrders(childOrders);

            List<OrderPerson> orderPersons = new ArrayList<>();
            List<SubmitOrderGuestsVo> guestsVoList = submitOrderParamVo.getGuests();
            for (SubmitOrderGuestsVo submitOrderGuestsVo : guestsVoList) {
                OrderPerson orderPerson = new OrderPerson();
                orderPerson.setName(submitOrderGuestsVo.getName());
                orderPerson.setCardNo(submitOrderGuestsVo.getIdCode());
                if (submitOrderGuestsVo.getIdType() != 0) {
                    //（1=身份证 2=军官证 3=通行证 4=护 照 5=其他）    番茄idType
                    //1.身份证;2.军人证;3.护照;        携程idType
                    int idType = submitOrderGuestsVo.getIdType() == 3 ? 4 : submitOrderGuestsVo.getIdType();
                    orderPerson.setCardType(idType);
                }
                orderPersons.add(orderPerson);
            }
            omsOrder.setPersons(orderPersons);
            return omsOrder;
        } catch (Exception e) {
            logger.error("提交订单对象转换异常，method=convertOrderModel", e);
            throw new CtripHomeStayConnException("提交订单对象转换异常，请检查参数是否正确", e);
        }
    }


    @Override
    public GetOrderReturnVo getOrder(Map map) {
        logger.info("携程民宿，查询订单请求，参数：" + JSON.toJSONString(map));
        GetOrderReturnVo getOrderReturnVo = new GetOrderReturnVo();
        try {
            List<Object> orderIDs;
            if (map == null || map.get("orderIDs") == null) {
                logger.error("查询订单，参数错误，method=getOrder");
                throw new CtripHomeStayConnException("查询订单，参数错误");
            } else {
                orderIDs = (List) map.get("orderIDs");
                List<GetOrderDetailVo> orderDetailVos = new ArrayList<>();
                for (Object orderID : orderIDs) {
                    GetOrderDetailVo getOrderDetailVo = new GetOrderDetailVo();
                    Order order = orderDao.selectOrderByOmsOrderCodeAndSource(ChannelSource.CTRIP_HOMESTAY.name(), String.valueOf(orderID));
                    getOrderDetailVo.setCreateTime(JodaTimeUtil.format(order.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
                    SubmitOrderRequestVo submitOrderParamVo = JSON.parseObject(order.getJsonData(), new TypeReference<SubmitOrderRequestVo>() {
                    });
                    getOrderDetailVo.setOfflinePayment(submitOrderParamVo.getOfflineAmount());
                    getOrderDetailVo.setOnlinePayment(submitOrderParamVo.getOnlineAmount());
                    getOrderDetailVo.setOrderId(Long.valueOf(orderID.toString()));
                    getOrderDetailVo.setTotalAmount(submitOrderParamVo.getTotalAmount());
                    if (order.getOrderStatus().name().equals(OrderStatus.CONFIM_AND_ORDER.name())) {
                        getOrderDetailVo.setStatusId(22);//预定已确认
                    } else if (order.getOrderStatus().name().equals(OrderStatus.CANCEL_ORDER.name())) {
                        getOrderDetailVo.setStatusId(31);//预定已取消
                    } else {//已拒绝
                        getOrderDetailVo.setStatusId(23);
                    }
                    orderDetailVos.add(getOrderDetailVo);
                }
                getOrderReturnVo.setOrders(orderDetailVos);
                getOrderReturnVo.setResultCode(CtripHomeStayResultCodeEnum.SUCCESS.getValue());
            }
        } catch (Exception e) {
            logger.error("查询订单异常，method=getOrder", e);
            getOrderReturnVo.setResultCode(CtripHomeStayResultCodeEnum.SYSTEM_ERROR.getValue());
            getOrderReturnVo.setResultMessage("查询订单异常，" + e.getMessage());
        }
        return getOrderReturnVo;
    }


    @Override
    public CancelOrderReturnVo cancelOrder(CancelOrderRequestVo cancelOrderRequestVo) {
        logger.info("携程民宿，取消订单请求，参数：" + JSON.toJSONString(cancelOrderRequestVo));
        CancelOrderReturnVo cancelOrderReturnVo = new CancelOrderReturnVo();
        try {
            Order order = orderDao.selectOrderByOmsOrderCodeAndSource(ChannelSource.CTRIP_HOMESTAY.name(), String.valueOf(cancelOrderRequestVo.getOrderId()));
            if (order == null) {
                throw new CtripHomeStayConnException("不存在此渠道订单id：" + cancelOrderRequestVo.getOrderId());
            }
            CancelOrderRefundVo cancelOrderRefundVo = new CancelOrderRefundVo();
            cancelOrderRefundVo.setAmount(order.getTotalPrice().multiply(BigDecimal.valueOf(100)).intValue());
            if (cancelOrderRequestVo.getCancelType() == 1) { //1.检查是否可以取消
                if (order.getOrderStatus().name().equals(OrderStatus.CONFIM_AND_ORDER.name())) {
                    cancelOrderReturnVo.setStatusId(2); //可取消
                    cancelOrderRefundVo.setDesc("此订单可退线上支付的金额。");
                    cancelOrderReturnVo.setRefund(cancelOrderRefundVo);
                } else {
                    cancelOrderReturnVo.setStatusId(1); //不可取消
                }
            } else if (cancelOrderRequestVo.getCancelType() == 2) { //2.取消订单
                if (order.getOrderStatus().name().equals(OrderStatus.CONFIM_AND_ORDER.name())) {
                    OrderParamDto orderParamDto = orderService.findOrderById(order.getId());
                    JsonModel jsonModel = this.orderService.cancelHandOrder(orderParamDto);
                    logger.debug("处理取消订单，返回值：" + JSON.toJSONString(jsonModel));
                    if (jsonModel.isSuccess()) {
                        cancelOrderReturnVo.setStatusId(2);
                        cancelOrderRefundVo.setDesc("订单退款结果：" + jsonModel.getMessage());
                        cancelOrderReturnVo.setRefund(cancelOrderRefundVo);
                    } else {
                        cancelOrderReturnVo.setStatusId(1);
                        cancelOrderReturnVo.setResultMessage(jsonModel.getMessage());
                    }
                } else {
                    cancelOrderReturnVo.setStatusId(1); //不可取消
                }
            }
            cancelOrderReturnVo.setResultCode(CtripHomeStayResultCodeEnum.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("取消订单异常，method=getOrder", e);
            cancelOrderReturnVo.setResultCode(CtripHomeStayResultCodeEnum.SYSTEM_ERROR.getValue());
            cancelOrderReturnVo.setResultMessage("取消订单异常，" + e.getMessage());
        }
        return cancelOrderReturnVo;
    }

    @Override
    public void updateOrAddHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {
        String innId = tbParam.getInnId();
        Company company = companyDao.selectCompanyByCompanyCode(tbParam.getCompanyCode());
        tpHolder.validate(company, innId, otaInfo.getOtaInfoId());
        if (tbParam.isSj()) {//上架，新增数据
            tbParam.setOtaId(String.valueOf(company.getOtaId()));
            logger.debug("请求oms查询房型URL：" + CommonApi.getRoomType());
            Map param = new HashMap();
            param.put("accountId", tbParam.getAccountId());
            param.put("otaId", tbParam.getOtaId());
            Long time = System.currentTimeMillis();
            param.put("timestamp", time);
            String signature = tbParam.getOtaId() + time + USERCODE + PASSWORD;
            signature = PassWordUtil.getMd5Pwd(signature);
            param.put("signature", signature);
            logger.debug("请求oms查询房型参数：" + JSON.toJSONString(param));
            String result = HttpClientUtil.httpKvPost(CommonApi.getRoomType(), param);
            Map resultMap = JSON.parseObject(result, Map.class);
            List<Map> listRooms = (List<Map>) resultMap.get("list");
            List<CtripHomeStayRoomRef> inserList = new ArrayList<>();
            for (Map room : listRooms) {
                CtripHomeStayRoomRef ctripHomeStayRoomRef = new CtripHomeStayRoomRef();
                ctripHomeStayRoomRef.setAccountId(Integer.parseInt(tbParam.getAccountId()));
                ctripHomeStayRoomRef.setOtaId(Integer.parseInt(tbParam.getOtaId()));
                ctripHomeStayRoomRef.setDeleted(0);
                ctripHomeStayRoomRef.setInnId(Integer.parseInt(tbParam.getInnId()));
                ctripHomeStayRoomRef.setOtaRoomTypeId(Integer.parseInt(room.get("roomTypeId").toString()));
                ctripHomeStayRoomRef.setRoomTypeName(room.get("roomTypeName").toString());
                inserList.add(ctripHomeStayRoomRef);
            }
            ctripHomeStayRoomRefDao.insertAll(inserList);
        } else {//下架，删除数据
            CtripHomeStayRoomRef ctripHomeStayRoomRef = new CtripHomeStayRoomRef();
            ctripHomeStayRoomRef.setAccountId(Integer.parseInt(tbParam.getAccountId()));
            ctripHomeStayRoomRefDao.deleteByAccountId(ctripHomeStayRoomRef);
        }
//
//        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), Integer.parseInt(innId));
//        tpHolder.validate(company, tbParam.getInnId(), otaInfo.getOtaInfoId());
//        tbParam.setOtaId(String.valueOf(company.getOtaId()));
//        String inn_info = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), tbParam.getAccountId() != null ? tbParam.getAccountId() : tbParam.getAccountIdDi(), CommonApi.INN_INFO);
//        logger.info("inn url:" + inn_info);
//        OtaInnOtaDto otaInnOta = otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(Integer.parseInt(tbParam.getInnId()), company.getId(), otaInfo.getOtaInfoId());
//        InnDto omsInnDto = InnRoomHelper.getInnInfo(inn_info);
//        //未绑定
//        BangInnDto bangInnDto = null;
//        if (bangInn == null) {
//            bangInnDto = BangInnDto.toDto(company.getId(), tbParam, omsInnDto);
//            bangInnDao.createBangInn(bangInnDto);
//        } else {
//            BangInnDto.toUpdateDto(bangInn, tbParam, omsInnDto);
//            bangInnDao.updateBangInnTp(bangInn);
//        }
//        if (otaInnOta != null) {
//            otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
//            otaInnOtaDao.updateOtaInnOta(otaInnOta);
//        } else {
//            String hid = tbParam.getOtaId().toString() + tbParam.getInnId().toString();
//            otaInnOta = OtaInnOtaDto.toDto(hid, omsInnDto.getInnName(), company.getId(), tbParam, bangInn == null ? bangInnDto.getId() : bangInn.getId(), otaInfo.getOtaInfoId());
//            otaInnOtaDao.saveOtaInnOta(otaInnOta);
//        }
    }

    @Override
    public void deleteHotel(TBParam tbParam, OtaInfoRefDto otaInfo) throws Exception {

    }

    @Override
    public void updateHotel(OtaInfoRefDto infoRefDto, TBParam tbParam) throws Exception {

    }

    @Override
    public void updateHotelRoom(OtaInfoRefDto infoRefDto, List<PushRoom> pushRoomList) throws Exception {

    }

    @Override
    public void updateHotelFailTimer(OtaInfoRefDto infoRefDto) {

    }

    @Override
    public void updateRoomTypePrice(OtaInfoRefDto infoRefDto, String innId, String companyId, String userId, String json) throws Exception {

    }

    @Override
    public Result validatedOTAAccuracy(OtaInfoRefDto infoRefDto) {
        return null;
    }

    @Override
    public void sellingRoomType(String from, String to, OtaInfoRefDto otaInfoRefDto) {

    }

}
