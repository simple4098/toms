package com.fanqielaile.toms.service.jointwisdomService;

import com.fanqie.support.OtaRequest;

import javax.xml.bind.annotation.*;

/**
 * Created by wangdayin on 2016/1/21.\
 * 众荟下单请求
 */
@XmlRootElement(name = "OTA_HotelResRQ", namespace = "http://www.opentravel.org/OTA/2003/05")
@XmlAccessorType(XmlAccessType.NONE)
public class OTAHotelResRQ extends OtaRequest {
    private HotelReservations hotelReservations;
    private String echoToken;
    private String timeStamp;
    private String version;
    private String xmlns;
    private String resStatus;
    private POS pos;

    @XmlElement(name = "POS", namespace = "http://www.opentravel.org/OTA/2003/05")
    public POS getPos() {
        return pos;
    }

    public void setPos(POS pos) {
        this.pos = pos;
    }

    @XmlAttribute(name = "ResStatus")
    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    @XmlAttribute(name = "EchoToken")
    public String getEchoToken() {
        return echoToken;
    }

    public void setEchoToken(String echoToken) {
        this.echoToken = echoToken;
    }


    @XmlAttribute(name = "TimeStamp")
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @XmlAttribute(name = "Version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    @XmlElement(name = "HotelReservations", namespace = "http://www.opentravel.org/OTA/2003/05")
    public HotelReservations getHotelReservations() {
        return hotelReservations;
    }

    public void setHotelReservations(HotelReservations hotelReservations) {
        this.hotelReservations = hotelReservations;
    }

}
