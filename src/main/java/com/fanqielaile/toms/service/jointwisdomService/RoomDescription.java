package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/11.
 * 房型描述
 */
@XmlRootElement(name = "RoomDescription", namespace = "http://www.opentravel.org/OTA/2003/05")
public class RoomDescription {
    private String name;//房型名称
    private Text text;//房型描述

    @XmlAttribute(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Text")
    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
