package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "POS", namespace = "http://www.opentravel.org/OTA/2003/05")
public class POS {
    private Source source;

    @XmlElement(name = "Source", namespace = "http://www.opentravel.org/OTA/2003/05")
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}

