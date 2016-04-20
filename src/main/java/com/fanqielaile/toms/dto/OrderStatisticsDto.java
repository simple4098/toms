package com.fanqielaile.toms.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fanqielaile.toms.model.OrderOtherPrice;

public class OrderStatisticsDto {
	//订单总间夜数
	private Integer orderNightNumber;
	//利润
	private BigDecimal profit = BigDecimal.ZERO;
	// 总成本
	private BigDecimal totalCostPrice = BigDecimal.ZERO;
	// 总营业额
	private BigDecimal totalPrice = BigDecimal.ZERO;
	//其他消费统计
	private List<OrderOtherPrice> otherConsumer;
	
	
	public Integer getOrderNightNumber() {
		return orderNightNumber;
	}
	public void setOrderNightNumber(Integer orderNightNumber) {
		this.orderNightNumber = orderNightNumber;
	}
	public BigDecimal getProfit() {
		return profit;
	}
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	public BigDecimal getTotalCostPrice() {
		return totalCostPrice;
	}
	public void setTotalCostPrice(BigDecimal totalCostPrice) {
		this.totalCostPrice = totalCostPrice;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<OrderOtherPrice> getOtherConsumer() {
		return otherConsumer;
	}
	public void setOtherConsumer(List<OrderOtherPrice> otherConsumer) {
		this.otherConsumer = otherConsumer;
	}
}
