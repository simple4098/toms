package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/11.
 */
@XmlRootElement(name = "Base", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Base {
    private String amountAfterTax;//税后价格
    private String currencyCode = "RMB";//货币类型
    private String amountBeforeTax;//税后价格
    private Taxes taxes;

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

    @XmlElement(name = "Taxes")
    public Taxes getTaxes() {
        return taxes;
    }

    public void setTaxes(Taxes taxes) {
        this.taxes = taxes;
    }
}
