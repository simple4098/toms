package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "AvailRequestSegment", namespace = "http://www.opentravel.org/OTA/2003/05")
public class AvailRequestSegment {
    private HotelSearchCriteria hotelSearchCriteria;
    private String availReqType;

    @XmlAttribute(name = "AvailReqType")
    public String getAvailReqType() {
        return availReqType;
    }

    public void setAvailReqType(String availReqType) {
        this.availReqType = availReqType;
    }

    @XmlElement(name = "HotelSearchCriteria", namespace = "http://www.opentravel.org/OTA/2003/05")
    public HotelSearchCriteria getHotelSearchCriteria() {
        return hotelSearchCriteria;
    }

    public void setHotelSearchCriteria(HotelSearchCriteria hotelSearchCriteria) {
        this.hotelSearchCriteria = hotelSearchCriteria;
    }
}
