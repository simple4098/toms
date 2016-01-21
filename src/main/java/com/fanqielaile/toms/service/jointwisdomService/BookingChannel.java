package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "BookingChannel", namespace = "http://www.opentravel.org/OTA/2003/05")
public class BookingChannel {
    private String type;
    private CompanyName companyName;

    @XmlElement(name = "CompanyName", namespace = "http://www.opentravel.org/OTA/2003/05")
    public CompanyName getCompanyName() {
        return companyName;
    }

    public void setCompanyName(CompanyName companyName) {
        this.companyName = companyName;
    }

    @XmlAttribute(name = "Type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
