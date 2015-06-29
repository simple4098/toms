package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.OrderSource;
import com.fanqie.core.dto.OrderSourceDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.OrderDto;
import com.fanqielaile.toms.dto.OtaBangInnRoomDto;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.DictionaryType;
import com.fanqielaile.toms.enums.FeeStatus;
import com.fanqielaile.toms.enums.OrderStatus;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.model.Dictionary;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.JsonModel;
import com.fanqielaile.toms.support.util.XmlDeal;
import net.sf.json.JSONObject;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
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
public class OrderService implements IOrderService {
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
    public Order addOrder(String xmlStr, ChannelSource channelSource) throws Exception {
        //解析xml
        Element dealXmlStr = XmlDeal.dealXmlStr(xmlStr);
        //转换成对象只针对淘宝传递的参数
        //TODO 需要计算的价格，下单时间，
        Order order = OrderMethodHelper.getOrder(dealXmlStr);
        //TODO 查询策略,然后计算相应的价格

        //设置渠道来源
        order.setChannelSource(channelSource);
        order.setOrderTime(new Date());
        //创建订单
        this.orderDao.insertOrder(order);
        //创建每日价格信息
        this.dailyInfosDao.insertDailyInfos(order);
        //创建入住人信息
        this.orderGuestsDao.insertOrderGuests(order);
        return order;
    }

    @Override
    public JsonModel cancelOrder(String xmlStr, ChannelSource channelSource) throws Exception {
        //解析取消订单的xml
        String orderId = XmlDeal.getOrder(xmlStr).getId();
        //验证此订单是否存在
        Order order = orderDao.selectOrderByIdAndChannelSource(orderId, channelSource);
        if (null == order) {
            return new JsonModel(false, "订单不存在");
        }
        order.setReason(XmlDeal.getOrder(xmlStr).getReason());
        order.setOrderStatus(OrderStatus.REFUSE);
        //判断订单是否需要同步OMS,条件根据订单是否付款
        if (!order.getFeeStatus().equals(FeeStatus.NOT_PAY)) {
            // 查询调用的url
            Dictionary dictionary = dictionaryDao.selectDictionaryByType(DictionaryType.CANCEL_ORDER.name());
            if (null != dictionary) {
                //发送请求
                String respose = HttpClientUtil.httpGetCancelOrder(dictionary.getUrl(), order.toCancelOrderParam(order, dictionary));
                JSONObject jsonObject = JSONObject.fromObject(respose);
                if (!jsonObject.get("status").equals(200)) {
                    return new JsonModel(false, jsonObject.get("status").toString() + ":" + jsonObject.get("message"));
                } else {
                    //同步成功后在修改数据库
                    this.orderDao.updateOrderStatusAndReason(order);
                }
            }
        }
        return new JsonModel(true, "取消订单成功");
    }

    @Override
    public JsonModel paymentSuccessCallBack(String xmlStr, ChannelSource channelSource, UserInfo userInfo) throws Exception {
        String orderId = XmlDeal.getOrder(xmlStr).getId();
        //获取订单号，判断订单是否存在
        Order order = this.orderDao.selectOrderByIdAndChannelSource(orderId, channelSource);
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
        if (order.getFeeStatus().equals(FeeStatus.NOT_PAY)) {
            //查询字典表中同步OMS需要的数据
            Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.CREATE_ORDER.name());
            //查询客栈信息
            BangInn bangInn = this.bangInnDao.selectBangInnByCompanyIdAndInnId("88888888", order.getInnId());
            if (null == bangInn) {
                return new JsonModel(false, "绑定客栈不存在");
            }
            order.setAccountId(bangInn.getAccountId());
            List<OtaBangInnRoomDto> otaBangInnRoomDtos = this.bangInnRoomDao.selectBangInnRoomByInnIdAndRoomTypeId(order.getInnId(), Integer.parseInt(order.getRoomTypeId()));
            if (otaBangInnRoomDtos.isEmpty()) {
                return new JsonModel(false, "房型不存在");
            }
            order.setRoomTypeName(otaBangInnRoomDtos.get(0).getRoomTypeName());
            String respose = HttpClientUtil.httpPostOrder(dictionary.getUrl(), order.toOrderParamDto(order, dictionary));
            JSONObject jsonObject = JSONObject.fromObject(respose);
            if (!jsonObject.get("status").equals(200)) {
                order.setOrderStatus(OrderStatus.REFUSE);
                order.setFeeStatus(FeeStatus.NOT_PAY);
                Company company = this.companyDao.selectCompanyById(userInfo.getCompanyId());
                //TODO 必须登录
                String result = TBXHotelUtil.orderUpdate(order, company);
                if (null != result && result.equals("success")) {
                    this.orderDao.updateOrderStatusAndFeeStatus(order);
                }
                return new JsonModel(false, jsonObject.get("status") + ":" + jsonObject.get("message"));
            } else {
                //同步成功后在修改数据库
                order.setFeeStatus(FeeStatus.PAID);
                order.setOrderStatus(OrderStatus.ACCEPT);
                //解析xml得到的order
                Order order1 = XmlDeal.getOrder(xmlStr);
                order.setAlipayTradeNo(order1.getAlipayTradeNo());
                order.setPayment(order1.getPayment());
                this.orderDao.updateOrderStatusAndFeeStatus(order);
                return new JsonModel(true, "付款成功");
            }

        } else {
            return new JsonModel(false, "系统内部错误");
        }
    }
}
