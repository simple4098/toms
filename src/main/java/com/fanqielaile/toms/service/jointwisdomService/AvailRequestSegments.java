package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "AvailRequestSegments", namespace = "http://www.opentravel.org/OTA/2003/05")
public class AvailRequestSegments {
    private AvailRequestSegment availRequestSegment;

    @XmlElement(name = "AvailRequestSegment", namespace = "http://www.opentravel.org/OTA/2003/05")
    public AvailRequestSegment getAvailRequestSegment() {
        return availRequestSegment;
    }

    public void setAvailRequestSegment(AvailRequestSegment availRequestSegment) {
        this.availRequestSegment = availRequestSegment;
    }
}
