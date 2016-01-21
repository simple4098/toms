
package com.fanqielaile.toms.service.jointwisdomService;


import com.fanqie.support.OtaRequest;

import javax.xml.bind.annotation.*;

/**
 * Created by wangdayin on 2016/1/18.
 * 众荟试订单传入对象
 */
@XmlRootElement(name = "OTAHotelAvailRQ", namespace = "http://www.opentravel.org/OTA/2003/05")
@XmlAccessorType(XmlAccessType.NONE)
public class OTAHotelAvailRQ extends OtaRequest {
    private POS pos;
    private AvailRequestSegments availRequestSegments;
    private String echoToken;
    private String timeStamp;
    private String version;
    private String xmlns;

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

    @XmlElement(name = "AvailRequestSegments", namespace = "http://www.opentravel.org/OTA/2003/05")
    public AvailRequestSegments getAvailRequestSegments() {
        return availRequestSegments;
    }

    public void setAvailRequestSegments(AvailRequestSegments availRequestSegments) {
        this.availRequestSegments = availRequestSegments;
    }

    @XmlElement(name = "POS", namespace = "http://www.opentravel.org/OTA/2003/05")
    public POS getPos() {
        return pos;
    }

    public void setPos(POS pos) {
        this.pos = pos;
    }
}
