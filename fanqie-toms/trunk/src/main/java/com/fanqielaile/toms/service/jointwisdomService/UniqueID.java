package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "UniqueID", namespace = "http://www.opentravel.org/OTA/2003/05")
public class UniqueID {
    private String type;
    private String IDContext;

    @XmlAttribute(name = "Type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute(name = "ID_Context")
    public String getIDContext() {
        return IDContext;
    }

    public void setIDContext(String IDContext) {
        this.IDContext = IDContext;
    }
}
