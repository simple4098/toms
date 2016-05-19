package com.fanqielaile.toms.support.thread;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fanqielaile.toms.dao.OrderGuestsDao;
import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.model.OrderGuests;
import com.fanqielaile.toms.support.SpringContextUtil;
import com.fanqielaile.toms.support.util.MobileUtil;

/*
 * 根据订单获取归属地
 */
public class LocalThread extends Thread {
	private static Logger logger = LoggerFactory.getLogger(LocalThread.class);
	private Order order;
	public LocalThread(Order order){
		this.order = order;
	}
	@Override
	public void run(){
		try {
			OrderGuestsDao orderGuestsDao = (OrderGuestsDao)SpringContextUtil.getBean("orderGuestsDao");
			Map<String,String> map = MobileUtil.getRegion(order.getGuestMobile());
			List<OrderGuests> guests = orderGuestsDao.selectOrderGuestByOrderId(order.getId());
			if(guests == null || guests.size() == 0){
				logger.info("未获取到该订单入住人相关信息，orderId:"+order.getId());
				return;
			}
			for(OrderGuests guest:guests){
				guest.setGuestProvince(map.get("province"));
				guest.setGuestCity(map.get("city"));
			}
			orderGuestsDao.updateOrderGuests(guests);
		} catch (Exception e) {
			logger.error("获取归属地失败",e);
		}
	}
}
