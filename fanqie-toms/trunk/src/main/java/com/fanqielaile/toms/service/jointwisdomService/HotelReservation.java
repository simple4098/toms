package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "HotelReservation", namespace = "http://www.opentravel.org/OTA/2003/05")
public class HotelReservation {
    private UniqueID uniqueID;
    private List<RoomStay> roomStays;
    private List<ResGuest> resGuests;
    private ResGlobalInfo resGlobalInfo;

    @XmlElement(name = "ResGlobalInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
    public ResGlobalInfo getResGlobalInfo() {
        return resGlobalInfo;
    }

    public void setResGlobalInfo(ResGlobalInfo resGlobalInfo) {
        this.resGlobalInfo = resGlobalInfo;
    }

    @XmlElement(name = "ResGuest", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "ResGuests")
    public List<ResGuest> getResGuests() {
        return resGuests;
    }

    public void setResGuests(List<ResGuest> resGuests) {
        this.resGuests = resGuests;
    }

    @XmlElement(name = "RoomStay", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "RoomStays")
    public List<RoomStay> getRoomStays() {
        return roomStays;
    }

    public void setRoomStays(List<RoomStay> roomStays) {
        this.roomStays = roomStays;
    }

    @XmlElement(name = "UniqueID", namespace = "http://www.opentravel.org/OTA/2003/05")
    public UniqueID getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(UniqueID uniqueID) {
        this.uniqueID = uniqueID;
    }
}
