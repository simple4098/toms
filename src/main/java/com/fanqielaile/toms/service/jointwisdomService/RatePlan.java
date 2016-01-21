package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/11.
 * 价格代码
 */
@XmlRootElement(name = "RatePlan", namespace = "http://www.opentravel.org/OTA/2003/05")
public class RatePlan {
    private String ratePlanCode;//价格代码
    private String ratePlanCategory = "501";//付款类型，16 现付，501 预付
    private RatePlanDescription ratePlanDescription;

    @XmlElement(name = "RatePlanDescription")
    public RatePlanDescription getRatePlanDescription() {
        return ratePlanDescription;
    }

    public void setRatePlanDescription(RatePlanDescription ratePlanDescription) {
        this.ratePlanDescription = ratePlanDescription;
    }

    @XmlAttribute(name = "RatePlanCode")
    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }

    @XmlAttribute(name = "RatePlanCategory")
    public String getRatePlanCategory() {
        return ratePlanCategory;
    }

    public void setRatePlanCategory(String ratePlanCategory) {
        this.ratePlanCategory = ratePlanCategory;
    }
}
