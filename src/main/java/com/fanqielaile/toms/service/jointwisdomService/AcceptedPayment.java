package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by wangdayin on 2016/2/24.
 */
@XmlRootElement(name = "AcceptedPayment", namespace = "http://www.opentravel.org/OTA/2003/05")
public class AcceptedPayment {
    private String value;

    @XmlValue
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
