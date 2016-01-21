package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/11.
 * 总价：税前、税后、货币代码
 */
@XmlRootElement(name = "Total", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Total {
    private String amountAfterTax;
    private String currencyCode = "RMB";
    private String amountBeforeTax;
    private Taxes taxes;

    @XmlElement(name = "Taxes", namespace = "http://www.opentravel.org/OTA/2003/05")
    public Taxes getTaxes() {
        return taxes;
    }

    public void setTaxes(Taxes taxes) {
        this.taxes = taxes;
    }

    @XmlAttribute(name = "AmountAfterTax")
    public String getAmountAfterTax() {
        return amountAfterTax;
    }

    public void setAmountAfterTax(String amountAfterTax) {
        this.amountAfterTax = amountAfterTax;
    }

    @XmlAttribute(name = "CurrencyCode")
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @XmlAttribute(name = "AmountBeforeTax")
    public String getAmountBeforeTax() {
        return amountBeforeTax;
    }

    public void setAmountBeforeTax(String amountBeforeTax) {
        this.amountBeforeTax = amountBeforeTax;
    }
}
