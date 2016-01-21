package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "StayDateRange", namespace = "http://www.opentravel.org/OTA/2003/05")
public class StayDateRange {
    private String start;
    private String end;

    @XmlAttribute(name = "Start")
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    @XmlAttribute(name = "End")
    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
