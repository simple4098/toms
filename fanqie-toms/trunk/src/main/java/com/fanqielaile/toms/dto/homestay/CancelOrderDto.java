package com.fanqielaile.toms.dto.homestay;

import com.fanqielaile.toms.model.homestay.Refund;

/**
 * Created by LZQ on 2016/9/2.
 */
public class CancelOrderDto extends BaseResultDto {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer statusId;
    private Refund refund;

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }
}
