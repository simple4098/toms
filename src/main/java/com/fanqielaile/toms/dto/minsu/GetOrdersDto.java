package com.fanqielaile.toms.dto.minsu;

import java.util.List;

import com.fanqielaile.toms.model.minsu.Order;

/**
 * Created by LZQ on 2016/9/2.
 */
public class GetOrdersDto extends BaseResultDto {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Order> orders;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
    
}
