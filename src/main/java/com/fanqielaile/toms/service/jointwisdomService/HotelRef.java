package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "HotelRef", namespace = "http://www.opentravel.org/OTA/2003/05")
public class HotelRef {
    private String hotelCode;

    @XmlAttribute(name = "HotelCode")
    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }
}
