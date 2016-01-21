package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "RoomStayCandidate", namespace = "http://www.opentravel.org/OTA/2003/05")
public class RoomStayCandidate {
    private String quantity;
    private GuestCounts guestCounts;

    @XmlElement(name = "GuestCounts", namespace = "http://www.opentravel.org/OTA/2003/05")
    public GuestCounts getGuestCounts() {
        return guestCounts;
    }

    public void setGuestCounts(GuestCounts guestCounts) {
        this.guestCounts = guestCounts;
    }

    @XmlAttribute(name = "Quantity")
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
