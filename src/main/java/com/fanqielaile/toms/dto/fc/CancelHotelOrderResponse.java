package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.model.fc.FCcheckRoomAvailResponseResult;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2015/9/2.
 * 天下房仓取消订单相应对象
 */
@XmlRootElement(name = "CancelHotelOrderResponse")
public class CancelHotelOrderResponse {
    //合作方订单编码
    private String spOrderId;
    //取消状态
    private Integer cancelStatus;

    @XmlElement(name = "SpOrderId")
    public String getSpOrderId() {
        return spOrderId;
    }

    public void setSpOrderId(String spOrderId) {
        this.spOrderId = spOrderId;
    }

    @XmlElement(name = "CancelStatus")
    public Integer getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Integer cancelStatus) {
        this.cancelStatus = cancelStatus;
    }
}
