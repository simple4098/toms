package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "ResGlobalInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
public class ResGlobalInfo {
    private List<HotelReservationID> hotelReservationIDs;

    @XmlElement(name = "HotelReservationID", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "HotelReservationIDs")
    public List<HotelReservationID> getHotelReservationIDs() {
        return hotelReservationIDs;
    }

    public void setHotelReservationIDs(List<HotelReservationID> hotelReservationIDs) {
        this.hotelReservationIDs = hotelReservationIDs;
    }
}
