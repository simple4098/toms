package com.fanqielaile.toms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fanqie.core.dto.GetOmsOrderStatusParamDto;
import com.fanqie.util.DateUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.OrderParamDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.enums.*;
import com.fanqielaile.toms.model.*;
import com.fanqielaile.toms.service.IExceptionOrderService;
import com.fanqielaile.toms.support.tb.TBXHotelUtilPromotion;
import com.fanqielaile.toms.support.util.JsonModel;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2016/1/28.
 */
@Service
public class ExceptionOrderService implements IExceptionOrderService {
    Logger logger = LoggerFactory.getLogger(ExceptionOrderService.class);
    @Resource
    private ExceptionOrderDao exceptionOrderDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private DailyInfosDao dailyInfosDao;
    @Resource
    private CompanyDao companyDao;
    @Resource
    private DictionaryDao dictionaryDao;
    @Override
    public void createExceptionOrder(Order order) {
        this.exceptionOrderDao.insertExceptionOrder(order);
    }

    @Override
    public void modifyExceptionOrder(ExceptionOrder exceptionOrder) {
        this.exceptionOrderDao.updateExceptionOrder(exceptionOrder);
    }

    @Override
    public List<Order> findAllExceptionOrder(PageBounds pageBounds) throws Exception {
        List<Order> orders = this.orderDao.selectAllExceptionOrder(pageBounds);
        //查询淘宝订单状态
        if (ArrayUtils.isNotEmpty(orders.toArray())) {
            for (Order order : orders) {
                if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
                    OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), OtaType.TB.name());
                    order.setEariestArriveTime(DateUtil.parse(DateUtil.format(DateUtil.addDay(order.getCreatedDate(), -1), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
                    order.setLastestArriveTime(DateUtil.parse(DateUtil.format(DateUtil.addDay(order.getCreatedDate(), 1), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
                    String response = TBXHotelUtilPromotion.searchOrderStatus(order, otaInfo);
                    if (StringUtils.isNotEmpty(response)) {
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        if (null != jsonObject && null != jsonObject.get("xhotel_order_search_response")) {
                            //淘宝查询订单状态成功
                            JSONObject object = JSONObject.parseObject(jsonObject.getJSONObject("xhotel_order_search_response").getJSONObject("hotel_orders").getJSONArray("x_hotel_order").get(0).toString());
                            order.setOtaOrderStatus(Enum.valueOf(OtaOrderStatus.class, String.valueOf(object.get("trade_status"))));
                        } else {
                            order.setOtaOrderStatus(OtaOrderStatus.Error);
                        }
                    }

                }
                //
                //设置总价和每日价格
                if (null != order.getAddPrice()) {
                    List<DailyInfos> dailyInfoses = this.dailyInfosDao.selectDailyInfoByOrderId(order.getId());
                    BigDecimal addTatalPirce = BigDecimal.ZERO;
                    if (ArrayUtils.isNotEmpty(dailyInfoses.toArray())) {
                        for (DailyInfos dailyInfos : dailyInfoses) {
                            if (1 == dailyInfos.getWeatherAdd()) {
                                dailyInfos.setPrice(dailyInfos.getPrice().add(order.getAddPrice()));
                                addTatalPirce = addTatalPirce.add(order.getAddPrice());
                            }
                        }
                    }
                    order.setTotalPrice(order.getTotalPrice().add(addTatalPirce));
                    order.setTotalPrice(order.getBasicTotalPrice());
                }
            }
        }
        return orders;
    }

    @Override
    public void deleteExceptionOrder(Order order) {
        this.exceptionOrderDao.deleteExceptionOrder(order);
    }

    @Override
    public JsonModel dealCloseOrder(OrderParamDto order) throws Exception {
        JsonModel result = new JsonModel();
        if (ChannelSource.TAOBAO.equals(order.getChannelSource())) {
            OtaInfoRefDto otaInfo = this.otaInfoDao.selectAllOtaByCompanyAndType(order.getCompanyId(), OtaType.TB.name());
            order.setEariestArriveTime(DateUtil.parse(DateUtil.format(DateUtil.addDay(order.getCreatedDate(), -1), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
            order.setLastestArriveTime(DateUtil.parse(DateUtil.format(DateUtil.addDay(order.getCreatedDate(), 1), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
            String response = TBXHotelUtilPromotion.searchOrderStatus(order, otaInfo);
            logger.info("关闭订单号：" + order.getChannelOrderCode() + " 淘宝返回值:" + response);
            if (StringUtils.isNotEmpty(response)) {
                JSONObject jsonObject = JSONObject.parseObject(response);
                if (null != jsonObject && null != jsonObject.get("xhotel_order_search_response")) {
                    //淘宝查询订单状态成功
                    JSONObject object = JSONObject.parseObject(jsonObject.getJSONObject("xhotel_order_search_response").getJSONObject("hotel_orders").getJSONArray("x_hotel_order").get(0).toString());
                    order.setOtaOrderStatus(Enum.valueOf(OtaOrderStatus.class, String.valueOf(object.get("trade_status"))));
                    if (OtaOrderStatus.WAIT_BUYER_PAY.equals(order.getOrderStatus())) {
                        order.setOrderStatus(OrderStatus.ACCEPT);
                        order.setFeeStatus(FeeStatus.NOT_PAY);
                    } else if (OtaOrderStatus.WAIT_SELLER_SEND_GOODS.equals(order.getOtaOrderStatus())) {
                        order.setOrderStatus(OrderStatus.ACCEPT);
                        order.setFeeStatus(FeeStatus.PAID);
                    } else if (OtaOrderStatus.TRADE_CLOSED.equals(order.getOtaOrderStatus())) {
                        order.setOrderStatus(OrderStatus.CANCEL_ORDER);
                    } else if (OtaOrderStatus.TRADE_FINISHED.equals(order.getOtaOrderStatus())) {
                        order.setOrderStatus(OrderStatus.ACCEPT);
                        order.setFeeStatus(FeeStatus.PAID);
                    } else if (OtaOrderStatus.TRADE_NO_CREATE_PAY.equals(order.getOtaOrderStatus())) {
                        order.setOrderStatus(OrderStatus.CANCEL_ORDER);
                        order.setFeeStatus(FeeStatus.NOT_PAY);
                    } else if (OtaOrderStatus.TRADE_CLOSED_BY_TAOBAO.equals(order.getOtaOrderStatus())) {
                        order.setOrderStatus(OrderStatus.CANCEL_ORDER);
                        order.setFeeStatus(FeeStatus.PAID);
                    } else if (OtaOrderStatus.TRADE_SUCCESS.equals(order.getOtaOrderStatus())) {
                        order.setOrderStatus(OrderStatus.ACCEPT);
                        order.setFeeStatus(FeeStatus.PAID);
                    } else {
                        result.setSuccess(false);
                        result.setMessage("解析淘宝订单状态有误");
                        return result;
                    }
                    this.orderDao.updateOrderStatusAndFeeStatus(order);
                    result.setSuccess(true);
                    result.setMessage("关闭订单成功");
                } else {
                    result.setSuccess(false);
                    result.setMessage("没有找到淘宝订单状态");
                }
            }

        }
        return result;
    }

    @Override
    public Map<String, Object> findOmsOrderStatus(OrderParamDto orderParamDto) throws IOException {
        Map<String, Object> result = new HashMap<>();
        Company company = this.companyDao.selectCompanyById(orderParamDto.getCompanyId());
        Dictionary dictionary = this.dictionaryDao.selectDictionaryByType(DictionaryType.ORDER_STATUS.name());
        if (null != company && null != dictionary) {
            GetOmsOrderStatusParamDto getOmsOrderStatusParamDto = new GetOmsOrderStatusParamDto();
            getOmsOrderStatusParamDto.setOtaId(company.getOtaId());
            getOmsOrderStatusParamDto.setOtaOrderNo(orderParamDto.getChannelOrderCode());
            getOmsOrderStatusParamDto.setvName(company.getUserAccount());
            getOmsOrderStatusParamDto.setvPWD(company.getUserPassword());
            String response = HttpClientUtil.httpGetOmsOrderStatus(dictionary.getUrl(), getOmsOrderStatusParamDto);
            JSONObject jsonObject = JSONObject.parseObject(response);
            if ("200".equals(jsonObject.get("status").toString())) {
                String orderStatus = jsonObject.get("orderStatus").toString();
                if ("0".equals(orderStatus)) {
                    result.put("orderStatus", "0:未处理");
                } else if ("1".equals(orderStatus)) {
                    result.put("orderStatus", "1:已接受");
                } else if ("2".equals(orderStatus)) {
                    result.put("orderStatus", "2:已拒绝");
                } else if ("3".equals(orderStatus)) {
                    result.put("orderStatus", "3:已取消");
                } else if ("4".equals(orderStatus)) {
                    result.put("orderStatus", "4:验证失败");
                } else {
                    result.put("orderStatus", "error:没有此订单状态，请检查");
                }
                result.put("status", true);
            } else {
                result.put("status", false);
                result.put("orderStatus", "获取oms订单状态失败");
            }
        } else {
            result.put("status", false);
            result.put("orderStatus", "未找到公司信息或者访问oms订单状态url");
        }
        return result;
    }

    @Override
    public Map<String, Object> finPmsOrderStatus(OrderParamDto orderParamDto) throws Exception {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isNotEmpty(orderParamDto.getOmsOrderCode())) {
            String response = HttpClientUtil.httpPostGetPmsOrderStatus(CommonApi.pmsOrderStatus, orderParamDto.getInnId(), orderParamDto.getOmsOrderCode());
            JSONObject jsonObject = JSONObject.parseObject(response);
            if ("200".equals(jsonObject.get("status").toString())) {
                String orderStatus = jsonObject.get("state").toString();
                if ("0".equals(orderStatus)) {
                    result.put("orderStatus", "0:取消");
                } else if ("1".equals(orderStatus)) {
                    result.put("orderStatus", "1:未入住[预定]");
                } else if ("2".equals(orderStatus)) {
                    result.put("orderStatus", "2:部分入住");
                } else if ("3".equals(orderStatus)) {
                    result.put("orderStatus", "3:未退房[入住]");
                } else if ("4".equals(orderStatus)) {
                    result.put("orderStatus", "4:部分退房");
                } else if ("5".equals(orderStatus)) {
                    result.put("orderStatus", "5:已退房");
                } else if ("-1".equals(orderStatus)) {
                    result.put("orderStatus", "-1：哑房");
                } else if ("-2".equals(orderStatus)) {
                    result.put("orderStatus", "-2:已删除");
                } else {
                    result.put("orderStatus", "error:没有此订单状态，请检查");
                }
                result.put("status", true);
            } else {
                result.put("status", false);
                result.put("orderStatus", jsonObject.get("message").toString());
            }
        } else {
            result.put("status", false);
            result.put("orderStatus", "oms订单号为空,请联系相关人员");
        }
        return result;
    }
}
