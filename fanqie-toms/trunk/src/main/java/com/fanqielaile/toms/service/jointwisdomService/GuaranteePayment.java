package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/2/24.
 */
@XmlRootElement(name = "GuaranteePayment", namespace = "http://www.opentravel.org/OTA/2003/05")
public class GuaranteePayment {
    private List<AcceptedPayment> acceptedPayments;

    @XmlElement(name = "AcceptedPayment")
    @XmlElementWrapper(name = "AcceptedPayments")
    public List<AcceptedPayment> getAcceptedPayments() {
        return acceptedPayments;
    }

    public void setAcceptedPayments(List<AcceptedPayment> acceptedPayments) {
        this.acceptedPayments = acceptedPayments;
    }
}
