package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "RoomStayCandidates", namespace = "http://www.opentravel.org/OTA/2003/05")
public class RoomStayCandidates {
    private RoomStayCandidate roomStayCandidate;

    @XmlElement(name = "RoomStayCandidate", namespace = "http://www.opentravel.org/OTA/2003/05")
    public RoomStayCandidate getRoomStayCandidate() {
        return roomStayCandidate;
    }

    public void setRoomStayCandidate(RoomStayCandidate roomStayCandidate) {
        this.roomStayCandidate = roomStayCandidate;
    }
}
