package com.fanqielaile.toms.service.jointwisdomService;

import com.fanqie.jw.enums.Version;
import com.fanqie.jw.util.JointWisdomUtils;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/11.
 * 众荟试订单成功返回对象
 */
@XmlRootElement(name = "OTA_HotelAvailRS", namespace = "http://www.opentravel.org/OTA/2003/05")
@XmlAccessorType(XmlAccessType.NONE)
public class JointWisdomAvailCheckOrderSuccessResponse extends JointWisdomAvailCheckOrderErrorResponse {
    private String success;
    private String echoToken;
    private String timeStamp;
    private String version;
    private List<RoomStay> roomStays;

    @XmlAttribute(name = "Version")
    public String getVersion() {
        return Version.V22.getText();
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlAttribute(name = "TimeStamp")
    public String getTimeStamp() {
        return JointWisdomUtils.getTimeStamp();
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @XmlAttribute(name = "EchoToken")
    public String getEchoToken() {
        return String.valueOf(new Date().getTime());
    }

    public void setEchoToken(String echoToken) {
        this.echoToken = echoToken;
    }

    @XmlElement(name = "Success")
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @XmlElement(name = "RoomStay")
    @XmlElementWrapper(name = "RoomStays")
    public List<RoomStay> getRoomStays() {
        return roomStays;
    }

    public void setRoomStays(List<RoomStay> roomStays) {
        this.roomStays = roomStays;
    }

    public static JointWisdomAvailCheckOrderSuccessResponse getBasicError(String errorText) {
        JointWisdomAvailCheckOrderSuccessResponse result = new JointWisdomAvailCheckOrderSuccessResponse();
        Error error = new Error();
        error.setError(errorText);
        result.setErrors(error);
        return result;
    }
}
