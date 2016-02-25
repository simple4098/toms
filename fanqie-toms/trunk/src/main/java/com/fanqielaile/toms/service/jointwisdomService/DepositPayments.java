package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/2/24.
 */
@XmlRootElement(name = "DepositPayments", namespace = "http://www.opentravel.org/OTA/2003/05")
public class DepositPayments {
    private GuaranteePayment guaranteePayment;

    @XmlElement(name = "GuaranteePayment")
    public GuaranteePayment getGuaranteePayment() {
        return guaranteePayment;
    }

    public void setGuaranteePayment(GuaranteePayment guaranteePayment) {
        this.guaranteePayment = guaranteePayment;
    }
}
