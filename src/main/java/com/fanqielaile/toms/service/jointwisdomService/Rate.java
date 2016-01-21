package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/11.
 * 价格
 */
@XmlRootElement(name = "Rate", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Rate {
    private String effectiveDate;//价格开始时间
    private String expireDate;//价格结束时间
    private Base base;//费用类型

    @XmlElement(name = "Base")
    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    @XmlAttribute(name = "EffectiveDate")
    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @XmlAttribute(name = "ExpireDate")
    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
