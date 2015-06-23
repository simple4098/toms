package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.OrderSource;
import com.fanqie.core.dto.OrderSourceDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.DailyInfosDao;
import com.fanqielaile.toms.dao.OrderDao;
import com.fanqielaile.toms.dao.OrderGuestsDao;
import com.fanqielaile.toms.dto.OrderDto;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.helper.OrderMethodHelper;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.util.XmlDeal;
import net.sf.json.JSONObject;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
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
    private OrderGuestsDao orderGuestsDao;


    @Override
    public Map<String, Object> findOrderSourceDetail(ParamDto paramDto,UserInfo userInfo)throws  Exception{
        paramDto.setUserId(userInfo.getId());
        paramDto.setCompanyId(userInfo.getCompanyId());
        String  order = HttpClientUtil.httpGets(CommonApi.ORDER, paramDto);
        JSONObject jsonObject = JSONObject.fromObject(order);
        List<OrderDto> data = new ArrayList<OrderDto>();
        Map<String,Object> map = new HashMap<String, Object>();
        Object rows = jsonObject.get("rows");
        Object obj = jsonObject.get("obj");
        OrderSourceDto orderSource=null;
        if (obj!=null){
             orderSource  = JacksonUtil.json2obj(obj.toString(), OrderSourceDto.class);
        }
        if (rows!=null){
            List<OrderSource> list  = JacksonUtil.json2list(rows.toString(), OrderSource.class);
            OrderDto orderDto=null;
            for (OrderSource o:list){
                orderDto= new OrderDto();
                orderDto.setValue(o.getIncome());
                orderDto.setName(o.getFromName());
                data.add(orderDto);
            }
            map.put("data",data);
            map.put("list",list);
            map.put("orderSource",orderSource);
            return map;
        }
        return null;
    }

    @Override
    public void addOrder(String xmlStr, ChannelSource channelSource) throws Exception {
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
    }
}
