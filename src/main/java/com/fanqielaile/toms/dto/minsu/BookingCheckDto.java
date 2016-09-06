package com.fanqielaile.toms.dto.minsu;

import com.fanqielaile.toms.model.minsu.Deposit;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by LZQ on 2016/9/2.
 */
public class BookingCheckDto extends BaseResultDto {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="是否可预定.0.不可预定;1.可预定",required=true)
	private Integer bookingStatus;
	@ApiModelProperty(value="订单总金额，单位：分；如100表示1元",required=true)
    private Integer totalAmount;
	@ApiModelProperty(value="在线支付总金额，单位：分；如100表示1元",required=true)
    private Integer onlineAmount;
	@ApiModelProperty(value="线下支付总金额，单位：分；如100表示1元",required=true)
    private Integer offlineAmount;
	@ApiModelProperty(value="线上线下支付比例",required=true)
    private Integer rate;
	@ApiModelProperty(value="折扣前原价",required=true)
    private Integer originAmount;
	@ApiModelProperty(value="折扣金额",required=true)
    private Integer derateAmount;
	@ApiModelProperty(value="押金信息",required=true)
    private Deposit deposit;

    public Integer getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Integer bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(Integer onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public Integer getOfflineAmount() {
        return offlineAmount;
    }

    public void setOfflineAmount(Integer offlineAmount) {
        this.offlineAmount = offlineAmount;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getOriginAmount() {
        return originAmount;
    }

    public void setOriginAmount(Integer originAmount) {
        this.originAmount = originAmount;
    }

    public Integer getDerateAmount() {
        return derateAmount;
    }

    public void setDerateAmount(Integer derateAmount) {
        this.derateAmount = derateAmount;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }
}
