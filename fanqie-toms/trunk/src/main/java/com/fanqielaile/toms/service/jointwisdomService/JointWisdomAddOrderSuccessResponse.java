package com.fanqielaile.toms.service.jointwisdomService;

import com.fanqie.jw.enums.ResponseType;
import com.fanqie.jw.response.order1.newOrder.HotelReservation;
import com.fanqie.jw.response.order1.newOrder.HotelReservationID;
import com.fanqie.jw.response.order1.newOrder.ResGlobalInfo;
import com.fanqie.jw.util.JointWisdomUtils;
import com.fanqie.support.OtaRequest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/13.
 */
@XmlRootElement(name = "OTA_HotelResRS", namespace = "http://www.opentravel.org/OTA/2003/05")
public class JointWisdomAddOrderSuccessResponse extends OtaRequest {
    private List<HotelReservation> hotelReservations;
    private String message;
    private String echoToken;
    private String timeStamp;
    private String version;
    private String xmlns = "http://www.opentravel.org/OTA/2003/05";
    private String responseType;

    @XmlAttribute(name = "EchoToken")
    public String getEchoToken() {
        return String.valueOf(new Date().getTime());
    }

    public void setEchoToken(String echoToken) {
        this.echoToken = echoToken;
    }

    @XmlAttribute(name = "TimeStamp")
    public String getTimeStamp() {
        return JointWisdomUtils.getTimeStamp();
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

    @XmlAttribute(name = "xmlns")
    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }


    @XmlAttribute(name = "ResResponseType")
    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    @XmlElement(name = "Success")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(name = "HotelReservation")
    @XmlElementWrapper(name = "HotelReservations")
    public List<HotelReservation> getHotelReservations() {
        return hotelReservations;
    }

    public void setHotelReservations(List<HotelReservation> hotelReservations) {
        this.hotelReservations = hotelReservations;
    }

    public static List<HotelReservation> getHotelReservationResult(String orderId, String pmsOrderId) {
        List<HotelReservation> result = new ArrayList<HotelReservation>();
        HotelReservation hotelReservation = new HotelReservation();
        ResGlobalInfo resGlobalInfo = new ResGlobalInfo();
        List<HotelReservationID> hotelReservationIDList = new ArrayList<HotelReservationID>();
        //ota
        HotelReservationID otaId = new HotelReservationID();
        otaId.setResType(ResponseType.R24.getText());
        otaId.setResValue(orderId);
        hotelReservationIDList.add(otaId);
        //pms
        HotelReservationID pmsId = new HotelReservationID();
        pmsId.setResType(ResponseType.R10.getText());
        pmsId.setResValue(pmsOrderId);
        hotelReservationIDList.add(pmsId);
        resGlobalInfo.setHotelReservationIDList(hotelReservationIDList);
        hotelReservation.setResGlobalInfo(resGlobalInfo);
        result.add(hotelReservation);
        return result;
    }
}
