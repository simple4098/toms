package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.OrderSource;
import com.fanqie.core.dto.OrderSourceDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.OrderDao;
import com.fanqielaile.toms.dto.OrderDto;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.util.XmlDeal;
import net.sf.json.JSONObject;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void addOrder(String xmlStr, Order order) throws Exception {
        //解析xml
        Element dealXmlStr = XmlDeal.dealXmlStr(xmlStr);
        //转换成对象
    }
}
