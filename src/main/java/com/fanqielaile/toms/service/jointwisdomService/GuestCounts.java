package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/20.
 */
@XmlRootElement(name = "GuestCounts", namespace = "http://www.opentravel.org/OTA/2003/05")
public class GuestCounts {
    private GuestCount guestCount;

    @XmlElement(name = "GuestCount", namespace = "http://www.opentravel.org/OTA/2003/05")
    public GuestCount getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(GuestCount guestCount) {
        this.guestCount = guestCount;
    }
}
