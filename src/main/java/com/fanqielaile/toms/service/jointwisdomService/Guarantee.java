package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "Guarantee", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Guarantee {
    private String guaranteeType;
    private GuaranteesAccepted guaranteesAccepted;

    @XmlElement(name = "GuaranteesAccepted", namespace = "http://www.opentravel.org/OTA/2003/05")
    public GuaranteesAccepted getGuaranteesAccepted() {
        return guaranteesAccepted;
    }

    public void setGuaranteesAccepted(GuaranteesAccepted guaranteesAccepted) {
        this.guaranteesAccepted = guaranteesAccepted;
    }

    @XmlAttribute(name = "GuaranteeType")
    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType;
    }
}
