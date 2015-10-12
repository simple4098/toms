package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.OrderSource;
import com.fanqie.core.dto.OrderSourceDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.DateUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.fc.CancelHotelOrderResponse;
import com.fanqielaile.toms.dto.fc.CheckRoomAvailResponse;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.dto.fc.GetOrderStatusResponse;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.model.Dictionary;
import com.fanqielaile.toms.model.fc.FcRoomTypeInfo;
import com.fanqielaile.toms.model.fc.SaleItem;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.service.IRoomTypeService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.*;
import com.fanqielaile.toms.support.util.ftp.FTPUtil;
import com.fanqielaile.toms.support.util.ftp.UploadStatus;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.taobao.api.ApiException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/5/29
 * @version: v1.0.0
 */
@Service
//@LogModule("订单Service:OrderService")
public class OrderService implements IOrderService {
    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

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
    /*@Resource
    private BusinLogClient businLogClient;
    private BusinLog businLog = new BusinLog();*/
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
    private IFcHotelInfoDao fcHotelInfoDao;
    @Resource
    private IFcRoomTypeInfoDao fcRoomTypeInfoDao;
    @Resource
    private IFcRoomTypeFqDao fcRoomTypeFqDao;
    @Override
    public Map<String, Object> findOrderSourceDetail(ParamDto paramDto, UserInfo userInfo) throws Exception {
        paramDto.setUserId(userInfo.getId());
        paramDto.setCompanyId(userInfo.getCompanyId());
        String order = HttpClientUtil.httpGets(CommonApi.ORDER, paramDto);
        JSONObject jsonObject = JSONObject.fromObject(order);
        List<OrderDto> data = new ArrayList<OrderDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        Object rows = jsonObject.get("rows");
        Object obj = jsonObject.get("obj");
        OrderSourceDto orderSource = null;
        if (obj != null) {
            orderSource = JacksonUtil.json2obj(obj.toString(), OrderSourceDto.class);
        }
        if (rows != null) {
            List<OrderSource> list = JacksonUtil.json2list(rows.toString(), OrderSource.class);
            OrderDto orderDto = null;
            for (OrderSource o : list) {
                orderDto = new OrderDto();
                orderDto.setValue(o.getIncome());
                orderDto.setName(o.getFromName());
                data.add(orderDto);
            }
            map.put("data", data);
            map.put("list", list);
            map.put("orderSource", orderSource);
            return map;
        }
        return null;
    }




    @Override
//    @Log(descr = "创建订单")
    public Order addOrder(String xmlStr, ChannelSource channelSource) throws Exception {
        String logStr = "创建订单传递参数=>" + xmlStr;
        //解析xml
        Element dealXmlStr = XmlDeal.dealXmlStr(xmlStr);
        //转换成对象只针对淘宝传递的参数
        Order order = OrderMethodHelper.getOrder(dealXmlStr);
        //创建订单
        createOrderMethod(channelSource, order);
      /*  //保存日志
        businLog.setDescr(logStr + order.toString());
        businLogClient.save(businLog);*/
        return order;
    }

    /**
     * 创建订单
     *
     * @param channelSource
     * @param order
     */
    private void createOrderMethod(ChannelSource channelSource, Order order) {
        OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
        OtaRoomPriceDto otaRoomPriceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(otaInnOtaDto.getCompanyId(), Integer.parseInt(order.getRoomTypeId()), otaInnOtaDto.getOtaInfoId()));
        //设置每日价格
        if (ArrayUtils.isNotEmpty(order.getDailyInfoses().toArray())) {
            if (otaRoomPriceDto == null) {
                for (DailyInfos dailyInfos : order.getDailyInfoses()) {
                    dailyInfos.setPrice(dailyInfos.getPrice().divide(otaInnOtaDto.getPriceModelValue(), 2, BigDecimal.ROUND_UP));
                }
            } else {
                //执行特殊价加减流程
                //1.判断入住时间是否在特殊价日期之间
                for (DailyInfos dailyInfos : order.getDailyInfoses()) {
                    if (DateUtil.isBetween(dailyInfos.getDay(), otaRoomPriceDto.getStartDate(), otaRoomPriceDto.getEndDate())) {
                        dailyInfos.setPrice(dailyInfos.getPrice().subtract(new BigDecimal(Double.toString(otaRoomPriceDto.getValue()))));
                    } else {
                        dailyInfos.setPrice(dailyInfos.getPrice().divide(otaInnOtaDto.getPriceModelValue(), 2, BigDecimal.ROUND_UP));
                    }
                }
            }
        }
        //设置渠道来源
        order.setChannelSource(channelSource);
        order.setOrderTime(new Date());
        //设置订单总价
        order.setTotalPrice(OrderMethodHelper.getTotalPrice(order));
        //设置订单号
        order.setOrderCode(OrderMethodHelper.getOrderCode());
        //算成本价与OTA收益
        order.setCostPrice(order.getTotalPrice());
        //不展示ota佣金
//        order.setOTAPrice(order.getTotalPrice().multiply(otaInnOtaDto.getPriceModelValue()).subtract(order.getTotalPrice()));
        order.setCompanyId(otaInnOtaDto.getCompanyId());
        //创建订单
        this.orderDao.insertOrder(order);
        //创建每日价格信息
        this.dailyInfosDao.insertDailyInfos(order);
        //创建入住人信息
        this.orderGuestsDao.insertOrderGuests(order);
    }

    @Override
//    @Log(descr = "取消订单")
    public JsonModel cancelOrder(String xmlStr, ChannelSource channelSource) throws Exception {
        logger.info("取消订单传入xml=》" + xmlStr);
        //解析取消订单的xml
        String orderId = XmlDeal.getOrder(xmlStr).getId();
        //验证此订单是否存在
        Order order = orderDao.selectOrderByIdAndChannelSource(orderId, channelSource);
        if (null == order) {
            return new JsonModel(false, "订单不存在");
        }
        order.setReason(XmlDeal.getOrder(xmlStr).getReason());
        return cancelOrderMethod(order);
    }

    /**
     * 取消订单
     *
     * @param order
     * @return
     * @throws Exception
     */
    private JsonModel cancelOrderMethod(Order order) throws Exception {
        order.setOrderStatus(OrderStatus.CANCEL_ORDER);
        //判断订单是否需要同步OMS,条件根据订单是否付款
        if (!order.getFeeStatus().equals(FeeStatus.NOT_PAY) || ChannelSource.FC.equals(order.getChannelSource())) {
            // 查询调用的url
            Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.CANCEL_ORDER.name());
            if (null != dictionary) {
                //发送请求
                logger.info("oms取消订单传递参数=>" + order.toCancelOrderParam(order, dictionary).toString());
                String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, dictionary));
                logger.info("调用OMS取消订单的返回值=>" + respose.toString());
                JSONObject jsonObject = JSONObject.fromObject(respose);
                if (!jsonObject.get("status").equals(200)) {
                    return new JsonModel(false, jsonObject.get("status").toString() + ":" + jsonObject.get("message"));
                } else {
                    //同步成功后在修改数据库
                    this.orderDao.updateOrderStatusAndReason(order);
                    //写入操作记录
                    this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.CANCEL_ORDER, "取消订单接口", ChannelSource.TAOBAO.name()));
                }
            }
        } else {
            this.orderDao.updateOrderStatusAndReason(order);
        }
//        businLog.setDescr(logStr + "取消的订单信息=>" + order.toString());
//        //保存日志
//        businLogClient.save(businLog);
        return new JsonModel(true, "取消订单成功");
    }

    @Override
//    @Log(descr = "付款成功回调")
    public JsonModel paymentSuccessCallBack(String xmlStr, ChannelSource channelSource) throws Exception {
        try {
            //日志
            String logStr = "付款成功回调传递参数" + xmlStr;
//        businLog.setDescr(logStr);
//        businLogClient.save(businLog);
            Order orderXml = XmlDeal.getOrder(xmlStr);
            //获取订单号，判断订单是否存在
            Order order = this.orderDao.selectOrderByIdAndChannelSource(orderXml.getId(), channelSource);
            //付款金额
            order.setPayment(orderXml.getPayment());
            //支付宝付款码
            order.setAlipayTradeNo(orderXml.getAlipayTradeNo());
            //1.判断当前订单客栈属于哪个公司，查找公司设置的下单规则
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), OtaType.TB.name());
            OrderConfig orderConfig = new OrderConfig(otaInfo.getOtaInfoId(), order.getCompanyId(), Integer.valueOf(order.getInnId()));
            OrderConfigDto orderConfigDto = orderConfigDao.selectOrderConfigByOtaInfoId(orderConfig);
            if (null == orderConfigDto || 0 == orderConfigDto.getStatus()) {
                //自动下单
                //设置订单状态为：接受
                order.setOrderStatus(OrderStatus.ACCEPT);
                return payBackDealMethod(order, new UserInfo(), OtaType.TB.name());
            } else {
                //手动下单,手动下单修改订单状态为待处理
                order.setOrderStatus(OrderStatus.NOT_DEAL);
                //待处理订单写入付款金额和付款码
                order.setFeeStatus(FeeStatus.PAID);
                this.orderDao.updateOrderStatusAndFeeStatus(order);
                this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.NOT_DEAL, "手动下单", ChannelSource.TAOBAO.name()));
                return new JsonModel(true, "付款成功");
            }
        } catch (Exception e) {
            return new JsonModel(false, "系统内部错误");
        }
    }

    /**
     * 付款成功回调的处理方法
     *
     * @param order
     * @return
     * @throws Exception
     */
    private JsonModel payBackDealMethod(Order order, UserInfo currentUser, String otaType) throws Exception {
        // 房态更新时间
        if (StringUtils.isNotEmpty(order.getOTAGid())) {
            OtaInnRoomTypeGoodsDto roomTypeGoodsDto = this.otaInnRoomTypeGoodsDao.findRoomTypeByRid(Long.parseLong(order.getOTAGid()));
            if (null != roomTypeGoodsDto) {
                order.setOrderCreateTime(roomTypeGoodsDto.getProductDate());
            }
        }
        //获取入住人信息
        List<OrderGuests> orderGuestses = this.orderGuestsDao.selectOrderGuestByOrderId(order.getId());
        order.setOrderGuestses(orderGuestses);
        if (null == order) {
            return new JsonModel(false, "订单不存在");
        }
        //获取每日房价信息
        List<DailyInfos> dailyInfoses = this.dailyInfosDao.selectDailyInfoByOrderId(order.getId());
        order.setDailyInfoses(dailyInfoses);
        //判断订单是否同步OMS
        if (order.getFeeStatus().equals(FeeStatus.NOT_PAY) || order.getOrderStatus().equals(OrderStatus.CONFIM_AND_ORDER)) {
            //查询字典表中同步OMS需要的数据
            Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.CREATE_ORDER.name());
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), otaType);
            //查询客栈信息
            BangInnDto bangInn = this.bangInnDao.selectBangInnByTBHotelId(order.getOTAHotelId(),otaInfo.getOtaInfoId(),order.getCompanyId());
            if (null == bangInn) {
                logger.info("绑定客栈不存在" + order.getOTAHotelId());
                return new JsonModel(false, "绑定客栈不存在");
            }
            //查询当前酒店以什么模式发布
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
            if (otaInnOtaDto.getsJiaModel().equals("MAI")) {
                order.setAccountId(bangInn.getAccountId());
            } else {
                order.setAccountId(bangInn.getAccountIdDi());
            }
            if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
                OtaBangInnRoomDto otaBangInnRoomDtos = this.bangInnRoomDao.selectBangInnRoomByInnIdAndRoomTypeId(order.getInnId(), Integer.parseInt(order.getRoomTypeId()), order.getCompanyId());
                if (null == otaBangInnRoomDtos) {
                    return new JsonModel(false, "房型不存在");
                }
                order.setRoomTypeName(otaBangInnRoomDtos.getRoomTypeName());
            } else if (ChannelSource.FC.equals(order.getChannelSource())) {
                OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), OtaType.FC.name());
                FcRoomTypeFqDto roomTypeFqInnIdRoomIdOtaInfoId = this.fcRoomTypeFqDao.findRoomTypeFqInnIdRoomIdOtaInfoId(order.getInnId(), Integer.parseInt(order.getRoomTypeId()), otaInfoRefDto.getOtaInfoId(), order.getCompanyId());
                if (null == roomTypeFqInnIdRoomIdOtaInfoId) {
                    return new JsonModel(false, "房型不存在");
                } else {
                    order.setRoomTypeName(roomTypeFqInnIdRoomIdOtaInfoId.getFqRoomTypeName());
                }
            }
            logger.info("OMS接口传递参数=>" + order.toOrderParamDto(order, dictionary).toString());
            String respose = "";
            JSONObject jsonObject = null;
            try {
                respose = HttpClientUtil.httpPostOrder(dictionary.getUrl(), order.toOrderParamDto(order, dictionary));
                jsonObject = JSONObject.fromObject(respose);
            } catch (Exception e) {
                order.setOrderStatus(OrderStatus.REFUSE);
                order.setFeeStatus(FeeStatus.PAID);
                if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
                    String result = TBXHotelUtil.orderUpdate(order, otaInfo, 1L);
                    logger.info("淘宝取消订单接口返回值=>" + result);
                    if (null != result && result.equals("success")) {
                        this.orderDao.updateOrderStatusAndFeeStatus(order);
                        //记录操作日志
                        this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.REFUSE, "淘宝下单到oms响应失败", null == currentUser.getId() ? ChannelSource.TAOBAO.name() : currentUser.getId()));
                    }
                    return new JsonModel(false, "OMS系统异常");
                } else if (ChannelSource.FC.equals(order.getChannelSource())) {
                    //如果付款失败，天下房仓返回创建订单失败
                    return new JsonModel(false, "OMS系统异常，创建订单失败");
                }
            }
            logger.info("OMS接口响应=>" + respose);
            if (!jsonObject.get("status").equals(200)) {
                order.setOrderStatus(OrderStatus.REFUSE);
                order.setFeeStatus(FeeStatus.NOT_PAY);
                if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
                    String result = TBXHotelUtil.orderUpdate(order, otaInfo, 1L);
                    logger.info("淘宝取消订单接口返回值=>" + result);
                    if (null != result && result.equals("success")) {
                        this.orderDao.updateOrderStatusAndFeeStatus(order);
                        //记录操作日志
                        this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.REFUSE, "淘宝下单到oms响应失败", currentUser.getId()));
                    }
                    return new JsonModel(false, jsonObject.get("status") + ":" + jsonObject.get("message"));
                } else if (ChannelSource.FC.equals(order.getChannelSource())) {
                    //如果付款失败，天下房仓返回创建订单失败
                    return new JsonModel(false, jsonObject.get("status") + ":" + jsonObject.get("message"));
                }
            } else {
                if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
                    //更新淘宝订单状态
                    String result = TBXHotelUtil.orderUpdate(order, otaInfo, 2L);
                    logger.info("淘宝更新订单返回值=>" + result);
                    if (null != result && result.equals("success")) {
                        //同步成功后在修改数据库
                        order.setFeeStatus(FeeStatus.PAID);
                        this.orderDao.updateOrderStatusAndFeeStatus(order);
                        //记录操作日志
                        this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.ACCEPT, order.getOrderStatus(), "淘宝付款成功", currentUser.getId()));
                        return new JsonModel(true, "付款成功");
                    } else {
                        return new JsonModel(false, "调用淘宝更新接口出错");
                    }
                } else if (ChannelSource.FC.equals(order.getChannelSource())) {
                    //天下房仓返回创建订单成功
                    return new JsonModel(true, "创建订单成功");
                }
            }

        }
        return new JsonModel(true, "付款成功");
    }

    /**
     * 淘宝取消接口
     *
     * @param order
     * @param parm  参数 1L取消订单，2L确认订单
     * @return
     * @throws ApiException
     */
    private JsonModel TBCancelMethod(Order order, long parm, UserInfo currentUser) throws ApiException {
        //更新淘宝订单状态
        OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), OtaType.TB.name());
        String result = TBXHotelUtil.orderUpdate(order, otaInfo, parm);
        logger.info("淘宝更新订单返回值=>" + result);
        if (null != result && result.equals("success")) {
            //同步成功后在修改数据库
            this.orderDao.updateOrderStatusAndReason(order);
            this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.ACCEPT, order.getOrderStatus(), "调用淘宝取消接口", currentUser.getId()));
            return new JsonModel(true, "更新订单成功");
        } else {
            return new JsonModel(false, "调用淘宝更新接口出错");
        }
    }

    @Override
//    @Log(descr = "查询订单状态")
    public Map<String, String> findOrderStatus(String xmlStr, ChannelSource channelSource) throws Exception {
        logger.info("查询订单状态传入参数=>"+xmlStr);
        Map<String, String> result = new HashMap<>();
        //解析查询订单状态
        String orderId = XmlDeal.getOrder(xmlStr).getId();
        //验证此订单是否存在
        Order order = orderDao.selectOrderByIdAndChannelSource(orderId, channelSource);
        if (null == order) {
            result.put("status", "-302");
            result.put("message", "订单不存在");
            return result;
        }
        String respose = getOrderStatusMethod(order);

        //解析返回值
        JSONObject jsonObject = JSONObject.fromObject(respose);
        if (!jsonObject.get("status").equals(200)) {
            result.put("status", "-300");
            result.put("message", "查询失败");
            return result;
        } else {
            JSONObject orderJson = JSONObject.fromObject(jsonObject.get("order"));
            if (orderJson.get("status").equals("0")) {
                result.put("status", "100");
            } else if (orderJson.get("status").equals("1")) {
                result.put("status", "1");
            } else if (orderJson.get("status").equals("2")) {
                result.put("status", "6");
            } else if (orderJson.get("status").equals("3")) {
                result.put("status", "4");
            } else if ((orderJson.get("status").equals("4"))) {
                result.put("status", "6");
            } else {
                throw new TomsRuntimeException("OMS内部错误");
            }
            result.put("message", order.getId());
            result.put("code", "0");
        }
       /* businLog.setDescr(logStr);
        businLogClient.save(businLog);*/
        return result;
    }

    /**
     * 获取订单状态
     *
     * @param order
     * @return
     * @throws Exception
     */
    private String getOrderStatusMethod(Order order) throws Exception {
        //查询调用的url
        Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.ORDER_STATUS.name());
        logger.info("查询订单传递参数=>" + order.toCancelOrderParam(order, dictionary).toString());
        //查询OMS订单状态
        String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, dictionary));
        logger.info("查询订单返回值=>" + respose);
        return respose;
    }

    @Override
    public Map<String, String> dealPayBackMethod(String xmlStr, ChannelSource taobao) throws Exception {
        Map<String, String> result = new HashMap<>();
        String orderId = XmlDeal.getOrder(xmlStr).getId();
        Order order = this.orderDao.selectOrderByIdAndChannelSource(orderId, taobao);
        if (null == order) {
            result.put("status", "-400");
            result.put("message", "没有此订单！");
            return result;
        }
        //调用oms取消订单接口
        // 查询调用的url
        Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.CANCEL_ORDER.name());
        if (null != dictionary) {
            //发送请求
            logger.info("oms取消订单传递参数=>" + order.toCancelOrderParam(order, dictionary).toString());
            String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, dictionary));
            JSONObject jsonObject = JSONObject.fromObject(respose);
            logger.info("oms取消订单返回值=>" + jsonObject.toString());
            if (!jsonObject.get("status").equals(200)) {
                result.put("status", "-400");
                result.put("message", "同步OMS失败！");
                return result;
            } else {
                //同步成功后在修改数据库
                order.setOrderStatus(OrderStatus.REFUSE);
                order.setReason("买家申请退款");
                this.orderDao.updateOrderStatusAndReason(order);
                result.put("status", "0");
                result.put("message", "success");
                return result;
            }
        } else {
            result.put("status", "-400");
            result.put("message", "处理失败");
        }
        return result;
    }

    @Override
    public List<OrderParamDto> findOrderByPage(String companyId, PageBounds pageBounds, OrderParamDto orderParamDto) {
        List<OrderParamDto> orderDtos = orderDao.selectOrderByPage(companyId, pageBounds, orderParamDto);
        //房型名称
        if (ArrayUtils.isNotEmpty(orderDtos.toArray())) {
            OtaBangInnRoomDto otaBangInnRoomDto = new OtaBangInnRoomDto();
            for (OrderParamDto orderDto : orderDtos) {
                if (ChannelSource.TAOBAO.equals(orderDto.getChannelSource())) {
                    otaBangInnRoomDto = this.bangInnRoomDao.selectOtaBangInnRoomByRid(orderDto.getOTARoomTypeId());
                } else {
                    otaBangInnRoomDto = this.bangInnRoomDao.selectBangInnRoomByInnIdAndRoomTypeId(orderDto.getInnId(), Integer.parseInt(orderDto.getRoomTypeId()), orderDto.getCompanyId());
                }
                if (null == otaBangInnRoomDto) {
                    orderDto.setRoomTypeName("暂无");
                } else {
                    orderDto.setRoomTypeName(otaBangInnRoomDto.getRoomTypeName());
                }
            }
        }
        return orderDtos;
    }

    @Override
    public OrderParamDto findOrders(String companyId, OrderParamDto orderParamDto) {
        OrderParamDto result = new OrderParamDto();
        List<OrderParamDto> orderParamDtoList = this.orderDao.selectOrders(companyId, orderParamDto);
        if (ArrayUtils.isNotEmpty(orderParamDtoList.toArray())) {
            for (OrderParamDto order : orderParamDtoList) {
                if (order.getOrderStatus().equals(OrderStatus.ACCEPT)) {
                    result.setAcceptOrder(result.getAcceptOrder() + 1);
                    result.setAllTotalPrice(result.getAllTotalPrice().add(order.getTotalPrice()));
                    if (null != order.getCostPrice()) {
                        result.setAllCostPrice(result.getAllCostPrice().add(order.getCostPrice()));
                    }
                    if (null != order.getOTAPrice()) {
                        result.setAllPayPrice(result.getAllPayPrice().add(order.getOTAPrice()));
                    }
                    if (null != order.getPrepayPrice()) {
                        result.setAllPrePrice(result.getAllPrePrice().add(order.getPrepayPrice()));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public OrderParamDto findOrderById(String id) {
        OrderParamDto orderParamDto = this.orderDao.selectOrderById(id);
        if (null != orderParamDto) {
            OtaBangInnRoomDto otaBangInnRoomDto = new OtaBangInnRoomDto();
            if (ChannelSource.TAOBAO.equals(orderParamDto.getChannelSource())) {
                otaBangInnRoomDto = this.bangInnRoomDao.selectOtaBangInnRoomByRid(orderParamDto.getOTARoomTypeId());
            } else {
                otaBangInnRoomDto = this.bangInnRoomDao.selectBangInnRoomByInnIdAndRoomTypeId(orderParamDto.getInnId(), Integer.parseInt(orderParamDto.getRoomTypeId()), orderParamDto.getCompanyId());
            }
            if (null != otaBangInnRoomDto) {
                orderParamDto.setRoomTypeName(otaBangInnRoomDto.getRoomTypeName());
            } else {
                orderParamDto.setRoomTypeName("暂无");
            }
            List<DailyInfos> dailyInfoses = this.dailyInfosDao.selectDailyInfoByOrderId(orderParamDto.getId());
            //价格策略
            if (ChannelSource.TAOBAO.equals(orderParamDto.getChannelSource())) {
                OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(orderParamDto.getOTAHotelId());
                if (ArrayUtils.isNotEmpty(dailyInfoses.toArray())) {
                    for (DailyInfos dailyInfos : dailyInfoses) {
                        dailyInfos.setCostPrice(dailyInfos.getPrice().multiply(otaInnOtaDto.getPriceModelValue()));
                    }
                }
            } else {
                if (ArrayUtils.isNotEmpty(dailyInfoses.toArray())) {
                    for (DailyInfos dailyInfos : dailyInfoses) {
                        dailyInfos.setCostPrice(dailyInfos.getPrice());
                    }
                }
            }
            orderParamDto.setDailyInfoses(dailyInfoses);
        }
        return orderParamDto;
    }

    @Override
    public JsonModel confirmOrder(OrderParamDto order, UserInfo currentUser) throws Exception {
        JsonModel jsonModel = new JsonModel();
        //手动确认并执行下单
        //设置订单状态为确认并执行下单
        order.setOrderStatus(OrderStatus.CONFIM_AND_ORDER);
        //淘宝订单处理方法
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            jsonModel = payBackDealMethod(order, currentUser, OtaType.TB.name());
        } else {
            //TODO DO SOMETHING
            jsonModel.setSuccess(true);
            jsonModel.setMessage("下单成功");
        }
        return jsonModel;
    }

    @Override
    public JsonModel refuesOrder(OrderParamDto order, UserInfo currentUser) throws ApiException {
        JsonModel jsonModel = new JsonModel();
        //直接拒绝订单，不同步oms，直接调用淘宝更新订单状态接口
        //1.调用淘宝更新订单接口
        order.setOrderStatus(OrderStatus.HAND_REFUSE);
        order.setReason("手动直接拒绝");
        //淘宝更新订单
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            jsonModel = TBCancelMethod(order, 1L,currentUser);
        } else {
            //更新订单
            this.orderDao.updateOrderStatusAndReason(order);
            //记录订单状态日志
            this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.NOT_DEAL, OrderStatus.HAND_REFUSE, "手动拒绝", currentUser.getId()));
            jsonModel.setSuccess(true);
            jsonModel.setMessage("成功拒绝订单");
        }

        return jsonModel;

    }

    @Override
    public JsonModel confirmNoOrder(OrderParamDto order, UserInfo currentUser) throws ApiException {
        JsonModel jsonModel = new JsonModel();
        //确认订单，但不同步oms
        //1.修改订单状态，2.调用淘宝更新订单确认有房
        order.setOrderStatus(OrderStatus.CONFIM_NO_ORDER);
        order.setReason("确认但不执行下单");
        //淘宝更新订单
        //淘宝订单才更新
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            jsonModel = TBCancelMethod(order, 2L,currentUser);
        } else {
            //更新订单
            this.orderDao.updateOrderStatusAndReason(order);
            this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.NOT_DEAL, OrderStatus.HAND_REFUSE, "确认但是不执行下单", currentUser.getId()));
            jsonModel.setSuccess(true);
            jsonModel.setMessage("确认订单成功");
        }
        return jsonModel;
    }

    @Override
    public Map<String, Object> dealHandMakeOrder(Order order, UserInfo userInfo) throws Exception {
        Map<String, Object> result = new HashMap<>();
        //下单到oms
        //1.查询字典表
        //查询字典表中同步OMS需要的数据
        Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.CREATE_ORDER.name());
        String respose = "";
        JSONObject jsonObject = null;
        RoomTypeInfoDto roomTypeInfoDto = new RoomTypeInfoDto();
        //处理每日价格信息
        ParamDto paramDto = new ParamDto();
        paramDto.setCompanyId(userInfo.getCompanyId());
        paramDto.setUserId(userInfo.getId());
        //设置底价和卖价的accountId
        paramDto.setAccountId(order.getAccountId() + "");
        BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndAccountId(userInfo.getCompanyId(), Integer.valueOf(order.getAccountId()));
        if (1 == order.getMaiAccount()) {
            //卖家
            order.setAccountId(bangInn.getAccountId());
        } else {
            //底价
            order.setAccountId(bangInn.getAccountIdDi());
        }
        //设置查询日期
        paramDto.setStartDate(DateUtil.format(order.getLiveTime(), "yyyy-MM-dd"));
        paramDto.setEndDate(DateUtil.format(DateUtil.addDay(order.getLeaveTime(), -1), "yyyy-MM-dd"));
        paramDto.setMaiAccount(order.getMaiAccount());
        roomTypeInfoDto = this.roomTypeService.findRoomType(paramDto, userInfo);
        Order hangOrder = order.makeHandOrder(order, roomTypeInfoDto);
        try {

            logger.info("oms手动下单传递参数" + order.toOrderParamDto(hangOrder, dictionary).toString());
            respose = HttpClientUtil.httpPostOrder(dictionary.getUrl(), order.toOrderParamDto(hangOrder, dictionary));
            jsonObject = JSONObject.fromObject(respose);
        } catch (Exception e) {
            result.put("status", false);
            result.put("message", "同步oms失败");
            return result;
        }
        logger.info("OMS接口响应=>" + respose);
        if (!jsonObject.get("status").equals(200)) {
            result.put("status", false);
            result.put("message", "oms接口相应失败");
            return result;
        } else {
            //设置innId
            if (null != bangInn) {
                hangOrder.setInnId(bangInn.getInnId());
                //同步oms订单成功，保存订单到toms
                this.orderDao.insertOrder(hangOrder);
                //创建每日价格信息
                this.dailyInfosDao.insertDailyInfos(hangOrder);
                //创建入住人信息
                this.orderGuestsDao.insertOrderGuests(hangOrder);
                result.put("status", true);
                result.put("message", "下单成功");
                return result;
            } else {
                result.put("status", false);
                result.put("message", "下单失败，客栈不存在");
                return result;
            }
        }
    }

    @Override
    public List<Order> findOrderChancelSource(String companyId) {
        return this.orderDao.selectOrderChancelSource(companyId);
    }

    @Override
    public List<RoomTypeInfoDto> findHandOrderRoomType(Order order, UserInfo userInfo) throws Exception {
        ParamDto paramDto = new ParamDto();
        paramDto.setCompanyId(userInfo.getCompanyId());
        paramDto.setUserId(userInfo.getId());
        paramDto.setAccountId(order.getAccountId() + "");
        if (StringUtils.isNotEmpty(order.getLeaveTime().toString())) {
            paramDto.setEndDate(DateUtil.format(order.getLeaveTime(), "yyyy-MM-dd"));
        }
        if (StringUtils.isNotEmpty(order.getLiveTime().toString())) {
            paramDto.setStartDate(DateUtil.format(order.getLiveTime(), "yyyy-MM-dd"));
        }
        paramDto.setTagId(order.getTagId());
        paramDto.setMaiAccount(order.getMaiAccount());
        RoomTypeInfoDto roomType = roomTypeService.findRoomType(paramDto, userInfo);
        List<RoomTypeInfoDto> roomTypeInfoDtos = new ArrayList<>();
        //处理找出的房型信息，如果房量为空的提出数据
        if (null != roomType) {
            if (ArrayUtils.isNotEmpty(roomType.getList().toArray())) {
                outer:
                for (RoomTypeInfo roomTypeInfo : roomType.getList()) {
                    if (ArrayUtils.isNotEmpty(roomTypeInfo.getRoomDetail().toArray())) {
                        RoomTypeInfoDto roomTypeInfoDto = new RoomTypeInfoDto();
                        for (RoomDetail roomDetail : roomTypeInfo.getRoomDetail()) {
                            if (null != roomDetail.getRoomNum()) {
                                if (0 != roomDetail.getRoomNum()) {
                                    roomTypeInfoDto.setRoomTypeId(roomTypeInfo.getRoomTypeId() + "");
                                    roomTypeInfoDto.setRoomTypeName(roomTypeInfo.getRoomTypeName());
                                    roomTypeInfoDto.setMaxRoomNum(roomDetail.getRoomNum());
                                }
                                if (0 == roomDetail.getRoomNum() || StringUtils.isEmpty(roomDetail.getRoomNum() + "")) {
                                    roomTypeInfoDto = new RoomTypeInfoDto();
                                    continue outer;
                                }
                            }
                        }
                        if (StringUtils.isNotEmpty(roomTypeInfoDto.getRoomTypeName()) && StringUtils.isNotEmpty(roomTypeInfoDto.getRoomTypeId())) {
                            roomTypeInfoDtos.add(roomTypeInfoDto);
                        }
                    }
                }
            }
        }
        return roomTypeInfoDtos;
    }

    @Override
    public JsonModel agreePayBackOrder(OrderParamDto order, UserInfo currentUser) throws Exception {
        JsonModel result = new JsonModel();
        //调用oms取消订单接口
        // 查询调用的url
        Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.CANCEL_ORDER.name());
        if (null != dictionary) {
            //发送请求
            logger.info("oms取消订单传递参数=>" + order.toCancelOrderParam(order, dictionary).toString());
            String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, dictionary));
            JSONObject jsonObject = JSONObject.fromObject(respose);
            logger.info("oms取消订单返回值=>" + jsonObject.toString());
            if (!jsonObject.get("status").equals(200)) {
                //如果取消订单失败，不修改订单状态
                result.setMessage("取消订单失败");
                result.setSuccess(false);
            } else {
                //同步成功后在修改数据库，退款申请成功修改订单状态为已取消
                order.setOrderStatus(OrderStatus.CANCEL_ORDER);
                order.setReason("买家申请退款");
                result.setSuccess(true);
                result.setMessage("取消订单成功");
                this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.ACCEPT, order.getOrderStatus(), "同意退款", currentUser.getId()));
            }
            this.orderDao.updateOrderStatusAndReason(order);
        } else {
            result.setMessage("取消订单失败");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public JsonModel refusePayBackOrder(OrderParamDto order, UserInfo currentUser) {
        JsonModel result = new JsonModel();
        //拒绝退款修改订单状态为接受
        order.setOrderStatus(OrderStatus.ACCEPT);
        this.orderDao.updateOrderStatusAndReason(order);
        this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.PAY_BACK, order.getOrderStatus(), "拒绝退款", currentUser.getId()));
        result.setMessage("拒绝申请成功");
        result.setSuccess(true);
        return result;
    }

    @Override
    public Map<String, Object> createFcHotelOrder(String xml) throws Exception {
        logger.info("天下房仓创建订单传递参数=>" + xml);
        Map<String, Object> result = new HashMap<>();
        //解析xml
        Order order = XmlDeal.getFcCreateOrder(xml);
        //创建订单
        //1.天下房仓需要查询otahotelid
        OtaInfoRefDto otaInfo = this.otaInfoDao.selectCompanyIdByAppKey(ResourceBundleUtil.getString("fc.appKey"), ResourceBundleUtil.getString("fc.appSecret"));
        OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(order.getInnId(), otaInfo.getCompanyId(), otaInfo.getOtaInfoId());
        order.setOTAHotelId(otaInnOtaDto.getWgHid());
        createOrderMethod(order.getChannelSource(), order);
        //天下房仓创建订单，同步oms
        JsonModel jsonModel = payBackDealMethod(order, new UserInfo(), OtaType.FC.name());
        result.put("status", jsonModel);
        result.put("order", order);
        return result;
    }

    @Override
    public CancelHotelOrderResponse cancelFcHotelOrder(String xml) throws Exception {
        logger.info("天下房仓取消订单传入参数=>" + xml);
        CancelHotelOrderResponse result = new CancelHotelOrderResponse();
        //解析xml
        Order orderCancel = XmlDeal.getFcCancelOrder(xml);
        //查询数据库中是否存在此订单
        Order order = this.orderDao.selectOrderByIdAndChannelSource(orderCancel.getId(), ChannelSource.FC);
        if (null == order) {
            return null;
        } else {
            order.setReason(orderCancel.getReason());
            JsonModel jsonModel = cancelOrderMethod(order);
            result.setSpOrderId(order.getId());
            //判断取消订单是否成功，设置响应的订单状态1,取消成功;2,取消失败;(表示此单无法取消) 3,取消待定;（表示此单）
            if (jsonModel.isSuccess()) {
                result.setCancelStatus(1);
            } else {
                result.setCancelStatus(2);
            }
        }
        return result;
    }

    @Override
    public GetOrderStatusResponse getFcOrderStatus(String xml) throws Exception {
        logger.info("天下房仓查询订单状态传递参数=>" + xml);
        GetOrderStatusResponse result = new GetOrderStatusResponse();
        //解析xml
        Order orderParam = XmlDeal.getFcOrderStatus(xml);
        Order order = this.orderDao.selectOrderByIdAndChannelSource(orderParam.getId(), ChannelSource.FC);
        if (null == order) {
            return null;
        } else {
            //获取订单状态
            String respose = getOrderStatusMethod(order);
            JSONObject jsonObject = JSONObject.fromObject(respose);
            if (!jsonObject.get("status").equals(200)) {
                return null;
            } else {
                if (jsonObject.get("orderStatus").equals("0")) {
                    result.setOrderStatus(0);
                } else if (jsonObject.get("orderStatus").equals("1")) {
                    result.setOrderStatus(1);
                } else if (jsonObject.get("orderStatus").equals("2")) {
                    result.setOrderStatus(2);
                } else if (jsonObject.get("orderStatus").equals("3")) {
                    result.setOrderStatus(3);
                } else if ((jsonObject.get("orderStatus").equals("4"))) {
                    result.setOrderStatus(2);
                } else {
                    throw new TomsRuntimeException("OMS内部错误");
                }
                result.setSpOrderId(order.getId());
            }
        }
        return result;
    }

    @Override
    public CheckRoomAvailResponse checkRoomAvail(String xml) throws IOException {
        logger.info("天下房仓试订单传入参数=>" + xml);
        CheckRoomAvailResponse result = new CheckRoomAvailResponse();
        //解析xml
        Order order = XmlDeal.getCheckRoomAvailOrder(xml);
        Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.CHECK_ORDER.name());
        logger.info("天下房仓试订单接口传递参数=>" + order.toRoomAvail(dictionary, order).toString());
        String response = HttpClientUtil.httpGetRoomAvail(dictionary.getUrl(), order.toRoomAvail(dictionary, order));
        JSONObject jsonObject = JSONObject.fromObject(response);
        logger.info("天下房仓试订单接口返回值=>" + response.toString());
        if (jsonObject.get("status").equals(200)) {
            //是否可以及时确认
            result.setCanImmediate("1");
            result.setSpRatePlanId(order.getOTARatePlanId());
            List<RoomDetail> roomDetails = (List<RoomDetail>) JSONArray.toList(jsonObject.getJSONArray("data"), RoomDetail.class);
            //转换oms房型信息为toms的每日入住信息
            List<DailyInfos> dailyInfos = OrderMethodHelper.toDailyInfos(roomDetails);
            if (null != dailyInfos && ArrayUtils.isNotEmpty(dailyInfos.toArray())) {
                List<SaleItem> saleItemList = new ArrayList<>();
                boolean canBook = false;
                for (DailyInfos dailyInfos1 : dailyInfos) {
                    SaleItem saleItem = new SaleItem();
                    //无早
                    saleItem.setBreakfastType("");
                    //配额数量
                    saleItem.setAvailableQuotaNum(0);
                    //早餐数量
                    saleItem.setBreakfastNum(0);
                    //货币类型
                    saleItem.setCurrencyType(CurrencyCode.CNY);
                    //根据房间数量判断房间状态和是否可预订
                    if (dailyInfos1.getRoomNum() > 0) {
                        //dayCanBook:1可预订，roomstatus：1有房
                        saleItem.setDayCanBook(1);
                        saleItem.setRoomStatus(1);
                        canBook = true;
                    } else {
                        //dayCanBook:0不可预订，roomstatus：2满房
                        saleItem.setDayCanBook(0);
                        saleItem.setRoomStatus(2);
                        canBook = false;
                    }
                    //是否可超，0否，1是
                    saleItem.setOverDraft(0);
                    //价格是否待查，0否，1是
                    saleItem.setPriceNeedCheck(0);
                    saleItem.setSaleDate(DateUtil.format(dailyInfos1.getDay(), "yyyy-MM-dd"));
//                    saleItem.setSalePrice(dailyInfos1.getPrice());
                    saleItem.setSalePrice(new BigDecimal(new DecimalFormat("#.00").format(dailyInfos1.getPrice())));
                    saleItemList.add(saleItem);
                }
                //设置是否可预定
                if (canBook) {
                    result.setCanBook("1");
                } else {
                    result.setCanBook("0");
                }
                result.setSaleItems(saleItemList);
            }
        } else {
            return null;
        }
        return result;
    }

    @Override
    public UploadStatus getFcAddHotelInfo() throws DocumentException {
        //1.下载天下房仓增量文件到本地
        FileDealUtil.downLoadFromUrl(ResourceBundleUtil.getString(Constants.FcDownLoadUrl) + DateUtil.format(new Date(), "yyyy-MM-dd") + ".zip", DateUtil.format(new Date(), "yyyy-MM-dd") + ".zip", FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd"));
        //2.解压下载的文件
        FileDealUtil.unZipFiles(new File(FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd") + "\\" + DateUtil.format(new Date(), "yyyy-MM-dd") + ".zip"), FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd") + "\\");
        //3.解析xml
        File file = new File(FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd"));
        if (file.isDirectory()) {
            File[] f = file.listFiles();
            outer:
            for (int i = 0; i < file.list().length; i++) {
                if (f[i].isDirectory() || f[i].getName().contains("zip")) {
                    continue outer;
                } else {
                    SAXReader reader = new SAXReader();
                    Document document = reader.read(f[i]);
                    FcHotelInfoDto fcHotelInfoDto = XmlDeal.dealFcHotelInfo(document);
                    //4.判断此酒店是否存在，存在更新，不存在插入
                    FcHotelInfoDto hotelInfoDto = this.fcHotelInfoDao.selectFcHotelInfoByFcHotelId(fcHotelInfoDto.getHotelId());
                    if (null == hotelInfoDto) {
                        this.fcHotelInfoDao.insertFcHotelInfo(fcHotelInfoDto);
                    } else {
                        this.fcHotelInfoDao.updateFcHotelInfo(fcHotelInfoDto);
                    }
                    //操作酒店对应的房型
                    if (ArrayUtils.isNotEmpty(fcHotelInfoDto.getFcRoomTypeInfos().toArray())) {
                        for (FcRoomTypeInfo fcRoomTypeInfo : fcHotelInfoDto.getFcRoomTypeInfos()) {
                            FcRoomTypeInfoDto fcRoomTypeInfoDto = this.fcRoomTypeInfoDao.selectFcRoomTypeByHotelIdAndRoomTypeId(fcHotelInfoDto.getHotelId(), fcRoomTypeInfo.getRoomTypeId());
                            if (null == fcRoomTypeInfoDto) {
                                //新增房型
                                this.fcRoomTypeInfoDao.insertRoomTypeInfo(fcRoomTypeInfo);
                            } else {
                                //修改房型
                                this.fcRoomTypeInfoDao.updateFcRoomTypeInfo(fcRoomTypeInfo);
                            }
                        }
                    }
                }
            }
        }
        //5.处理完成将文件上传到ftp上
        UploadStatus uploadStatus = FTPUtil.upload(new File(FileDealUtil.getCurrentPath() + ResourceBundleUtil.getString(Constants.FcDownLoadSavePath) + DateUtil.format(new Date(), "yyyy-MM-dd") + "\\" + DateUtil.format(new Date(), "yyyy-MM-dd") + ".zip"), ResourceBundleUtil.getString(Constants.FcUploadUrl) + DateUtil.format(new Date(), "yyyy-MM-dd") + "/" + DateUtil.format(new Date(), "yyyy-MM-dd") + ".zip", false);
        return uploadStatus;


    }
}
