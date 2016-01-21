package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/12.
 */
@XmlRootElement(name = "BasicPropertyInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
public class BasicPropertyInfo {
    private String hotelCode;
    private String hotelName;

    @XmlAttribute(name = "HotelName")
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    @XmlAttribute(name = "HotelCode")
    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    @Override
    public String toString() {
        return "BasicPropertyInfo{" +
                "hotelCode='" + hotelCode + '\'' +
                ", hotelName='" + hotelName + '\'' +
                '}';
    }
}
