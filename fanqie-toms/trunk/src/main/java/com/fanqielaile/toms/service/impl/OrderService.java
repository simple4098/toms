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
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.model.Dictionary;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.service.IRoomTypeService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.JsonModel;
import com.fanqielaile.toms.support.util.XmlDeal;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.taobao.api.ApiException;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
        OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
        //设置每日价格
        if (ArrayUtils.isNotEmpty(order.getDailyInfoses().toArray())) {
            for (DailyInfos dailyInfos : order.getDailyInfoses()) {
                dailyInfos.setPrice(dailyInfos.getPrice().divide(otaInnOtaDto.getPriceModelValue(), 2, BigDecimal.ROUND_UP));
            }
        }
        //设置渠道来源
        order.setChannelSource(channelSource);
        order.setOrderTime(new Date());
        //设置订单总价
        order.setTotalPrice(order.getTotalPrice().divide(otaInnOtaDto.getPriceModelValue(), 2, BigDecimal.ROUND_UP));
        //设置订单号
        order.setOrderCode(OrderMethodHelper.getOrderCode());
        //算成本价与OTA收益
        order.setCostPrice(order.getTotalPrice());
        order.setOTAPrice(order.getTotalPrice().multiply(otaInnOtaDto.getPriceModelValue()).subtract(order.getTotalPrice()));
        order.setCompanyId(otaInnOtaDto.getCompanyId());
        //创建订单
        this.orderDao.insertOrder(order);
        //创建每日价格信息
        this.dailyInfosDao.insertDailyInfos(order);
        //创建入住人信息
        this.orderGuestsDao.insertOrderGuests(order);
      /*  //保存日志
        businLog.setDescr(logStr + order.toString());
        businLogClient.save(businLog);*/
        return order;
    }

    @Override
//    @Log(descr = "取消订单")
    public JsonModel cancelOrder(String xmlStr, ChannelSource channelSource) throws Exception {
        String logStr = "取消订单传递的=>" + xmlStr;
        //解析取消订单的xml
        String orderId = XmlDeal.getOrder(xmlStr).getId();
        //验证此订单是否存在
        Order order = orderDao.selectOrderByIdAndChannelSource(orderId, channelSource);
        if (null == order) {
            return new JsonModel(false, "订单不存在");
        }
        order.setReason(XmlDeal.getOrder(xmlStr).getReason());
        order.setOrderStatus(OrderStatus.CANCEL_ORDER);
        //判断订单是否需要同步OMS,条件根据订单是否付款
        if (!order.getFeeStatus().equals(FeeStatus.NOT_PAY)) {
            // 查询调用的url
            Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.CANCEL_ORDER.name());
            if (null != dictionary) {
                //发送请求
                String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, dictionary));
                logStr = logStr + "调用OMS取消订单的返回值=>" + respose.toString();
                JSONObject jsonObject = JSONObject.fromObject(respose);
                if (!jsonObject.get("status").equals(200)) {
                    return new JsonModel(false, jsonObject.get("status").toString() + ":" + jsonObject.get("message"));
                } else {
                    //同步成功后在修改数据库
                    this.orderDao.updateOrderStatusAndReason(order);
                }
            }
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
                return payBackDealMethod(order);
            } else {
                //手动下单,手动下单修改订单状态为待处理
                order.setOrderStatus(OrderStatus.NOT_DEAL);
                //待处理订单写入付款金额和付款码
                order.setFeeStatus(FeeStatus.PAID);
                this.orderDao.updateOrderStatusAndFeeStatus(order);
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
    private JsonModel payBackDealMethod(Order order) throws Exception {
        // 房态更新时间
        OtaInnRoomTypeGoodsDto roomTypeGoodsDto = this.otaInnRoomTypeGoodsDao.findRoomTypeByRid(Long.parseLong(order.getOTAGid()));
        if (null != roomTypeGoodsDto) {
            order.setOrderCreateTime(roomTypeGoodsDto.getProductDate());
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
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), OtaType.TB.name());
            //查询客栈信息
            BangInnDto bangInn = this.bangInnDao.selectBangInnByTBHotelId(order.getOTAHotelId(),otaInfo.getOtaInfoId(),order.getCompanyId());
            if (null == bangInn) {
                logger.info("绑定客栈不存在" + order.getOTAHotelId());
                return new JsonModel(false, "绑定客栈不存在");
            }
            //查询当前酒店以什么模式发布thi
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
            if (otaInnOtaDto.getsJiaModel().equals("MAI")) {
                order.setAccountId(bangInn.getAccountId());
            } else {
                order.setAccountId(bangInn.getAccountIdDi());
            }
            OtaBangInnRoomDto otaBangInnRoomDtos = this.bangInnRoomDao.selectBangInnRoomByInnIdAndRoomTypeId(order.getInnId(), Integer.parseInt(order.getRoomTypeId()), order.getCompanyId());
            if (null == otaBangInnRoomDtos) {
                return new JsonModel(false, "房型不存在");
            }
            order.setRoomTypeName(otaBangInnRoomDtos.getRoomTypeName());
            logger.info("OMS接口传递参数=>" + order.toOrderParamDto(order, dictionary).toString());
            String respose = "";
            JSONObject jsonObject = null;
            try {
                respose = HttpClientUtil.httpPostOrder(dictionary.getUrl(), order.toOrderParamDto(order, dictionary));
                jsonObject = JSONObject.fromObject(respose);
            } catch (Exception e) {
                order.setOrderStatus(OrderStatus.REFUSE);
                order.setFeeStatus(FeeStatus.NOT_PAY);
                String result = TBXHotelUtil.orderUpdate(order, otaInfo, 1L);
                logger.info("淘宝取消订单接口返回值=>" + result);
                if (null != result && result.equals("success")) {
                    this.orderDao.updateOrderStatusAndFeeStatus(order);
                }
                return new JsonModel(false, "OMS系统异常");
            }
            logger.info("OMS接口响应=>" + respose);
            if (!jsonObject.get("status").equals(200)) {
                order.setOrderStatus(OrderStatus.REFUSE);
                order.setFeeStatus(FeeStatus.NOT_PAY);
                String result = TBXHotelUtil.orderUpdate(order, otaInfo, 1L);
                logger.info("淘宝取消订单接口返回值=>" + result);
                if (null != result && result.equals("success")) {
                    this.orderDao.updateOrderStatusAndFeeStatus(order);
                }
                return new JsonModel(false, jsonObject.get("status") + ":" + jsonObject.get("message"));
            } else {
                //更新淘宝订单状态
                String result = TBXHotelUtil.orderUpdate(order, otaInfo, 2L);
                logger.info("淘宝更新订单返回值=>" + result);
                if (null != result && result.equals("success")) {
                    //同步成功后在修改数据库
                    order.setFeeStatus(FeeStatus.PAID);
                    this.orderDao.updateOrderStatusAndFeeStatus(order);
                    return new JsonModel(true, "付款成功");
                } else {
                    return new JsonModel(false, "调用淘宝更新接口出错");
                }
            }

        } else {
            return new JsonModel(false, "系统内部错误");
        }
    }

    /**
     * 淘宝取消接口
     *
     * @param order
     * @param parm  参数 1L取消订单，2L确认订单
     * @return
     * @throws ApiException
     */
    private JsonModel TBCancelMethod(Order order, long parm) throws ApiException {
        //更新淘宝订单状态
        OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), OtaType.TB.name());
        String result = TBXHotelUtil.orderUpdate(order, otaInfo, parm);
        logger.info("淘宝更新订单返回值=>" + result);
        if (null != result && result.equals("success")) {
            //同步成功后在修改数据库
            this.orderDao.updateOrderStatusAndReason(order);
            return new JsonModel(true, "更新订单成功");
        } else {
            return new JsonModel(false, "调用淘宝更新接口出错");
        }
    }

    @Override
//    @Log(descr = "查询订单状态")
    public Map<String, String> findOrderStatus(String xmlStr, ChannelSource channelSource) throws Exception {
        String logStr = "查询订单状态传入参数=>" + xmlStr;
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
        //查询调用的url
        Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.ORDER_STATUS.name());
        logger.info("查询订单传递参数=>" + order.toCancelOrderParam(order, dictionary).toString());
        logStr = logStr + "查询订单传递参数=>" + order.toCancelOrderParam(order, dictionary).toString();
        //查询OMS订单状态
        String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, dictionary));
        logger.info("查询订单返回值=>" + respose);
        logStr = logStr + "查询订单返回值=>" + respose;
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
    public JsonModel confirmOrder(OrderParamDto order) throws Exception {
        JsonModel jsonModel = new JsonModel();
        //手动确认并执行下单
        //设置订单状态为确认并执行下单
        order.setOrderStatus(OrderStatus.CONFIM_AND_ORDER);
        //淘宝订单处理方法
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            jsonModel = payBackDealMethod(order);
        } else {
            //TODO DO SOMETHING
            jsonModel.setSuccess(true);
            jsonModel.setMessage("下单成功");
        }
        return jsonModel;
    }

    @Override
    public JsonModel refuesOrder(OrderParamDto order) throws ApiException {
        JsonModel jsonModel = new JsonModel();
        //直接拒绝订单，不同步oms，直接调用淘宝更新订单状态接口
        //1.调用淘宝更新订单接口
        order.setOrderStatus(OrderStatus.HAND_REFUSE);
        order.setReason("手动直接拒绝");
        //淘宝更新订单
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            jsonModel = TBCancelMethod(order, 1L);
        } else {
            //更新订单
            this.orderDao.updateOrderStatusAndReason(order);
            jsonModel.setSuccess(true);
            jsonModel.setMessage("成功拒绝订单");
        }

        return jsonModel;

    }

    @Override
    public JsonModel confirmNoOrder(OrderParamDto order) throws ApiException {
        JsonModel jsonModel = new JsonModel();
        //确认订单，但不同步oms
        //1.修改订单状态，2.调用淘宝更新订单确认有房
        order.setOrderStatus(OrderStatus.CONFIM_NO_ORDER);
        order.setReason("确认但不执行下单");
        //淘宝更新订单
        //淘宝订单才更新
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            jsonModel = TBCancelMethod(order, 2L);
        } else {
            //更新订单
            this.orderDao.updateOrderStatusAndReason(order);
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
        paramDto.setAccountId(order.getAccountId() + "");
        //设置查询日期
        paramDto.setStartDate(DateUtil.format(order.getLiveTime(), "yyyy-MM-dd"));
        paramDto.setEndDate(DateUtil.format(DateUtil.addDay(order.getLeaveTime(), -1), "yyyy-MM-dd"));
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
            BangInn bangInn = this.bangInnDao.selectBangInnByCompanyIdAndAccountId(hangOrder.getCompanyId(), hangOrder.getAccountId());
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
        RoomTypeInfoDto roomType = roomTypeService.findRoomType(paramDto, userInfo);
        List<RoomTypeInfoDto> roomTypeInfoDtos = new ArrayList<>();
        //处理找出的房型信息，如果放量为空的提出数据
        if (null != roomType) {
            if (ArrayUtils.isNotEmpty(roomType.getList().toArray())) {
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
}
