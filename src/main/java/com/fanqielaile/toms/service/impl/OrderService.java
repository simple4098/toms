package com.fanqielaile.toms.service.impl;

import com.fanqie.core.domain.OrderSource;
import com.fanqie.core.dto.OrderSourceDto;
import com.fanqie.core.dto.ParamDto;
import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OrderDto;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IOrderService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

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


    @Override
    public Map<String, Object> findOrderSourceDetail(ParamDto paramDto,UserInfo userInfo)throws  Exception{
        paramDto.setUserId(userInfo.getId());
        paramDto.setCompanyId(userInfo.getCompanyId());
        String  order = HttpClientUtil.httpGets(CommonApi.ORDER, paramDto);
        JSONObject jsonObject = JSONObject.fromObject(order);
        List<OrderDto> data = new ArrayList<OrderDto>();
        Map<String,Object> map = new HashMap<String, Object>();
        OrderSourceDto orderSource=null;
        if (jsonObject.get("obj")!=null){
             orderSource  = JacksonUtil.json2obj(jsonObject.get("obj").toString(), OrderSourceDto.class);
        }
        if (jsonObject.get("rows")!=null){
            Object rows = jsonObject.get("rows");
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
}
