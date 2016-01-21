package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "HotelReservations", namespace = "http://www.opentravel.org/OTA/2003/05")
public class HotelReservations {
    private HotelReservation hotelReservation;

    @XmlElement(name = "HotelReservation", namespace = "http://www.opentravel.org/OTA/2003/05")
    public HotelReservation getHotelReservation() {
        return hotelReservation;
    }

    public void setHotelReservation(HotelReservation hotelReservation) {
        this.hotelReservation = hotelReservation;
    }
}
