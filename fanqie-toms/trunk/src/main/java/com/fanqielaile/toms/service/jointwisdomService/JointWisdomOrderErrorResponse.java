package com.fanqielaile.toms.service.jointwisdomService;

import com.fanqie.jw.response.order1.baseResponse.Error;
import com.fanqie.jw.util.JointWisdomUtils;
import com.fanqie.support.OtaRequest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by wangdayin on 2016/1/13.
 */
@XmlRootElement(name = "OTA_HotelResRS", namespace = "http://www.opentravel.org/OTA/2003/05")
public class JointWisdomOrderErrorResponse extends OtaRequest {

    private String echoToken;
    private String timeStamp;
    private String version;
    private String xmlns = "http://www.opentravel.org/OTA/2003/05";
    private String responseType;

    private Error errors;

    @XmlElement(name = "Errors")
    public Error getErrors() {
        return errors;
    }

    public void setErrors(Error errors) {
        this.errors = errors;
    }

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

    public static com.fanqie.jw.response.order1.JointWisdomOrderErrorResponse getBasicError(String responseType, String version, String errorText) {
        com.fanqie.jw.response.order1.JointWisdomOrderErrorResponse result = new com.fanqie.jw.response.order1.JointWisdomOrderErrorResponse();
        Error error = new Error();
        error.setError(errorText);
        result.setErrors(error);
        result.setResponseType(responseType);
        result.setVersion(version);
        return result;
    }
}
