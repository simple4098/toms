package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "SpecialRequests", namespace = "http://www.opentravel.org/OTA/2003/05")
public class SpecialRequest {
    private List<Text> texts;

    @XmlElement(name = "Text", namespace = "http://www.opentravel.org/OTA/2003/05")
    public List<Text> getTexts() {
        return texts;
    }

    public void setTexts(List<Text> texts) {
        this.texts = texts;
    }
}
