package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "ArrivalTransport", namespace = "http://www.opentravel.org/OTA/2003/05")
public class ArrivalTransport {
    private TransportInfo transportInfo;

    @XmlElement(name = "TransportInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
    public TransportInfo getTransportInfo() {
        return transportInfo;
    }

    public void setTransportInfo(TransportInfo transportInfo) {
        this.transportInfo = transportInfo;
    }
}
