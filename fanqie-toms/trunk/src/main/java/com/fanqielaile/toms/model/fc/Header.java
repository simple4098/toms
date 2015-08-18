package com.fanqielaile.toms.model.fc;

import com.fanqie.util.DateUtil;
import com.fanqie.util.DcUtil;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/8/13
 * @version: v1.0.0
 */
@XmlRootElement(name = "Header")
public class Header {
    //请求时间戳
    private String TimeStamp = DateUtil.formatDateToString(new Date(),DateUtil.FORMAT_DATE_STR_SECOND);
    //房仓提供的合作商密钥
    private String PartnerCode;
    //请求类型
    private RequestType type;
    private String  RequestType;
    //签名
    private String Signature;

    private Header() {
    }
    public Header(RequestType requestType, String partnerCode, String securityCode) {
        type = requestType;
        PartnerCode = partnerCode;
        this.Signature = DcUtil.obtMd5(TimeStamp+PartnerCode+ DcUtil.obtMd5(securityCode)+type.name());
    }

    @XmlAttribute(name = "TimeStamp")
    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    @XmlAttribute(name = "PartnerCode")
    public String getPartnerCode() {
        return PartnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        PartnerCode = partnerCode;
    }

    @XmlAttribute(name = "PartnerCode")
    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    @XmlAttribute(name = "Signature")
    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
