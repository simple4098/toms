package com.fanqielaile.toms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.dao.DailyInfosDao;
import com.fanqielaile.toms.dao.ExceptionOrderDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dao.OrderDao;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.OtaOrderStatus;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.DailyInfos;
import com.fanqielaile.toms.model.ExceptionOrder;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.service.IExceptionOrderService;
import com.fanqielaile.toms.support.tb.TBXHotelUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/28.
 */
@Service
public class ExceptionOrderService implements IExceptionOrderService {
    @Resource
    private ExceptionOrderDao exceptionOrderDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private DailyInfosDao dailyInfosDao;

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
                    String response = TBXHotelUtil.searchOrderStatus(order, otaInfo);
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
}
