package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "HotelReservationID", namespace = "http://www.opentravel.org/OTA/2003/05")
public class HotelReservationID {
    private String resIDType;
    private String resIDValue;

    @XmlAttribute(name = "ResID_Type")
    public String getResIDType() {
        return resIDType;
    }

    public void setResIDType(String resIDType) {
        this.resIDType = resIDType;
    }

    @XmlAttribute(name = "ResID_Value")
    public String getResIDValue() {
        return resIDValue;
    }

    public void setResIDValue(String resIDValue) {
        this.resIDValue = resIDValue;
    }
}
