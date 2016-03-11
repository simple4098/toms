package com.fanqielaile.toms.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/3/8.
 */
@XmlRootElement(name = "Result")
public class TBAvailOrderResult {
    private String message;
    private String createOrderValidateKey;
    private String resultCode;
    //价格日历
    private String inventoryPrice;

    @XmlElement(name = "Message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(name = "CreateOrderValidateKey")
    public String getCreateOrderValidateKey() {
        return createOrderValidateKey;
    }

    public void setCreateOrderValidateKey(String createOrderValidateKey) {
        this.createOrderValidateKey = createOrderValidateKey;
    }

    @XmlElement(name = "ResultCode")
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @XmlElement(name = "InventoryPrice")
    public String getInventoryPrice() {
        return inventoryPrice;
    }

    public void setInventoryPrice(String inventoryPrice) {
        this.inventoryPrice = inventoryPrice;
    }
}
