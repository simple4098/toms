package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "Source", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Source {
    private RequestorID requestorID;
    private BookingChannel bookingChannel;

    @XmlElement(name = "BookingChannel", namespace = "http://www.opentravel.org/OTA/2003/05")
    public BookingChannel getBookingChannel() {
        return bookingChannel;
    }

    public void setBookingChannel(BookingChannel bookingChannel) {
        this.bookingChannel = bookingChannel;
    }

    @XmlElement(name = "RequestorID", namespace = "http://www.opentravel.org/OTA/2003/05")
    public RequestorID getRequestorID() {
        return requestorID;
    }

    public void setRequestorID(RequestorID requestorID) {
        this.requestorID = requestorID;
    }
}
