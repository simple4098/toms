package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/18.
 */
@XmlRootElement(name = "HotelSearchCriteria", namespace = "http://www.opentravel.org/OTA/2003/05")
public class HotelSearchCriteria {
    private Criterion criterion;

    @XmlElement(name = "Criterion", namespace = "http://www.opentravel.org/OTA/2003/05")
    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }
}
