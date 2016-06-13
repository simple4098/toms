package com.fanqielaile.toms.dto;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.OrderStatus;
import com.fanqielaile.toms.model.Order;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2015/7/6.
 */
public class OrderParamDto extends Order {
    //房型名称
    private String roomTypeName;
    //客栈名称
    private String innName;
    //开始时间
    private String beginDate;
    //结束时间
    private String endDate;
    //日期类型
    private String searchType;
    //已接受订单数量
    private int acceptOrder = 0;
    //所有的总价
    private BigDecimal allTotalPrice = BigDecimal.ZERO;
    //所有预付金额
    private BigDecimal allPrePrice = BigDecimal.ZERO;
    //所有成本价
    private BigDecimal allCostPrice = BigDecimal.ZERO;
    //ota佣金
    private BigDecimal allPayPrice = BigDecimal.ZERO;
    //订单状态
    private String orderStatusDesc;
    //订单状态字符串
    private String orderStatusString;
    //订单操作人下拉列表框相关
    private List<UserInfoDto> operators;
    //订单状态下拉列表框相关
    private List<OrderStatus> selectedOrderStatus;
    //pms申请取消订单时，toms是否同意状态
    private boolean agreeStatus;
    //当前是否是营业统计状态，true为营业统计状态
    private boolean statisticsStatus = false;
    
    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    public String getOrderStatusDesc() {
        return getOrderStatus().getText();
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public int getAcceptOrder() {
        return acceptOrder;
    }

    public void setAcceptOrder(int acceptOrder) {
        this.acceptOrder = acceptOrder;
    }

    public BigDecimal getAllTotalPrice() {
        return allTotalPrice;
    }

    public void setAllTotalPrice(BigDecimal allTotalPrice) {
        this.allTotalPrice = allTotalPrice;
    }

    public BigDecimal getAllPrePrice() {
        return allPrePrice;
    }

    public void setAllPrePrice(BigDecimal allPrePrice) {
        this.allPrePrice = allPrePrice;
    }

    public BigDecimal getAllCostPrice() {
        return allCostPrice;
    }

    public void setAllCostPrice(BigDecimal allCostPrice) {
        this.allCostPrice = allCostPrice;
    }

    public BigDecimal getAllPayPrice() {
        return allPayPrice;
    }

    public void setAllPayPrice(BigDecimal allPayPrice) {
        this.allPayPrice = allPayPrice;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInnName() {
        String result = getOrderInnName();
        if (StringUtils.isEmpty(result)) {
            result = "暂无";
        }
        return result;
    }

    public void setInnName(String innName) {
        this.innName = innName;
    }

    @Override
    public String getRoomTypeName() {
        String result = getOrderRoomTypeName();
        if (StringUtils.isEmpty(result)) {
            result = "暂无";
        }
        return result;
    }

    @Override
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public OrderParamDto getOrderByDealTime(OrderParamDto orderParamDto) {
        if (null != orderParamDto) {
            if (StringUtils.isNotEmpty(orderParamDto.getBeginDate())) {
                orderParamDto.setBeginDate(DateUtil.format(DateUtil.parse(orderParamDto.getBeginDate(), "yyyy-MM-dd"), "yyyy-MM-dd"));
            }
            if (StringUtils.isNotEmpty(orderParamDto.getEndDate())) {
                orderParamDto.setEndDate(DateUtil.format(DateUtil.parse(orderParamDto.getEndDate(), "yyyy-MM-dd"), "yyyy-MM-dd"));
            }
        }
        return orderParamDto;
    }

    public Map toMap() {
        Map map = new HashMap();
        map.put("channelSource", getChannelSource().getText());
        map.put("channelOrderCode", getChannelOrderCode());
        map.put("orderStatus", getOrderStatus().getText());
        map.put("innName", getInnName());
        map.put("guestName", getGuestName());
        map.put("roomTypeName", getRoomTypeName());
        map.put("homeAmount", getHomeAmount());
        map.put("liveLeaveDate", DateUtil.format(getLiveTime(), "yyyy-MM-dd") + "/" + DateUtil.format(getLeaveTime(), "yyyy-MM-dd"));
//        if (ChannelSource.HAND_ORDER.equals(getChannelSource())) {
//            map.put("totalPrice", getPrepayPrice());
//        } else {
//            map.put("totalPrice", getTotalPrice());
//        }
        map.put("prepayPrice", getPrepayPrice());
        map.put("costPrice", getCostPrice());
        map.put("operator", getOperator());
        map.put("profit", getProfit());
        map.put("orderTime", DateUtil.format(getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
        return map;
    }

	public List<UserInfoDto> getOperators() {
		return operators;
	}

	public void setOperators(List<UserInfoDto> operators) {
		this.operators = operators;
	}

	public List<OrderStatus> getSelectedOrderStatus() {
		return selectedOrderStatus;
	}

	public void setSelectedOrderStatus(List<OrderStatus> selectedOrderStatus) {
		this.selectedOrderStatus = selectedOrderStatus;
	}

	public boolean isAgreeStatus() {
		return agreeStatus;
	}

	public void setAgreeStatus(boolean agreeStatus) {
		this.agreeStatus = agreeStatus;
	}

	public Boolean getStatisticsStatus() {
		return statisticsStatus;
	}

	public void setStatisticsStatus(Boolean statisticsStatus) {
		this.statisticsStatus = statisticsStatus;
	}
}
