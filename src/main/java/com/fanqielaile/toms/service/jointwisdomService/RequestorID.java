package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.*;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "RequestorID", namespace = "http://www.opentravel.org/OTA/2003/05")
@XmlAccessorType(XmlAccessType.NONE)
public class RequestorID {
    private CompanyName companyName;
    private String type;
    private String IDContext;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "ID_Context")
    public String getIDContext() {
        return IDContext;
    }

    public void setIDContext(String IDContext) {
        this.IDContext = IDContext;
    }

    @XmlAttribute(name = "Type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "CompanyName", namespace = "http://www.opentravel.org/OTA/2003/05")
    public CompanyName getCompanyName() {
        return companyName;
    }

    public void setCompanyName(CompanyName companyName) {
        this.companyName = companyName;
    }
}
