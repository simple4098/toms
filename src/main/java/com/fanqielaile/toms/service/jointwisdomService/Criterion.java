package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "Criterion", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Criterion {
    private HotelRef hotelRef;
    private StayDateRange stayDateRange;
    private RoomStayCandidates roomStayCandidates;


    @XmlElement(name = "HotelRef", namespace = "http://www.opentravel.org/OTA/2003/05")
    public HotelRef getHotelRef() {
        return hotelRef;
    }

    public void setHotelRef(HotelRef hotelRef) {
        this.hotelRef = hotelRef;
    }

    @XmlElement(name = "StayDateRange", namespace = "http://www.opentravel.org/OTA/2003/05")
    public StayDateRange getStayDateRange() {
        return stayDateRange;
    }

    public void setStayDateRange(StayDateRange stayDateRange) {
        this.stayDateRange = stayDateRange;
    }

    @XmlElement(name = "RoomStayCandidates", namespace = "http://www.opentravel.org/OTA/2003/05")
    public RoomStayCandidates getRoomStayCandidates() {
        return roomStayCandidates;
    }

    public void setRoomStayCandidates(RoomStayCandidates roomStayCandidates) {
        this.roomStayCandidates = roomStayCandidates;
    }
}
