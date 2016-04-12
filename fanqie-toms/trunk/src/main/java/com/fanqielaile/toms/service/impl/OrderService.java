package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.OrderSource;
import com.fanqie.core.dto.OrderSourceDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.jw.dto.JointWisdomInnRoomMappingDto;
import com.fanqie.util.DateUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.*;
import com.fanqielaile.toms.dto.ctrip.CtripRoomTypeMapping;
import com.fanqielaile.toms.dto.fc.CancelHotelOrderResponse;
import com.fanqielaile.toms.dto.fc.CheckRoomAvailResponse;
import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.dto.fc.GetOrderStatusResponse;
import com.fanqielaile.toms.dto.orderLog.OrderLogData;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.model.Dictionary;
import com.fanqielaile.toms.model.fc.FcRoomTypeInfo;
import com.fanqielaile.toms.model.fc.SaleItem;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.service.IRoomTypeService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.tb.TBXHotelUtilPromotion;
import com.fanqielaile.toms.support.util.*;
import com.fanqielaile.toms.support.util.ftp.FTPUtil;
import com.fanqielaile.toms.support.util.ftp.UploadStatus;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.taobao.api.ApiException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
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
import javax.servlet.http.HttpServletResponse;
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
    @Resource
    private IOtaCommissionPercentDao otaCommissionPercentDao;
    @Resource
    private CtripRoomTypeMappingDao ctripRoomTypeMappingDao;
    @Resource
    private IJointWisdomInnRoomDao jointWisdomInnRoomDao;
    @Resource
    private ExceptionOrderDao exceptionOrderDao;
    @Resource
    private OrderOtherPriceDao orderOtherPriceDao;


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
    public Map<String, Object> addOrder(String xmlStr, ChannelSource channelSource) throws Exception {
        Map<String, Object> reslut = new HashMap<>();
        String logStr = "创建订单传递参数=>" + xmlStr;
        //解析xml
        Element dealXmlStr = XmlDeal.dealXmlStr(xmlStr);
        //转换成对象只针对淘宝传递的参数
        Order order = OrderMethodHelper.getOrder(dealXmlStr);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(ChannelSource.TAOBAO, order.getChannelOrderCode(), order.getId(), null, OrderStatus.ACCEPT, OrderStatus.ACCEPT, FeeStatus.NOT_PAY, JacksonUtil.obj2json(order), null, order.getInnId(), order.getInnCode(), "淘宝创建订单"));
        //创建订单
        JsonModel j = createOrderMethod(channelSource, order);
        reslut.put("status", j.isSuccess());
        reslut.put("data", order);
        reslut.put("message", j.getMessage());
        logger.info("淘宝创建订单返回值:" + j.toString());
        return reslut;
    }

    /**
     * 创建订单
     *
     * @param channelSource
     * @param order
     */
    public JsonModel createOrderMethod(ChannelSource channelSource, Order order) throws IOException {
        JsonModel result = new JsonModel();
        //查询客栈关联信息
        OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
        //查询价格模式（toms的增减价）
        OtaRoomPriceDto otaRoomPriceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(otaInnOtaDto.getCompanyId(), Integer.parseInt(order.getRoomTypeId()), otaInnOtaDto.getOtaInfoId()));
        //价格比例
        BigDecimal percent = BigDecimal.ZERO;
        order.setAddPrice(BigDecimal.ZERO);
        //公司信息
        Company company = this.companyDao.selectCompanyById(otaInnOtaDto.getCompanyId());
        UsedPriceModel usedPriceModel = null;
        if (ChannelSource.TAOBAO.equals(channelSource)) {
            //查询
            OtaInfoRefDto otaInfoRefDto = otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(company.getId(), otaInnOtaDto.getOtaInfoId());
            usedPriceModel = otaInfoRefDto.getUsedPriceModel();
            percent = getOtaPercent(company, usedPriceModel);
        } else if (ChannelSource.FC.equals(channelSource)) {
            OtaInfoRefDto otaInfoRefDto = otaInfoDao.selectAllOtaByCompanyAndType(company.getId(), OtaType.FC.name());
            usedPriceModel = otaInfoRefDto.getUsedPriceModel();
            percent = getOtaPercent(company, usedPriceModel);
        } else if (ChannelSource.XC.equals(channelSource)) {
            OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectAllOtaByCompanyAndType(company.getId(), OtaType.XC.name());
            usedPriceModel = otaInfoRefDto.getUsedPriceModel();
            percent = getOtaPercent(company, usedPriceModel);
        } else if (ChannelSource.ZH.equals(channelSource)) {
            OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectAllOtaByCompanyAndType(company.getId(), OtaType.ZH.name());
            usedPriceModel = otaInfoRefDto.getUsedPriceModel();
            percent = getOtaPercent(company, usedPriceModel);
        }

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
                        //设置减价数额
                        if (null != otaRoomPriceDto && null != otaRoomPriceDto.getValue()) {
                            order.setAddPrice(BigDecimal.valueOf(otaRoomPriceDto.getValue()));
                            //当前下单日期执行了加减价
                            dailyInfos.setWeatherAdd(1);
                        }
                    } else {
                        dailyInfos.setPrice(dailyInfos.getPrice().divide(otaInnOtaDto.getPriceModelValue(), 2, BigDecimal.ROUND_UP));
                    }
                }
            }
        }

        //设置客栈名称和房型名称
        BangInn bangInn = this.bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), order.getInnId());
        if (null != bangInn) {
            order.setOrderInnName(bangInn.getInnName());
        }
        if (ChannelSource.TAOBAO.equals(channelSource)) {
            OtaBangInnRoomDto otaBangInnRoomDto = this.bangInnRoomDao.selectOtaBangInnRoomByRid(order.getOTARoomTypeId());
            if (null != otaBangInnRoomDto) {
                order.setOrderRoomTypeName(otaBangInnRoomDto.getRoomTypeName());
            }
        } else if (ChannelSource.FC.equals(channelSource)) {
            FcRoomTypeFqDto fcRoomTypeFqDto = this.fcRoomTypeFqDao.selectRoomTypeInfoByRoomTypeId(order.getRoomTypeId());
            if (null != fcRoomTypeFqDto) {
                order.setOrderRoomTypeName(fcRoomTypeFqDto.getFqRoomTypeName());
            }
        } else if (ChannelSource.XC.equals(channelSource)) {
            CtripRoomTypeMapping ctripRoomTypeMapping = this.ctripRoomTypeMappingDao.selectRoomTypeByHotelIdAndRoomTypeId(order.getOTAHotelId(), order.getOTARoomTypeId());
            if (null != ctripRoomTypeMapping) {
                order.setOrderRoomTypeName(ctripRoomTypeMapping.getTomRoomTypeName());
            }
        } else if (ChannelSource.ZH.equals(channelSource)) {
            JointWisdomInnRoomMappingDto jointWisdomInnRoomMappingDto = this.jointWisdomInnRoomDao.selectRoomMappingByInnIdAndRoomTypeId(order);
            if (null != jointWisdomInnRoomMappingDto) {
                order.setOrderRoomTypeName(jointWisdomInnRoomMappingDto.getRoomTypeName());
            }
        }
        //设置渠道来源
        order.setChannelSource(channelSource);
        order.setOrderTime(new Date());
        //设置订单总价
        order.setTotalPrice(OrderMethodHelper.getTotalPrice(order));
        //设置订单号
        order.setOrderCode(OrderMethodHelper.getOrderCode());
        //保存价格比例10%
        order.setPercent(percent);
        //算成本价与OTA收益 成本价 = 总价 * （1-比例）
        if (UsedPriceModel.MAI.equals(usedPriceModel)) {
            order.setCostPrice(order.getTotalPrice().multiply((new BigDecimal(1).subtract(percent))));
        } else {
            order.setCostPrice(order.getTotalPrice());
        }
        order.setUsedPriceModel(usedPriceModel);
        //不展示ota佣金
//        order.setOTAPrice(order.getTotalPrice().multiply(otaInnOtaDto.getPriceModelValue()).subtract(order.getTotalPrice()));
        order.setCompanyId(otaInnOtaDto.getCompanyId());
        //记录日志
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), OrderStatus.ACCEPT, OrderStatus.ACCEPT, order.getFeeStatus(), JacksonUtil.obj2json(order), null, order.getInnId(), order.getInnCode(), "创建toms订单"));
        //创建订单
        this.orderDao.insertOrder(order);
        //创建每日价格信息
        this.dailyInfosDao.insertDailyInfos(order.dealDailyInfosMethod(order));
        //创建入住人信息
        this.orderGuestsDao.insertOrderGuests(order);
        //判断订单是否为信用住
        if (PaymentType.CREDIT.equals(order.getPaymentType())) {
            logger.info("淘宝信用住订单，执行付款操作" + order.getChannelOrderCode());
            //信用住需要下单到oms
            try {
                return payBackDealMethod(order, new UserInfo(), OtaType.TB.name());
            } catch (Exception e) {
                result.setSuccess(false);
                result.setMessage("信用住预定，系统异常" + e);
                return result;
            }
        } else {
            result.setSuccess(true);
            result.setMessage("创建订单成功");
        }
        return result;
    }

    private BigDecimal getOtaPercent(Company company, UsedPriceModel usedPriceModel) {
        BigDecimal percent = BigDecimal.ZERO;
        OtaCommissionPercentDto commission = otaCommissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), usedPriceModel.name()));
        if (null != commission) {
            percent = BigDecimal.valueOf(commission.getCommissionPercent());
        }
        return TomsUtil.getPercent(percent);
    }

    @Override
//    @Log(descr = "取消订单")
    public Map<String, Object> cancelOrder(String xmlStr, ChannelSource channelSource) throws Exception {
        Map<String, Object> result = new HashMap<>();
        logger.info("取消订单传入xml=》" + xmlStr);
        //解析取消订单的xml
        Order order1 = XmlDeal.getOrder(xmlStr);
        //验证此订单是否存在
        Order order = new Order();
        if (StringUtils.isNotEmpty(order1.getId())) {
            order = orderDao.selectOrderByIdAndChannelSource(order1.getId(), channelSource);
        } else {
            order = orderDao.selectOrderByChannelOrderCodeAndSource(order1);
        }
        logger.info("取消订单号为orderCode=" + order.getChannelOrderCode());
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CANCEL_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), JacksonUtil.obj2json(order), null, order.getInnId(), order.getInnCode(), "取消订单传入参数"));
        if (null == order) {
            return new JsonModel(false, "订单不存在");
        }
        order.setReason(XmlDeal.getOrder(xmlStr).getReason());
        JsonModel jsonModel = cancelOrderMethod(order);
        result.put("status", jsonModel.isSuccess());
        result.put("message", jsonModel.getMessage());
        if (PaymentType.CREDIT.equals(order.getPaymentType())) {
            result.put("orderId", order.getId());
        }
        return result;
    }

    /**
     * 取消订单
     *
     * @param order
     * @return
     * @throws Exception
     */
    public JsonModel cancelOrderMethod(Order order) throws Exception {
        OrderStatus beforeOrderStatus = order.getOrderStatus();
        order.setOrderStatus(OrderStatus.CANCEL_ORDER);
        //判断订单是否需要同步OMS,条件根据订单是否付款
        if (!order.getFeeStatus().equals(FeeStatus.NOT_PAY) || ChannelSource.FC.equals(order.getChannelSource())) {
            // 查询调用的url
            Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.CANCEL_ORDER.name());
            //查询公司信息
            OrderParamDto orderParamDto = this.orderDao.selectOrderById(order.getId());
            Company company = this.companyDao.selectCompanyById(orderParamDto.getCompanyId());
            if (null != dictionary) {
                //发送请求
                logger.info("oms取消订单传递参数=>" + order.toCancelOrderParam(order, company).toString());
                MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CANCEL_ORDER, new OrderLogData(order.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), order.toCancelOrderParam(order, company).toString(), null, orderParamDto.getInnId(), orderParamDto.getInnCode(), "取消订单，调用oms接口，传递参数"));
                String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, company));
                logger.info("调用OMS取消订单的返回值=>" + respose.toString());
                MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CANCEL_ORDER, new OrderLogData(order.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), order.toCancelOrderParam(order, company).toString(), respose.toString(), orderParamDto.getInnId(), orderParamDto.getInnCode(), "取消订单，调用oms接口，oms返回值"));
                JSONObject jsonObject = JSONObject.fromObject(respose);
                if (!jsonObject.get("status").equals(200)) {
                    return new JsonModel(false, jsonObject.get("status").toString() + ":" + jsonObject.get("message"));
                } else {
                    //同步成功后在修改数据库
                    this.orderDao.updateOrderStatusAndReason(order);
                    MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CANCEL_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), null, null, order.getInnId(), order.getInnCode(), "取消订单成功后修改toms订单状态"));
                    //写入操作记录
                    this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), beforeOrderStatus, OrderStatus.CANCEL_ORDER, "取消订单接口", ChannelSource.TAOBAO.name()));
                }
            }
        } else {
            this.orderDao.updateOrderStatusAndReason(order);
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CANCEL_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), null, null, order.getInnId(), order.getInnCode(), "取消订单成功后修改toms订单状态"));
        }
//        businLog.setDescr(logStr + "取消的订单信息=>" + order.toString());
//        //保存日志
//        businLogClient.save(businLog);
        return new JsonModel(true, "取消订单成功");
    }

    @Override
//    @Log(descr = "付款成功回调")
    public JsonModel paymentSuccessCallBack(String xmlStr, ChannelSource channelSource) throws Exception {
        Order orderXml = XmlDeal.getOrder(xmlStr);
        try {
            //日志
            String logStr = "付款成功回调传递参数" + xmlStr;
//        businLog.setDescr(logStr);
//        businLogClient.save(businLog);

            //获取订单号，判断订单是否存在
            Order order = this.orderDao.selectOrderByChannelOrderCodeAndSource(orderXml);
            logger.info("付款订单号orderCode=" + order.getChannelOrderCode());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xmlStr, null, order.getInnId(), order.getInnCode(), "付款通知传递参数"));
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
                OrderStatus beforeOrderStatus = order.getOrderStatus();
                //手动下单,手动下单修改订单状态为待处理
                order.setOrderStatus(OrderStatus.NOT_DEAL);
                //待处理订单写入付款金额和付款码
                order.setFeeStatus(FeeStatus.PAID);
                this.orderDao.updateOrderStatusAndFeeStatus(order);
                this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.NOT_DEAL, "手动下单", ChannelSource.TAOBAO.name()));
                //保存日志
                MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), null, null, order.getInnId(), order.getInnCode(), "下单到oms，设置为手动下单"));
                return new JsonModel(true, "付款成功");
            }
        } catch (Exception e) {
            MessageCenterUtils.sendWeiXin("付款异常：渠道：" + channelSource + " 订单号为：" + orderXml.getChannelOrderCode());
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
    public JsonModel payBackDealMethod(Order order, UserInfo currentUser, String otaType) throws Exception {
        //组装order参数
        order = packageOrder(order);
        OrderStatus beforeOrderStatus = order.getOrderStatus();
        //判断订单是否同步OMS
        if (order.getFeeStatus().equals(FeeStatus.NOT_PAY) || order.getOrderStatus().equals(OrderStatus.CONFIM_AND_ORDER)) {

            //查询字典表中同步OMS需要的数据
            Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.CREATE_ORDER.name());
            //判断是否为异步下单
            if (1 == dictionary.getWeatherAsynchronous()) {
                dictionary.setUrl(dictionary.getAsynchronousUrl());
            }
            //查询当前酒店以什么模式发布
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());

            OtaInfoRefDto otaInfo = this.otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(order.getCompanyId(), otaInnOtaDto.getOtaInfoId());
            //查询客栈信息
            BangInnDto bangInn = this.bangInnDao.selectBangInnByTBHotelId(order.getOTAHotelId(), otaInfo.getOtaInfoId(), order.getCompanyId());
            if (null == bangInn) {
                logger.info("绑定客栈不存在" + order.getOTAHotelId());
                return new JsonModel(false, "绑定客栈不存在");
            }
            //公司信息
            Company company = this.companyDao.selectCompanyById(order.getCompanyId());
            //判断当前客栈发布模式
            if (otaInnOtaDto.getsJiaModel().equals("DI")) {
                order.setAccountId(bangInn.getAccountIdDi());
            } else {
                order.setAccountId(bangInn.getAccountId());
            }
            //封装订单房型名称信息
            order = packageOrderRoomTypeName(order);
            //判断oms价格与下单价格是否一致，不一致不同步oms
            //1.查询当前订单上架的价格模式,如果是卖转低，进行价格匹配验证,并需改下单到oms的总价和每日房价
            Map<String, Object> objectMap = dealMAIToDIMethod(order, currentUser, otaInfo, company);
            if (true == (Boolean) objectMap.get("status")) {
                order = (Order) objectMap.get("order");
            } else {
                return (JsonModel) objectMap.get("jsonModel");
            }
            logger.info("OMS下单接口传递参数=>" + order.toOrderParamDto(order, company).toString());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), order.toOrderParamDto(order, company).toString(), null, order.getInnId(), order.getInnCode(), "下单到oms，传递参数"));
            String respose = "";
            JSONObject jsonObject = null;
            try {
                respose = HttpClientUtil.httpPostOrder(dictionary.getUrl(), order.toOrderParamDto(order, company));
                jsonObject = JSONObject.fromObject(respose);
            } catch (Exception e) {
                order.setOrderStatus(OrderStatus.REFUSE);
                order.setFeeStatus(FeeStatus.PAID);
                JsonModel resultJson = dealCancelOrder(order, currentUser, otaInfo);
                MessageCenterUtils.sendWeiXin("付款成功回调时，调用oms下单接口异常，订单号为：" + order.getChannelOrderCode());
                MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), order.toOrderParamDto(order, company).toString(), null, order.getInnId(), order.getInnCode(), "下单到oms，传递参数"));
                return resultJson;
            }
            logger.info("OMS接口响应=>" + respose + " 订单号：" + order.getChannelOrderCode());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), order.toOrderParamDto(order, company).toString(), respose, order.getInnId(), order.getInnCode(), "下单到oms，返回值"));
            if (!jsonObject.get("status").equals(200)) {
                order.setOrderStatus(OrderStatus.REFUSE);
                order.setFeeStatus(FeeStatus.NOT_PAY);
                JsonModel jsonModel = dealCancelOrder(order, currentUser, otaInfo);
                jsonModel.setMessage(jsonObject.get("status") + ":" + jsonObject.get("message"));
                return jsonModel;
            } else {
                logger.info("oms返回oms订单号为：" + jsonObject.get("orderNo"));
                order.setOmsOrderCode((String) jsonObject.get("orderNo"));
                if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
                    //同步
                    //判断当前配置下单请求是否为同步
                    if (1 != dictionary.getWeatherAsynchronous()) {
                        logger.info("同步下单，单号为：" + order.getChannelOrderCode());
                        return pushSuccessToTB(order, currentUser, otaInfo);
                    } else {
                        logger.info("异步下单，单号为：" + order.getChannelOrderCode());
                        order.setFeeStatus(FeeStatus.PAID);
                        order.setOrderStatus(OrderStatus.DEALING);
                        this.orderDao.updateOrderStatusAndFeeStatus(order);
                        return new JsonModel(true, "付款成功");
                    }
                } else if (ChannelSource.FC.equals(order.getChannelSource()) || ChannelSource.XC.equals(order.getChannelSource()) || ChannelSource.ZH.equals(order.getChannelSource())) {
                    //天下房仓返回创建订单成功
                    order.setOrderStatus(OrderStatus.ACCEPT);
                    order.setFeeStatus(FeeStatus.PAID);
                    this.orderDao.updateOrderStatusAndFeeStatus(order);
                    MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), order.toOrderParamDto(order, company).toString(), respose, order.getInnId(), order.getInnCode(), "下单到oms，toms更新订单状态"));
                    return new JsonModel(true, "创建订单成功");
                }
            }

        }
        return new JsonModel(true, "付款成功");
    }

    /**
     * 处理卖转低模式
     *
     * @param order
     * @param currentUser
     * @param otaInfo
     * @param company
     * @return
     * @throws IOException
     */
    private Map<String, Object> dealMAIToDIMethod(Order order, UserInfo currentUser, OtaInfoRefDto otaInfo, Company company) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel())) {
            BigDecimal percent = order.getPercent();
            //查询验证房型的价格url
            Dictionary dictionaryRoom = this.dictionaryDao.selectDictionaryByType(DictionaryType.ROOM_DAY_INFO.name());
            //验证价格
            boolean flag = true;
            BigDecimal omsTotalPrice = BigDecimal.ZERO;
            if (ArrayUtils.isNotEmpty(order.getDailyInfoses().toArray())) {
                for (DailyInfos dailyInfos : order.getDailyInfoses()) {
                    logger.info("调用oms获取每日入住信息传入参数==>" + order.toRoomAvail(company, order, dailyInfos).toString());
                    //分别通过房型调用oms价格
                    String response = HttpClientUtil.httpGetRoomAvail(dictionaryRoom.getUrl(), order.toRoomAvail(company, order, dailyInfos));
                    JSONObject jsonObject = JSONObject.fromObject(response);
                    logger.info("调用oms获取每日入住信息返回值==>" + jsonObject.toString());
                    if (!jsonObject.get("status").equals(200)) {
                        flag = false;
                        break;
                    } else {
                        //验证价格是否一致
                        RoomDetail room = (RoomDetail) JSONObject.toBean(jsonObject.getJSONObject("data"), RoomDetail.class);
                        //判断oms返回价格*（1-比例） == 订单传入价格
                        BigDecimal orderPrice = dailyInfos.getPrice();
                        BigDecimal omsPrice = BigDecimal.valueOf(room.getRoomPrice()).multiply((new BigDecimal(1).subtract(percent)));
                        logger.info("订单每日价格为" + dailyInfos.getDay() + "====>" + orderPrice);
                        logger.info("oms每日价格为" + dailyInfos.getDay() + "====>" + omsPrice);
                        logger.info("验证每日价格对比值" + orderPrice.subtract(omsPrice).abs() + "对比" + 2 + "结果为：" + orderPrice.subtract(omsPrice).abs().compareTo(BigDecimal.valueOf(2)));
                        if (orderPrice.subtract(omsPrice).abs().compareTo(BigDecimal.valueOf(2)) != -1) {
                            flag = false;
                            break;
                        } else {
                            //设置每日价格
                            dailyInfos.setPrice(BigDecimal.valueOf(room.getRoomPrice()));
                            omsTotalPrice = omsTotalPrice.add(BigDecimal.valueOf(room.getRoomPrice()));
                        }
                    }

                }
                //设置订单总价
                //oms总价格需要*房间数量
                omsTotalPrice = omsTotalPrice.multiply(BigDecimal.valueOf(order.getHomeAmount()));
                //1.验证订单总价是否一致
                logger.info("订单总价为：" + order.getTotalPrice() + "   oms总价为：" + omsTotalPrice);
                logger.info("oms总价价格比例过后价格=>" + omsTotalPrice.multiply((new BigDecimal(1).subtract(percent))));
                logger.info("总价只差为：" + order.getTotalPrice().subtract(omsTotalPrice.multiply((new BigDecimal(1).subtract(percent)))).abs());
                logger.info("总价差的绝对值比较值为：" + order.getTotalPrice().subtract(omsTotalPrice.multiply((new BigDecimal(1).subtract(percent)))).abs().compareTo(BigDecimal.valueOf(order.getDailyInfoses().size() * order.getHomeAmount())));
                if (order.getTotalPrice().subtract(omsTotalPrice.multiply((new BigDecimal(1).subtract(percent)))).abs().compareTo(BigDecimal.valueOf(order.getDailyInfoses().size() * order.getHomeAmount())) == -1) {
                    order.setTotalPrice(omsTotalPrice);
                } else {
                    flag = false;
                }
            }
            //如果验证价格不一致，返回付款失败，价格与实际价格不一致，并调用淘宝取消订单接口
            if (!flag) {
                logger.info("订单付款失败，订单中的价格与实际价格不一致，订单号为orderCode=" + order.getChannelOrderCode());
                order.setOrderStatus(OrderStatus.REFUSE);
                order.setFeeStatus(FeeStatus.NOT_PAY);
                JsonModel jsonModel = dealCancelOrder(order, currentUser, otaInfo);
                jsonModel.setMessage("付款失败，订单中的价格与实际价格不一致");
                result.put("order", order);
                result.put("jsonModel", jsonModel);
                result.put("status", false);
                return result;
            }
        }
        result.put("status", true);
        result.put("order", order);
        return result;
    }

    /**
     * 封装订单房型信息
     *
     * @param order
     * @return
     */
    private Order packageOrderRoomTypeName(Order order) {
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            OtaBangInnRoomDto otaBangInnRoomDtos = this.bangInnRoomDao.selectBangInnRoomByInnIdAndRoomTypeId(order.getInnId(), Integer.parseInt(order.getRoomTypeId()), order.getCompanyId());
            if (null == otaBangInnRoomDtos) {
                logger.info("查询订单房型信息出错,订单号为：" + order.getChannelOrderCode());
                throw new TomsRuntimeException("查询订单房型信息出错,订单号为：" + order.getChannelOrderCode());
            }
            order.setRoomTypeName(otaBangInnRoomDtos.getRoomTypeName());
        } else if (ChannelSource.FC.equals(order.getChannelSource())) {
            OtaInfoRefDto otaInfoRefDto = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), OtaType.FC.name());
            FcRoomTypeFqDto roomTypeFqInnIdRoomIdOtaInfoId = this.fcRoomTypeFqDao.findRoomTypeFqInnIdRoomIdOtaInfoId(order.getInnId(), Integer.parseInt(order.getRoomTypeId()), otaInfoRefDto.getOtaInfoId(), order.getCompanyId());
            if (null == roomTypeFqInnIdRoomIdOtaInfoId) {
                logger.info("查询订单房型信息出错,订单号为：" + order.getChannelOrderCode());
                throw new TomsRuntimeException("查询订单房型信息出错,订单号为：" + order.getChannelOrderCode());
            } else {
                order.setRoomTypeName(roomTypeFqInnIdRoomIdOtaInfoId.getFqRoomTypeName());
            }
        } else if (ChannelSource.XC.equals(order.getChannelSource())) {
            CtripRoomTypeMapping ctripRoomTypeMapping = this.ctripRoomTypeMappingDao.selectRoomTypeByHotelIdAndRoomTypeId(order.getOTAHotelId(), order.getOTARoomTypeId());
            if (null == ctripRoomTypeMapping) {
                logger.info("查询订单房型信息出错,订单号为：" + order.getChannelOrderCode());
                throw new TomsRuntimeException("查询订单房型信息出错,订单号为：" + order.getChannelOrderCode());
            } else {
                order.setRoomTypeName(ctripRoomTypeMapping.getTomRoomTypeName());
            }
        }
        return order;
    }

    /**
     * 组装订单参数
     *
     * @param order
     * @return
     */
    private Order packageOrder(Order order) {
        // 房态更新时间
        if (StringUtils.isNotEmpty(order.getOTAGid())) {
            OtaInnRoomTypeGoodsDto roomTypeGoodsDto = this.otaInnRoomTypeGoodsDao.findRoomTypeByRid(Long.parseLong(order.getOTAGid()));
            if (null != roomTypeGoodsDto) {
                order.setOrderCreateTime(roomTypeGoodsDto.getProductDate());
            }
        }
        //获取入住人信息
        if (null == order.getOrderGuestses() || ArrayUtils.isEmpty(order.getOrderGuestses().toArray())) {
            List<OrderGuests> orderGuestses = this.orderGuestsDao.selectOrderGuestByOrderId(order.getId());
            order.setOrderGuestses(orderGuestses);
        }
        //获取每日房价信息
        if (null == order.getDailyInfoses() || ArrayUtils.isEmpty(order.getDailyInfoses().toArray())) {
            List<DailyInfos> dailyInfoses = this.dailyInfosDao.selectDailyInfoByOrderId(order.getId());
            order.setDailyInfoses(dailyInfoses);
        }
        return order;
    }

    /**
     * 推送确认订单到淘宝
     *
     * @param order
     * @param currentUser
     * @param otaInfo
     * @return
     */
    private JsonModel pushSuccessToTB(Order order, UserInfo currentUser, OtaInfoRefDto otaInfo) {
        logger.info("淘宝更新订单传入订单号为orderCode=" + order.getChannelOrderCode());
        OrderStatus beforeOrderStatus = order.getOrderStatus();
        if (PaymentType.CREDIT.equals(order.getPaymentType())) {
            //同步成功后在修改数据库
            order.setOrderStatus(OrderStatus.ACCEPT);
            order.setFeeStatus(FeeStatus.PAID);
            this.orderDao.updateOrderStatusAndFeeStatus(order);
            return new JsonModel(true, "付款成功");
        }
        //更新淘宝订单状态,判断淘宝是否开启更新时间
        //同步成功后在修改数据库
        order.setOrderStatus(OrderStatus.ACCEPT);
        order.setFeeStatus(FeeStatus.PAID);
        this.orderDao.updateOrderStatusAndFeeStatus(order);
        //记录操作日志
        this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.ACCEPT, order.getOrderStatus(), "淘宝付款成功", currentUser.getId()));
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), order.toString(), null, order.getInnId(), order.getInnCode(), "下单到oms，toms更新订单状态"));
        if (ResourceBundleUtil.getBoolean("taobao.time.open")) {
            String result = TBXHotelUtilPromotion.orderUpdate(order, otaInfo, 2L);
            logger.info("淘宝更新订单返回值=>" + result);
            if (null != result && result.equals("success")) {
                return new JsonModel(true, "付款成功");
            } else {
                return new JsonModel(false, "调用淘宝更新接口出错");
            }
        } else {
            return new JsonModel(true, "付款成功");
        }

    }

    /**
     * 调用渠道取消订单接口
     *
     * @param order
     * @param currentUser
     * @param otaInfo
     * @return
     */
    private JsonModel dealCancelOrder(Order order, UserInfo currentUser, OtaInfoRefDto otaInfo) {
        this.orderDao.updateOrderStatusAndFeeStatus(order);
        if (ChannelSource.TAOBAO.equals(order.getChannelSource()) && ResourceBundleUtil.getBoolean("taobao.time.open")) {
            logger.info("淘宝更新订单传入订单号为orderCode=" + order.getChannelOrderCode());
            String result = TBXHotelUtilPromotion.orderUpdate(order, otaInfo, 1L);
            logger.info("淘宝取消订单接口返回值=>" + result);
            if (null != result && result.equals("success")) {
                //记录操作日志
                this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.REFUSE, "淘宝下单到oms响应失败", null == currentUser.getId() ? ChannelSource.TAOBAO.name() : currentUser.getId()));
            } else {
                //如果淘宝返回值为空或者false，记录该订单为异常单
                ExceptionOrder exceptionOrder = new ExceptionOrder();
                exceptionOrder.setOrderId(order.getId());
                this.exceptionOrderDao.insertExceptionOrderByException(exceptionOrder);
            }
            return new JsonModel(false, "OMS系统异常");
        } else if (ChannelSource.FC.equals(order.getChannelSource())) {
            //如果付款失败，天下房仓返回创建订单失败
            return new JsonModel(false, "OMS系统异常，创建订单失败");
        } else if (ChannelSource.XC.equals(order.getChannelSource())) {
            return new JsonModel(false, "创建订单失败");
        } else if (ChannelSource.ZH.equals(order.getChannelSource())) {
            return new JsonModel(false, "OMS系统异常，创建订单失败");
        }
        return new JsonModel(false, "OMS系统异常");
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
        OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
        //更新淘宝订单状态
        OtaInfoRefDto otaInfo = this.otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(order.getCompanyId(), otaInnOtaDto.getOtaInfoId());
        logger.info("淘宝更新订单传入订单号为orderCode=" + order.getChannelOrderCode());
        String result = TBXHotelUtilPromotion.orderUpdate(order, otaInfo, parm);
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
        logger.info("查询订单状态传入参数=>" + xmlStr);
        Map<String, String> result = new HashMap<>();
        //解析查询订单状态
        Order order1 = XmlDeal.getOrder(xmlStr);
        //验证此订单是否存在
        Order order = orderDao.selectOrderByChannelOrderCodeAndSource(order1);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.SEARCH_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xmlStr, null, order.getInnId(), order.getInnCode(), "查询订单状态"));
        logger.info("查询订单状态订单单号为orderCode=" + order.getChannelOrderCode());
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
            String omsOrderStatus = (String) jsonObject.get("orderStatus");
            if (omsOrderStatus.equals("0")) {
                result.put("status", "100");
            } else if (omsOrderStatus.equals("1")) {
                result.put("status", "1");
            } else if (omsOrderStatus.equals("2")) {
                result.put("status", "6");
            } else if (omsOrderStatus.equals("3")) {
                if (PaymentType.CREDIT.equals(order.getPaymentType())) {
                    result.put("status", "7");
                } else {
                    result.put("status", "4");
                }
            } else if (omsOrderStatus.equals("4")) {
                result.put("status", "6");
            } else if (omsOrderStatus.equals("5")) {
                result.put("status", "3");
            } else if (omsOrderStatus.equals("6")) {
                result.put("status", "5");

            } else if (omsOrderStatus.equals("7")) {
                result.put("status", "9");

            } else if (omsOrderStatus.equals("8")) {
                result.put("status", "8");
            } else {
                throw new TomsRuntimeException("OMS内部错误");
            }
            if (ResourceBundleUtil.getBoolean("taobao.time.open")) {
                result.put("message", order.getId());
            } else {
                if (StringUtils.isNotEmpty(order.getOmsOrderCode())) {
                    result.put("message", order.getId());
                } else {
                    result.put("message", "");
                }
            }
            result.put("code", "0");
            //判断订单是否为信用住
            if (PaymentType.CREDIT.equals(order.getPaymentType())) {
                result.put("taobaoOrderId", order.getChannelOrderCode());
                result.put("orderId", order.getId());
            }
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
    public String getOrderStatusMethod(Order order) throws Exception {
        //查询调用的url
        Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.ORDER_STATUS.name());
        //查询公司信息,根据订单
        OrderParamDto orderParamDto = this.orderDao.selectOrderById(order.getId());
        Company company = this.companyDao.selectCompanyById(orderParamDto.getCompanyId());
        logger.info("查询订单传递参数=>" + order.toCancelOrderParam(order, company).toString());
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.SEARCH_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), order.toCancelOrderParam(order, company).toString(), null, order.getInnId(), order.getInnCode(), "查询订单状态，oms传递参数"));
        //查询OMS订单状态
        String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, company));
        logger.info("查询订单返回值=>" + respose);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.SEARCH_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), order.toCancelOrderParam(order, company).toString(), respose, order.getInnId(), order.getInnCode(), "查询订单状态，oms返回值"));
        return respose;
    }

    @Override
    public Map<String, String> dealPayBackMethod(String xmlStr, ChannelSource taobao) throws Exception {
        Map<String, String> result = new HashMap<>();
        String orderId = XmlDeal.getOrder(xmlStr).getId();
        Order order = this.orderDao.selectOrderByIdAndChannelSource(orderId, taobao);
        OrderStatus beforeOrderStatus = order.getOrderStatus();
        if (null == order) {
            result.put("status", "-400");
            result.put("message", "没有此订单！");
            return result;
        }
        //判断订单状态，只有已接收并付款订单才需要手动确认退款
        if (OrderStatus.ACCEPT.equals(order.getOrderStatus()) && FeeStatus.PAID.equals(order.getFeeStatus())) {
            //拦截申请退款的订单，将订单状态改为退款申请中
            order.setOrderStatus(OrderStatus.PAY_BACK);
            this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.PAY_BACK, "申请退款", ChannelSource.TAOBAO.name()));
        } else {
            //直接修改订单状态为已取消
            if (!OrderStatus.PAY_BACK.equals(order.getOrderStatus())) {
                order.setOrderStatus(OrderStatus.CANCEL_ORDER);
                order.setReason("买家申请退款");
            }
        }
        this.orderDao.updateOrderStatusAndReason(order);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.SEARCH_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), null, null, order.getInnId(), order.getInnCode(), "申请退款订单，订单状态"));
        result.put("status", "0");
        result.put("message", "success");
        return result;
    }

    @Override
    public List<OrderParamDto> findOrderByPage(String companyId, PageBounds pageBounds, OrderParamDto orderParamDto) {
        //处理查询时间
        orderParamDto.setBeginDate(TomsUtil.getDayBeafore(orderParamDto.getBeginDate()));
        orderParamDto.setEndDate(TomsUtil.getDayEnd(orderParamDto.getEndDate()));
        List<OrderParamDto> orderDtos = orderDao.selectOrderByPage(companyId, pageBounds, orderParamDto);
        //房型名称
        if (ArrayUtils.isNotEmpty(orderDtos.toArray())) {
            for (OrderParamDto orderDto : orderDtos) {
                List<DailyInfos> dailyInfoses = this.dailyInfosDao.selectDailyInfoByOrderId(orderDto.getId());
                orderDto.setDailyInfoses(dailyInfoses);
                //设置总价和每日价格
                if (null != orderDto.getAddPrice()) {
                    BigDecimal addTatalPirce = BigDecimal.ZERO;
                    if (ArrayUtils.isNotEmpty(dailyInfoses.toArray())) {
                        for (DailyInfos dailyInfos : dailyInfoses) {
                            if (1 == dailyInfos.getWeatherAdd()) {
                                dailyInfos.setPrice(dailyInfos.getPrice().add(orderDto.getAddPrice()));
                                addTatalPirce = addTatalPirce.add(orderDto.getAddPrice());
                            }
                        }
                    }
                    orderDto.setTotalPrice(orderDto.getTotalPrice().add(addTatalPirce));
                    orderDto.setTotalPrice(orderDto.getBasicTotalPrice());
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
        //查询客栈关联信息
        OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(orderParamDto.getOTAHotelId());
        if (null != orderParamDto) {

            List<DailyInfos> dailyInfoses = this.dailyInfosDao.selectDailyInfoByOrderId(orderParamDto.getId());
            //设置房型名称
            String roomTypeName = "";
            if (null != dailyInfoses && ArrayUtils.isNotEmpty(dailyInfoses.toArray())) {
                for (DailyInfos d : dailyInfoses) {
                    if (!roomTypeName.contains(d.getRoomTypeName())) {
                        roomTypeName += d.getRoomTypeName() + "、";
                    }
                }
            }
            orderParamDto.setRoomTypeName(roomTypeName);
            orderParamDto.setOrderRoomTypeName(roomTypeName);
            //价格策略
            if (ChannelSource.TAOBAO.equals(orderParamDto.getChannelSource())) {
                if (ArrayUtils.isNotEmpty(dailyInfoses.toArray())) {
                    for (DailyInfos dailyInfos : dailyInfoses) {
                        dailyInfos.setCostPrice(dailyInfos.getPrice().multiply(otaInnOtaDto.getPriceModelValue()));
                        //判断当前下单的价格模式
                        if (UsedPriceModel.MAI.equals(orderParamDto.getUsedPriceModel())) {
                            dailyInfos.setCostPrice(dailyInfos.getCostPrice().multiply((new BigDecimal(1).subtract(orderParamDto.getPercent()))));
                        } else {
                            dailyInfos.setCostPrice(dailyInfos.getPrice());
                        }
                    }
                }
            } else {
                if (ArrayUtils.isNotEmpty(dailyInfoses.toArray())) {
                    for (DailyInfos dailyInfos : dailyInfoses) {
                        dailyInfos.setCostPrice(dailyInfos.getPrice());
                        if (UsedPriceModel.MAI.equals(orderParamDto.getUsedPriceModel())) {
                            dailyInfos.setCostPrice(dailyInfos.getCostPrice().multiply((new BigDecimal(1).subtract(orderParamDto.getPercent()))));
                        } else {
                            dailyInfos.setCostPrice(dailyInfos.getPrice());
                        }
                    }
                }
            }
            orderParamDto.setDailyInfoses(dailyInfoses);
        }
        //设置总价和每日价格
        if (null != orderParamDto.getAddPrice()) {
            List<DailyInfos> dailyInfoses = this.dailyInfosDao.selectDailyInfoByOrderId(orderParamDto.getId());
            BigDecimal addTatalPirce = BigDecimal.ZERO;
            if (ArrayUtils.isNotEmpty(dailyInfoses.toArray())) {
                for (DailyInfos dailyInfos : dailyInfoses) {
                    if (1 == dailyInfos.getWeatherAdd()) {
                        dailyInfos.setPrice(dailyInfos.getPrice().add(orderParamDto.getAddPrice()));
                        addTatalPirce = addTatalPirce.add(orderParamDto.getAddPrice());
                    }
                }
            }
            orderParamDto.setTotalPrice(orderParamDto.getTotalPrice().add(addTatalPirce));
        }
        orderParamDto.setTotalPrice(orderParamDto.getBasicTotalPrice());
        return orderParamDto;
    }

    @Override
    public JsonModel confirmOrder(OrderParamDto order, UserInfo currentUser) throws Exception {
        JsonModel jsonModel = new JsonModel();
        OrderStatus beforeOrderStatus = order.getOrderStatus();
        //手动确认并执行下单
        //设置订单状态为确认并执行下单
        order.setOrderStatus(OrderStatus.CONFIM_AND_ORDER);
        //淘宝订单处理方法
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            jsonModel = payBackDealMethod(order, currentUser, OtaType.TB.name());
        } else if (ChannelSource.FC.equals(order.getChannelSource())) {
            jsonModel = payBackDealMethod(order, currentUser, OtaType.FC.name());

        } else if (ChannelSource.XC.equals(order.getChannelSource())) {
            jsonModel = payBackDealMethod(order, currentUser, OtaType.XC.name());
        } else if (ChannelSource.ZH.equals(order.getChannelSource())) {
            jsonModel = payBackDealMethod(order, currentUser, OtaType.ZH.name());
        } else {
            throw new TomsRuntimeException("没有找到ota渠道信息");
        }
        if (jsonModel.isSuccess()) {
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), JacksonUtil.obj2json(order), null, order.getInnId(), order.getInnCode(), "确认下单到oms"));
        }
        return jsonModel;
    }

    @Override
    public JsonModel refuesOrder(OrderParamDto order, UserInfo currentUser) throws ApiException {
        JsonModel jsonModel = new JsonModel();
        //直接拒绝订单，不同步oms，直接调用淘宝更新订单状态接口
        //1.调用淘宝更新订单接口
        OrderStatus beforeOrderStatus = order.getOrderStatus();
        order.setOrderStatus(OrderStatus.HAND_REFUSE);
        order.setReason("手动直接拒绝");
        //淘宝更新订单
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            jsonModel = TBCancelMethod(order, 1L, currentUser);
        } else {
            //更新订单
            this.orderDao.updateOrderStatusAndReason(order);
            //记录订单状态日志
            this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.NOT_DEAL, OrderStatus.HAND_REFUSE, "手动拒绝", currentUser.getId()));
            jsonModel.setSuccess(true);
            jsonModel.setMessage("成功拒绝订单");
        }
        if (jsonModel.isSuccess()) {
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), JacksonUtil.obj2json(order), null, order.getInnId(), order.getInnCode(), "手动拒绝订单"));
        }
        return jsonModel;

    }

    @Override
    public JsonModel confirmNoOrder(OrderParamDto order, UserInfo currentUser) throws ApiException {
        JsonModel jsonModel = new JsonModel();
        //确认订单，但不同步oms
        //1.修改订单状态，2.调用淘宝更新订单确认有房
        OrderStatus beforeOrderStatus = order.getOrderStatus();
        order.setOrderStatus(OrderStatus.CONFIM_NO_ORDER);
        order.setReason("确认但不执行下单");
        //淘宝更新订单
        //淘宝订单才更新
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            jsonModel = TBCancelMethod(order, 2L, currentUser);
        } else {
            //更新订单
            this.orderDao.updateOrderStatusAndReason(order);
            this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.NOT_DEAL, OrderStatus.HAND_REFUSE, "确认但是不执行下单", currentUser.getId()));
            jsonModel.setSuccess(true);
            jsonModel.setMessage("确认订单成功");
        }
        if (jsonModel.isSuccess()) {
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CREATE_ORDER_TO_OMS, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), JacksonUtil.obj2json(order), null, order.getInnId(), order.getInnCode(), "确认单不执行下单"));
        }
        return jsonModel;
    }

    @Override
    public Map<String, Object> dealHandMakeOrderRoomTypes(Order order, UserInfo userInfo) throws Exception {
        Map<String, Object> result = new HashMap<>();
        //公司信息
        Company company = this.companyDao.selectCompanyById(userInfo.getCompanyId());
        BigDecimal percent = BigDecimal.ZERO;
        //根据模式查询比例
        OtaCommissionPercentDto commission = null;
        if (1 != order.getMaiAccount()) {
            commission = this.otaCommissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), UsedPriceModel.DI.name()));
            order.setUsedPriceModel(UsedPriceModel.DI);
        } else {
            commission = this.otaCommissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), UsedPriceModel.MAI.name()));
            order.setUsedPriceModel(UsedPriceModel.MAI);
        }
        if (null != commission) {
            percent = TomsUtil.getPercent(BigDecimal.valueOf(commission.getCommissionPercent()));
        }
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

        //这里的accountId为绑定客栈的ID
        BangInnDto bangInn = bangInnDao.selectBangInnById(order.getBangInnId());
        order.setOrderInnName(bangInn.getInnName());
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
        //设置底价和卖价的accountId
        paramDto.setAccountId(bangInn.getId());
        paramDto.setMaiAccount(order.getMaiAccount());
        roomTypeInfoDto = this.roomTypeService.findRoomType(paramDto, userInfo);
        //设置比例
        order.setPercent(percent);
        //多房型下单处理订单参数
        Order hangOrder = order.makeHandOrderByRoomTypes(order, roomTypeInfoDto);
        try {

            logger.info("oms手动下单传递参数" + order.toOrderParamDto(hangOrder, company).toString());
            respose = HttpClientUtil.httpPostOrder(dictionary.getUrl(), order.toOrderParamDto(hangOrder, company));
            jsonObject = JSONObject.fromObject(respose);
        } catch (Exception e) {
            hangOrder.setOrderStatus(OrderStatus.REFUSE);
            if (null != bangInn) {
                hangOrder.setInnId(bangInn.getInnId());
            }
            this.orderDao.insertOrder(hangOrder);
            //创建每日价格信息
            this.dailyInfosDao.insertDailyInfos(hangOrder);
            //创建入住人信息
            this.orderGuestsDao.insertOrderGuests(hangOrder);
            //保存订单其他消费
            if (null != hangOrder.getOrderOtherPriceList() && CollectionUtils.isNotEmpty(hangOrder.getOrderOtherPriceList())) {
                this.orderOtherPriceDao.insertIntoOrderOtherPrice(hangOrder);
            }
            MessageCenterUtils.sendWeiXin("手动下单，调用oms下单接口异常，订单号为：" + order.getChannelOrderCode());
            result.put("status", false);
            result.put("message", "同步oms失败");
        }
        logger.info("OMS接口响应=>" + respose);
        if (!jsonObject.get("status").equals(200)) {
            //手动下单失败保存手动下单订单
            if (null != bangInn) {
                hangOrder.setInnId(bangInn.getInnId());
            }
            //设置订单状态为拒绝
            hangOrder.setOrderStatus(OrderStatus.REFUSE);
            result.put("status", false);
            result.put("message", jsonObject.get("message"));
        } else {
            //将oms订单号保存
            hangOrder.setOmsOrderCode((String) jsonObject.get("orderNo"));
            //设置innId
            if (null != bangInn) {
                hangOrder.setInnId(bangInn.getInnId());
                //同步oms订单成功，保存订单到toms
                result.put("status", true);
                result.put("message", "下单成功");
            } else {
                result.put("status", false);
                result.put("message", "下单失败，客栈不存在");
            }
        }
        this.orderDao.insertOrder(hangOrder);
        //创建每日价格信息
        this.dailyInfosDao.insertDailyInfos(hangOrder);
        //创建入住人信息
        this.orderGuestsDao.insertOrderGuests(hangOrder);
        //保存订单其他消费
        if (null != hangOrder.getOrderOtherPriceList() && CollectionUtils.isNotEmpty(hangOrder.getOrderOtherPriceList())) {
            this.orderOtherPriceDao.insertIntoOrderOtherPrice(hangOrder);
        }
        return result;
    }

    @Override
    public Map<String, Object> dealHandMakeOrder(Order order, UserInfo userInfo) throws Exception {
        Map<String, Object> result = new HashMap<>();
        //公司信息
        Company company = this.companyDao.selectCompanyById(userInfo.getCompanyId());
        BigDecimal percent = BigDecimal.ZERO;
        //根据模式查询比例
        OtaCommissionPercentDto commission = null;
        if (1 != order.getMaiAccount()) {
            commission = this.otaCommissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), UsedPriceModel.DI.name()));
            order.setUsedPriceModel(UsedPriceModel.DI);
        } else {
            commission = this.otaCommissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), UsedPriceModel.MAI.name()));
            order.setUsedPriceModel(UsedPriceModel.MAI);
        }
        if (null != commission) {
            percent = TomsUtil.getPercent(BigDecimal.valueOf(commission.getCommissionPercent()));
        }
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

        //这里的accountId为绑定客栈的ID
        BangInnDto bangInn = bangInnDao.selectBangInnById(order.getBangInnId());
        order.setOrderInnName(bangInn.getInnName());
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
        //设置底价和卖价的accountId
        paramDto.setAccountId(bangInn.getId());
        paramDto.setMaiAccount(order.getMaiAccount());
        roomTypeInfoDto = this.roomTypeService.findRoomType(paramDto, userInfo);
        //设置比例
        order.setPercent(percent);
        Order hangOrder = order.makeHandOrder(order, roomTypeInfoDto);
        try {

            logger.info("oms手动下单传递参数" + order.toOrderParamDto(hangOrder, company).toString());
            respose = HttpClientUtil.httpPostOrder(dictionary.getUrl(), order.toOrderParamDto(hangOrder, company));
            jsonObject = JSONObject.fromObject(respose);
        } catch (Exception e) {
            hangOrder.setOrderStatus(OrderStatus.REFUSE);
            if (null != bangInn) {
                hangOrder.setInnId(bangInn.getInnId());
            }
            this.orderDao.insertOrder(hangOrder);
            //创建每日价格信息
            this.dailyInfosDao.insertDailyInfos(hangOrder);
            //创建入住人信息
            this.orderGuestsDao.insertOrderGuests(hangOrder);
            MessageCenterUtils.sendWeiXin("手动下单，调用oms下单接口异常，订单号为：" + order.getChannelOrderCode());
            result.put("status", false);
            result.put("message", "同步oms失败");
        }
        logger.info("OMS接口响应=>" + respose);
        if (!jsonObject.get("status").equals(200)) {
            //手动下单失败保存手动下单订单
            if (null != bangInn) {
                hangOrder.setInnId(bangInn.getInnId());
            }
            //设置订单状态为拒绝
            hangOrder.setOrderStatus(OrderStatus.REFUSE);
            result.put("status", false);
            result.put("message", jsonObject.get("message"));
        } else {
            //将oms订单号保存
            hangOrder.setOmsOrderCode((String) jsonObject.get("orderNo"));
            //设置innId
            if (null != bangInn) {
                hangOrder.setInnId(bangInn.getInnId());
                //同步oms订单成功，保存订单到toms
                result.put("status", true);
                result.put("message", "下单成功");
            } else {
                result.put("status", false);
                result.put("message", "下单失败，客栈不存在");
            }
        }
        this.orderDao.insertOrder(hangOrder);
        //创建每日价格信息
        this.dailyInfosDao.insertDailyInfos(hangOrder);
        //创建入住人信息
        this.orderGuestsDao.insertOrderGuests(hangOrder);
        return result;
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
        //这里的accountId为绑定客栈的ID
        BangInnDto bangInnDto = this.bangInnDao.selectBangInnById(order.getBangInnId());
        paramDto.setAccountId(bangInnDto.getId());
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
            List<RoomStatusDetail> roomStatus = roomType.getRoomStatus();
            if (ArrayUtils.isNotEmpty(roomStatus.toArray())) {
                outer:
                for (RoomStatusDetail roomStatusDetail : roomStatus) {
                    if (ArrayUtils.isNotEmpty(roomStatusDetail.getRoomDetail().toArray())) {
                        RoomTypeInfoDto roomTypeInfoDto = new RoomTypeInfoDto();
                        for (RoomDetail roomDetail : roomStatusDetail.getRoomDetail()) {
                            if (null != roomDetail.getRoomNum()) {
                                if (0 != roomDetail.getRoomNum()) {
                                    roomTypeInfoDto.setRoomTypeId(roomStatusDetail.getRoomTypeId() + "");
                                    roomTypeInfoDto.setRoomTypeName(roomStatusDetail.getRoomTypeName());
                                    if (null != roomTypeInfoDto.getMaxRoomNum() && roomTypeInfoDto.getMaxRoomNum() > roomDetail.getRoomNum()) {
                                        roomTypeInfoDto.setMaxRoomNum(roomDetail.getRoomNum());
                                    } else if (null == roomTypeInfoDto.getMaxRoomNum()) {
                                        roomTypeInfoDto.setMaxRoomNum(roomDetail.getRoomNum());
                                    }
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
        OrderStatus beforeOrderStatus = order.getOrderStatus();
        Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.CANCEL_ORDER.name());
        OrderParamDto orderParamDto = this.orderDao.selectOrderById(order.getId());
        //查询公司信息
        Company company = this.companyDao.selectCompanyById(orderParamDto.getCompanyId());
        if (null != dictionary) {
            //发送请求
            logger.info("oms取消订单传递参数=>" + order.toCancelOrderParam(order, company).toString());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.PAY_BACK, new OrderLogData(order.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), beforeOrderStatus, orderParamDto.getOrderStatus(), orderParamDto.getFeeStatus(), order.toCancelOrderParam(order, company).toString(), null, orderParamDto.getInnId(), orderParamDto.getInnCode(), "同意退款,请求oms参数"));
            String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, company));
            JSONObject jsonObject = JSONObject.fromObject(respose);
            logger.info("oms取消订单返回值=>" + jsonObject.toString());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.PAY_BACK, new OrderLogData(order.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), beforeOrderStatus, orderParamDto.getOrderStatus(), orderParamDto.getFeeStatus(), order.toCancelOrderParam(order, company).toString(), respose.toString(), orderParamDto.getInnId(), orderParamDto.getInnCode(), "同意退款,oms返回值"));
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
                MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.PAY_BACK, new OrderLogData(order.getChannelSource(), orderParamDto.getChannelOrderCode(), orderParamDto.getId(), orderParamDto.getOmsOrderCode(), beforeOrderStatus, OrderStatus.CANCEL_ORDER, orderParamDto.getFeeStatus(), order.toCancelOrderParam(order, company).toString(), respose.toString(), orderParamDto.getInnId(), orderParamDto.getInnCode(), "同意退款,oms返回值"));
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
        OrderStatus beforeOrderStatus = order.getOrderStatus();
        order.setOrderStatus(OrderStatus.ACCEPT);
        this.orderDao.updateOrderStatusAndReason(order);
        this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), OrderStatus.PAY_BACK, order.getOrderStatus(), "拒绝退款", currentUser.getId()));
        result.setMessage("拒绝申请成功");
        result.setSuccess(true);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.PAY_BACK, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), beforeOrderStatus, order.getOrderStatus(), order.getFeeStatus(), JacksonUtil.obj2json(order), null, order.getInnId(), order.getInnCode(), "拒绝退款,toms订单状态变更"));
        return result;
    }

    @Override
    public Map<String, Object> createFcHotelOrder(String xml) throws Exception {
        logger.info("天下房仓创建订单传递参数=>" + xml);
        Map<String, Object> result = new HashMap<>();
        //解析xml
        Order order = XmlDeal.getFcCreateOrder(xml);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, null, order.getInnId(), order.getInnCode(), "天下房仓创建订单传入参数"));
        //创建订单
        //1.天下房仓需要查询otahotelid
        OtaInfoRefDto otaInfo = this.otaInfoDao.selectCompanyIdByAppKey(ResourceBundleUtil.getString("fc.appKey"), ResourceBundleUtil.getString("fc.appSecret"));
        OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByInnIdAndCompanyIdAndOtaInfoId(order.getInnId(), otaInfo.getCompanyId(), otaInfo.getOtaInfoId());
        order.setOTAHotelId(otaInnOtaDto.getWgHid());
        createOrderMethod(order.getChannelSource(), order);
        //天下房仓创建订单，同步oms
        //查询当前公司设置的下单是自动或者手动
        //1.判断当前订单客栈属于哪个公司，查找公司设置的下单规则
        OrderConfig orderConfig = new OrderConfig(otaInfo.getOtaInfoId(), otaInfo.getCompanyId(), Integer.valueOf(order.getInnId()));
        OrderConfigDto orderConfigDto = orderConfigDao.selectOrderConfigByOtaInfoId(orderConfig);
        JsonModel jsonModel = null;
        if (null == orderConfigDto || 0 == orderConfigDto.getStatus()) {
            //自动下单
            //设置订单状态为：接受
            order.setOrderStatus(OrderStatus.ACCEPT);
            jsonModel = payBackDealMethod(order, new UserInfo(), OtaType.FC.name());
        } else {
            //手动下单,手动下单修改订单状态为待处理
            order.setOrderStatus(OrderStatus.NOT_DEAL);
            //待处理订单写入付款金额和付款码
            order.setFeeStatus(FeeStatus.PAID);
            this.orderDao.updateOrderStatusAndFeeStatus(order);
            this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.NOT_DEAL, "手动下单", ChannelSource.FC.name()));
            jsonModel = new JsonModel(true, "付款成功");
            MessageCenterUtils.savePushTomsLog(OtaType.FC, order.getInnId(), Integer.valueOf(order.getRoomTypeId()), null, LogDec.Order,
                    "手动下单：" + order.getChannelOrderCode());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), OrderStatus.ACCEPT, order.getOrderStatus(), order.getFeeStatus(), JacksonUtil.obj2json(order), null, order.getInnId(), order.getInnCode(), "天下房仓创建订单，设置为手动下单"));
        }
        result.put("status", jsonModel);
        result.put("order", order);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.ADD_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), JacksonUtil.obj2json(order), result.toString(), order.getInnId(), order.getInnCode(), "天下房仓创建订单，toms返回值"));
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
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CANCEL_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, null, order.getInnId(), order.getInnCode(), "天下房仓取消订单传入参数"));
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
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CANCEL_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, result.toString(), order.getInnId(), order.getInnCode(), "天下房仓取消订单,toms返回值"));
        return result;
    }

    @Override
    public GetOrderStatusResponse getFcOrderStatus(String xml) throws Exception {
        logger.info("天下房仓查询订单状态传递参数=>" + xml);
        GetOrderStatusResponse result = new GetOrderStatusResponse();
        //解析xml
        Order orderParam = XmlDeal.getFcOrderStatus(xml);
        Order order = this.orderDao.selectOrderByIdAndChannelSource(orderParam.getId(), ChannelSource.FC);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.SEARCH_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, null, order.getInnId(), order.getInnCode(), "天下房仓查询订单传入参数"));
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
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.SEARCH_ORDER, new OrderLogData(order.getChannelSource(), order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), xml, result.toString(), order.getInnId(), order.getInnCode(), "天下房仓查询订单，toms返回值"));
        return result;
    }

    @Override
    public CheckRoomAvailResponse checkRoomAvail(String xml) throws IOException {
        logger.info("天下房仓试订单传入参数=>" + xml);
        CheckRoomAvailResponse result = new CheckRoomAvailResponse();
        //解析xml
        Order order = XmlDeal.getCheckRoomAvailOrder(xml);
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.FC, JacksonUtil.obj2json(order), "天下房仓试订单请求参数"));
        Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.CHECK_ORDER.name());
        //1.查询公司信息
        OtaInfoRefDto otaInfo = this.otaInfoDao.selectCompanyIdByAppKey(ResourceBundleUtil.getString("fc.appKey"), ResourceBundleUtil.getString("fc.appSecret"));
        //查询公司信息
        Company company = this.companyDao.selectCompanyById(otaInfo.getCompanyId());
        logger.info("天下房仓试订单接口传递参数=>" + order.toRoomAvail(company, order).toString());
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.FC, order.toRoomAvail(company, order).toString(), "天下房仓试订单请求参数,oms请求参数"));
        String response = HttpClientUtil.httpGetRoomAvail(dictionary.getUrl(), order.toRoomAvail(company, order));
        JSONObject jsonObject = JSONObject.fromObject(response);
        logger.info("天下房仓试订单接口返回值=>" + response.toString());
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.FC, response, "天下房仓试订单请求参数,oms返回值"));
        if (jsonObject.get("status").equals(200)) {
            //查询当前的价格模式
            BigDecimal percent = BigDecimal.ZERO;
            //2.判断当前公司使用什么价格模式
            if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel())) {
                //查询价格比例
                OtaCommissionPercentDto commission = this.otaCommissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), otaInfo.getUsedPriceModel().name()));
                percent = TomsUtil.getPercent(BigDecimal.valueOf(commission.getCommissionPercent()));
            }
            //是否可以及时确认
            result.setCanImmediate("1");
            result.setSpRatePlanId(order.getOTARatePlanId());
            List<RoomDetail> roomDetails = (List<RoomDetail>) JSONArray.toList(jsonObject.getJSONArray("data"), RoomDetail.class);
            //转换oms房型信息为toms的每日入住信息
            List<DailyInfos> dailyInfos = OrderMethodHelper.toDailyInfos(roomDetails);
            if (null != dailyInfos && ArrayUtils.isNotEmpty(dailyInfos.toArray())) {
                List<SaleItem> saleItemList = new ArrayList<>();
                boolean canBook = true;
                for (DailyInfos dailyInfos1 : dailyInfos) {
                    SaleItem saleItem = new SaleItem();
                    //无早
                    saleItem.setBreakfastType("");
                    //配额数量
                    saleItem.setAvailableQuotaNum(dailyInfos1.getRoomNum());
                    //早餐数量
                    saleItem.setBreakfastNum(0);
                    //货币类型
                    saleItem.setCurrencyType(CurrencyCode.CNY);
                    //根据房间数量判断房间状态和是否可预订
                    if (dailyInfos1.getRoomNum() >= order.getHomeAmount()) {
                        //dayCanBook:1可预订，roomstatus：1有房
                        saleItem.setDayCanBook(1);
                        saleItem.setRoomStatus(1);
                        if (canBook) {
                            canBook = true;
                        }
                    } else {
                        //dayCanBook:0不可预订，roomstatus：2满房
                        saleItem.setDayCanBook(0);
                        saleItem.setRoomStatus(2);
                        if (canBook) {
                            canBook = false;
                        }
                    }
                    //是否可超，0否，1是
                    saleItem.setOverDraft(0);
                    //价格是否待查，0否，1是
                    saleItem.setPriceNeedCheck(0);
                    saleItem.setSaleDate(DateUtil.format(dailyInfos1.getDay(), "yyyy-MM-dd"));
//                    saleItem.setSalePrice(dailyInfos1.getPrice());
                    //判断当前价格模式，如果是卖转低需要转换价格:oms价格*（1-价格比例）=返回给房仓的价格
                    if (UsedPriceModel.MAI2DI.equals(otaInfo.getUsedPriceModel())) {
                        saleItem.setSalePrice(new BigDecimal(new DecimalFormat("#.00").format(dailyInfos1.getPrice().multiply((new BigDecimal(1).subtract(percent))))));
                    } else {
                        saleItem.setSalePrice(new BigDecimal(new DecimalFormat("#.00").format(dailyInfos1.getPrice())));
                    }
                    //判断当前是否执行了加减价，需要把加减价算上
                    OtaRoomPriceDto otaRoomPriceDto = this.otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(company.getId(), Integer.valueOf(order.getRoomTypeId()), otaInfo.getOtaInfoId()));
                    if (null != otaRoomPriceDto && dailyInfos1.getDay().getTime() >= otaRoomPriceDto.getStartDate().getTime() && dailyInfos1.getDay().getTime() <= otaRoomPriceDto.getEndDate().getTime()) {
                        saleItem.setSalePrice(saleItem.getSalePrice().add(BigDecimal.valueOf(otaRoomPriceDto.getValue())));
                    }
                    //将价格小数全部收上去
                    saleItem.setSalePrice(BigDecimal.valueOf(TomsUtil.getPriceRoundUp(saleItem.getSalePrice().doubleValue())));
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
        logger.info("天下房仓试订单返回值：" + result.toString());
        MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.FC, result.toString(), "天下房仓试订单请求参数,toms返回值"));
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

    @Override
    public JsonModel cancelHandOrder(OrderParamDto orderParamDto) throws Exception {
        JsonModel jsonModel = cancelOrderMethod(orderParamDto);
        return jsonModel;
    }

    @Override
    public void SynchronousOmsOrderStatus(Order order) {
        //验证订单是否存在
        Order orderDb = this.orderDao.selectOrderByChannelOrderCodeAndSource(order);
        if (null != orderDb) {
            //判断当前渠道
            String otaType = "";
            JsonModel jsonModel = null;
            if (ChannelSource.FC.equals(orderDb.getChannelSource())) {
                otaType = "FC";
            } else if (ChannelSource.TAOBAO.equals(orderDb.getChannelSource())) {
                otaType = "TB";
            } else {
                throw new TomsRuntimeException("查询otaInfo表中渠道出错,订单号为：" + orderDb.getChannelOrderCode());
            }
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), otaType);
            //判断oms订单返回状态,返回成功，同步淘宝确认有房，返回失败，同步淘宝确认无房
            if (order.getOmsOrderStatus()) {
                //确认有房
                jsonModel = pushSuccessToTB(orderDb, new UserInfo(), otaInfo);
            } else {
                //确认无房
                orderDb.setOrderStatus(OrderStatus.REFUSE);
                jsonModel = dealCancelOrder(orderDb, new UserInfo(), otaInfo);
            }
            logger.info("同步oms订单状态" + jsonModel.toString());

        } else {
            logger.info("同步oms订单状态，渠道订单号为：" + order.getChannelOrderCode() + "不存在");
        }
    }

    @Override
    public void pushOrderStatusMethod(String pushXml) throws Exception {
        //解析oms推送订单状态
        Order orderByOmsPush = XmlDeal.getOrderByOmsPush(pushXml);
        //oms订单成功.查询本地是否存在此单
        Order order = this.orderDao.selectOrderByOmsOrderCodeAndChannelSourceCode(orderByOmsPush.getChannelOrderCode(), orderByOmsPush.getOmsOrderCode());
        if (null != order) {
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), "TB");
            //1.判断订单状态是否正常
            if (orderByOmsPush.getOmsOrderStatus()) {
                //下单成功，调用淘宝更新订单接口
                //TODO 目前只有淘宝异步订单
                JsonModel jsonModel = pushSuccessToTB(order, new UserInfo(), otaInfo);
                logger.info("异步下单调用淘宝成功更新订单接口：" + jsonModel.toString());
            } else {
                //2.订单状态异常
                order.setOrderStatus(OrderStatus.CANCEL_ORDER);
                order.setReason("oms异步订单状态异常");
                JsonModel jsonModel = TBCancelMethod(order, 1L, new UserInfo());
                logger.info("异步下单调用淘宝取消订单更新订单接口" + jsonModel.toString());
            }
        } else {
            logger.info("异步下单oms回调，传入的参数，在toms中未找到该订单，oms回调传入参数：" + order.toString());
        }
    }

    @Override
    public void dealOrderExport(UserInfo currentUser, OrderParamDto orderParamDto, HttpServletResponse response) throws Exception {
        orderParamDto.setCompanyId(currentUser.getCompanyId());
        //处理查询时间
        orderParamDto.setBeginDate(TomsUtil.getDayBeafore(orderParamDto.getBeginDate()));
        orderParamDto.setEndDate(TomsUtil.getDayEnd(orderParamDto.getEndDate()));
        List<OrderParamDto> orderDtos = this.orderDao.selectOrderByNoPage(orderParamDto);
        //房型名称
        if (ArrayUtils.isNotEmpty(orderDtos.toArray())) {
            for (OrderParamDto orderDto : orderDtos) {
                //设置总价和每日价格
                if (null != orderDto.getAddPrice()) {
                    List<DailyInfos> dailyInfoses = this.dailyInfosDao.selectDailyInfoByOrderId(orderDto.getId());
                    BigDecimal addTatalPirce = BigDecimal.ZERO;
                    if (ArrayUtils.isNotEmpty(dailyInfoses.toArray())) {
                        for (DailyInfos dailyInfos : dailyInfoses) {
                            if (1 == dailyInfos.getWeatherAdd()) {
                                dailyInfos.setPrice(dailyInfos.getPrice().add(orderDto.getAddPrice()));
                                addTatalPirce = addTatalPirce.add(orderDto.getAddPrice());
                            }
                        }
                    }
                    orderDto.setTotalPrice(orderDto.getTotalPrice().add(addTatalPirce));
                }
            }
        }
        StringBuilder builder = new StringBuilder("订单列表_");
        builder.append(DateUtil.formatDateToString(new Date(), "yyyyMMddHHmmssSSS")).append(".xls");
        ExportExcelUtil.execlOrderExport(orderDtos, response, builder.toString());

    }

    @Override
    public List<Order> findExceptionOrderList(Map<String, String> map) {
        OrderParamDto order = new OrderParamDto();
        order.setBeginDate(map.get("fifteen"));
        order.setEndDate(map.get("fourteen"));
        return this.orderDao.selectExceptionOrderList(order);
    }

    @Override
    public JsonModel createOrderOmsMethod(OrderParamDto orderParamDto, UserInfo currentUser) throws Exception {
        //下单到oms
        String otaType = "";
        if (ChannelSource.TAOBAO.equals(orderParamDto.getChannelSource())) {
            otaType = OtaType.TB.name();
        } else if (ChannelSource.FC.equals(orderParamDto.getChannelSource())) {
            otaType = OtaType.FC.name();
        } else if (ChannelSource.XC.equals(orderParamDto.getChannelSource())) {
            otaType = OtaType.XC.name();
        } else if (ChannelSource.ZH.equals(orderParamDto.getChannelSource())) {
            otaType = OtaType.ZH.name();
        } else {
            throw new TomsRuntimeException("系统内部错误，未找到该渠道信息!");
        }
        JsonModel jsonModel = payBackDealMethod(orderParamDto, new UserInfo(), otaType);
        //更新异常订单状态为删除
        //判断oms是否成功
        if (jsonModel.isSuccess()) {
            ExceptionOrder exceptionOrder = new ExceptionOrder(OrderStatus.ACCEPT, orderParamDto.getId(), FeeStatus.PAID);
            exceptionOrder.setModifierId(currentUser.getId());
            this.exceptionOrderDao.updateExceptionOrder(exceptionOrder);
        }
        return jsonModel;
    }

    @Override
    public JsonModel cancelOrderOmsMethod(OrderParamDto order, UserInfo currentUser) throws Exception {
        JsonModel result = new JsonModel();
        // 查询调用的url
        Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.CANCEL_ORDER.name());
        //查询公司信息
        OrderParamDto orderParamDto = this.orderDao.selectOrderById(order.getId());
        Company company = this.companyDao.selectCompanyById(orderParamDto.getCompanyId());
        if (null != dictionary) {
            //发送请求
            logger.info("oms取消订单传递参数=>" + order.toCancelOrderParam(order, company).toString());
            String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, company));
            logger.info("调用OMS取消订单的返回值=>" + respose.toString());
            JSONObject jsonObject = JSONObject.fromObject(respose);
            //写入操作记录
            this.orderOperationRecordDao.insertOrderOperationRecord(new OrderOperationRecord(order.getId(), order.getOrderStatus(), OrderStatus.CANCEL_ORDER, "取消订单接口", ChannelSource.TAOBAO.name()));
            //判断oms响应值
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
                //同步成功后在修改数据库
                this.orderDao.updateOrderStatusAndReason(order);
                //取消成功后更新异常订单表
                ExceptionOrder exceptionOrder = new ExceptionOrder(OrderStatus.CANCEL_ORDER, orderParamDto.getId(), FeeStatus.PAID);
                exceptionOrder.setModifierId(currentUser.getId());
                this.exceptionOrderDao.updateExceptionOrder(exceptionOrder);
            }
            return result;
        }
        return new JsonModel(true, "取消订单成功");
    }

    @Override
    public Map<String, Object> dealAvailOrder(String xmlStr) throws Exception {

        Map<String, Object> result = new HashMap<>();
        try {
            Order order = XmlDeal.getAvailOrder(xmlStr);
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.TAOBAO, order.getChannelOrderCode(), order.getId(), order.getOmsOrderCode(), order.getOrderStatus(), order.getOrderStatus(), order.getFeeStatus(), JacksonUtil.obj2json(order), null, order.getInnId(), order.getInnCode(), "淘宝试订单请求参数"));
            //查询客栈关联信息
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
            //公司信息
            Company company = this.companyDao.selectCompanyById(otaInnOtaDto.getCompanyId());
            Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.CHECK_ORDER.name());
            logger.info("淘宝试订单接口传递参数=>" + order.toRoomAvail(company, order).toString());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.TAOBAO, order.toRoomAvail(company, order).toString(), "淘宝试订单请求参数,oms请求参数"));
            String response = HttpClientUtil.httpGetRoomAvail(dictionary.getUrl(), order.toRoomAvail(company, order));
            JSONObject jsonObject = JSONObject.fromObject(response);
            logger.info("淘宝试订单接口返回值oms=>" + response.toString());
            MessageCenterUtils.savePushTomsOrderLog(order.getInnId(), OrderLogDec.CHECK_ORDER, new OrderLogData(ChannelSource.TAOBAO, response, "淘宝试订单请求参数,oms返回值"));
            if (jsonObject.get("status").equals(200)) {
                List<RoomDetail> roomDetails = (List<RoomDetail>) JSONArray.toList(jsonObject.getJSONArray("data"), RoomDetail.class);
                //转换oms房型信息为toms的每日入住信息
                List<DailyInfos> dailyInfos = OrderMethodHelper.toDailyInfos(roomDetails);
                //查询价格模式（toms的增减价）
                OtaRoomPriceDto otaRoomPriceDto = otaRoomPriceDao.selectOtaRoomPriceDto(new OtaRoomPriceDto(otaInnOtaDto.getCompanyId(), Integer.parseInt(order.getRoomTypeId()), otaInnOtaDto.getOtaInfoId()));
                //价格比例
                BigDecimal percent = BigDecimal.ZERO;
                //查询
                OtaInfoRefDto otaInfoRefDto = otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(otaInnOtaDto.getCompanyId(), otaInnOtaDto.getOtaInfoId());
                UsedPriceModel usedPriceModel = otaInfoRefDto.getUsedPriceModel();
                percent = getOtaPercent(company, usedPriceModel);
                List<InventoryRate> inventoryRates = new ArrayList<>();
                if (ArrayUtils.isNotEmpty(dailyInfos.toArray())) {
                    for (DailyInfos d : dailyInfos) {
                        InventoryRate inventoryRate = new InventoryRate();
                        if (null != usedPriceModel && usedPriceModel.equals(UsedPriceModel.MAI2DI)) {
                            d.setPrice(d.getPrice().multiply(new BigDecimal(1).subtract(percent)));
                        }
                        if (null != otaRoomPriceDto) {
                            if (DateUtil.isBetween(d.getDay(), otaRoomPriceDto.getStartDate(), otaRoomPriceDto.getEndDate())) {
                                inventoryRate.setPrice((d.getPrice().doubleValue() + otaRoomPriceDto.getValue()) * Constants.tpPriceUnit);
                            } else {
                                inventoryRate.setPrice(d.getPrice().doubleValue() * Constants.tpPriceUnit);
                            }
                        } else {
                            inventoryRate.setPrice(d.getPrice().doubleValue());
                        }
                        inventoryRate.setPrice(inventoryRate.getPrice() * 100);
                        inventoryRate.setDate(DateUtil.format(d.getDay(), "yyyy-MM-dd"));
                        inventoryRate.setQuota(d.getRoomNum());
                        inventoryRates.add(inventoryRate);
                    }
                }
                result.put("status", true);
                result.put("data", JacksonUtil.obj2json(inventoryRates));
                result.put("message", "淘宝试订单请求成功");
            } else {
                result.put("status", false);
                result.put("message", "淘宝试订单失败，oms状态" + jsonObject.toString());
            }
        } catch (Exception e) {
            result.put("status", false);
            result.put("message", "处理淘宝试订单异常" + e);
        }
        return result;
    }

    @Override
    public JsonModel pushHotelOrderStatus(HotelOrderStatus hotelOrderStatus) throws Exception {
        JsonModel result = new JsonModel();
        Order order = this.orderDao.selectOrderByOmsOrderCodeAndChannelSourceCode(hotelOrderStatus.getTid(), hotelOrderStatus.getOmsOrderCode());
        if (null != order) {
            //如果不为空，调用淘宝酒店更新接口
            hotelOrderStatus.setOutId(order.getId());
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(order.getCompanyId(), otaInnOtaDto.getOtaInfoId());
            logger.info("淘宝信用住更新订单状态" + order.getChannelOrderCode() + "传入参数" + JacksonUtil.obj2json(hotelOrderStatus));
            String response = TBXHotelUtilPromotion.updateHotelOrderStatus(hotelOrderStatus, otaInfo);
            logger.info("淘宝信用住更新订单状态，淘宝返回值：" + order.getChannelOrderCode() + ":" + response);
            JSONObject jsonObject = JSONObject.fromObject(response);
            if (null != jsonObject && null != jsonObject.get("xhotel_order_alipayface_update_response")) {
                //更新toms本地订单状态
                if (hotelOrderStatus.getOptType() == 8) {
                    //取消订单同步数据库
                    order.setReason("pms取消订单");
                    order.setOrderStatus(OrderStatus.CANCEL_ORDER);
                    this.orderDao.updateOrderStatusAndReason(order);
                }
                result.setSuccess(true);
                result.setMessage("更新订单状态成功");
            } else {
                result.setSuccess(false);
                result.setMessage("更新订单状态失败");
            }
        } else {
            result.setSuccess(false);
            result.setMessage("无此订单信息");
        }
        return result;
    }

    @Override
    public JsonModel dealOrderPay(HotelOrderPay hotelOrderPay) throws Exception {
        JsonModel result = new JsonModel();
        Order order = this.orderDao.selectOrderByOmsOrderCodeAndChannelSourceCode(hotelOrderPay.getTid(), hotelOrderPay.getOmsOrderCode());
        if (null != order) {
            hotelOrderPay.setOutId(order.getId());
            OtaInnOtaDto otaInnOtaDto = this.otaInnOtaDao.selectOtaInnOtaByTBHotelId(order.getOTAHotelId());
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(order.getCompanyId(), otaInnOtaDto.getOtaInfoId());
            logger.info("淘宝信用住结账:" + order.getChannelOrderCode() + "传入参数" + JacksonUtil.obj2json(hotelOrderPay));
            String response = TBXHotelUtilPromotion.updateOrderPay(hotelOrderPay, otaInfo);
            logger.info("淘宝信用住结账，淘宝返回值：" + order.getChannelOrderCode() + ":" + response);
            JSONObject jsonObject = JSONObject.fromObject(response);
            if (null != jsonObject && null != jsonObject.get("xhotel_order_alipayface_settle_response")) {
                //淘宝信用住结账成功后需要更新本地订单状态
                order.setOrderStatus(OrderStatus.ACCEPT);
                order.setFeeStatus(FeeStatus.PAID);
                order.setPayment(hotelOrderPay.getTotalRoomFee());
                order.setOmsOrderCode(hotelOrderPay.getOmsOrderCode());
                this.orderDao.updateOrderStatusAndFeeStatus(order);
                result.setSuccess(true);
                result.setMessage("淘宝信用住结账成功");
            } else {
                result.setSuccess(false);
                result.setMessage("淘宝信用住结账失败");
            }
        } else {
            result.setSuccess(false);
            result.setMessage("无此订单信息");
        }
        return result;
    }
}
