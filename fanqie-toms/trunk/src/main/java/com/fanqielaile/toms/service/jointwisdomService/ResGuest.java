package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "ResGuest", namespace = "http://www.opentravel.org/OTA/2003/05")
public class ResGuest {
    private String resGuestRPH;
    private List<ProfileInfo> profileInfos;
    private ArrivalTransport arrivalTransport;
    private String departureTransport;

    @XmlElement(name = "DepartureTransport", namespace = "http://www.opentravel.org/OTA/2003/05")
    public String getDepartureTransport() {
        return departureTransport;
    }

    public void setDepartureTransport(String departureTransport) {
        this.departureTransport = departureTransport;
    }

    @XmlElement(name = "ArrivalTransport", namespace = "http://www.opentravel.org/OTA/2003/05")
    public ArrivalTransport getArrivalTransport() {
        return arrivalTransport;
    }

    public void setArrivalTransport(ArrivalTransport arrivalTransport) {
        this.arrivalTransport = arrivalTransport;
    }

    @XmlElement(name = "ProfileInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "Profiles")
    public List<ProfileInfo> getProfileInfos() {
        return profileInfos;
    }

    public void setProfileInfos(List<ProfileInfo> profileInfos) {
        this.profileInfos = profileInfos;
    }

    @XmlAttribute(name = "ResGuestRPH")
    public String getResGuestRPH() {
        return resGuestRPH;
    }

    public void setResGuestRPH(String resGuestRPH) {
        this.resGuestRPH = resGuestRPH;
    }
}
