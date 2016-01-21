package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "GuestCount", namespace = "http://www.opentravel.org/OTA/2003/05")
public class GuestCount {
    private String ageQualifyingCode;
    private String count;

    @XmlAttribute(name = "AgeQualifyingCode")
    public String getAgeQualifyingCode() {
        return ageQualifyingCode;
    }

    public void setAgeQualifyingCode(String ageQualifyingCode) {
        this.ageQualifyingCode = ageQualifyingCode;
    }

    @XmlAttribute(name = "Count")
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
