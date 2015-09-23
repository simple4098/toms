package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.enums.BedType;
import com.fanqielaile.toms.enums.CurrencyCode;
import com.fanqielaile.toms.enums.PayMethod;
import com.fanqielaile.toms.model.fc.FcRatePlan;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/9/23
 * @version: v1.0.0
 */
public class FcRatePlanDto extends FcRatePlan {

    private String currencyValue;
    //支付类型
    private String payMethodValue;
    //床型
    private String bedTypeValue;

    public String getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    public String getPayMethodValue() {
        return payMethodValue;
    }

    public void setPayMethodValue(String payMethodValue) {
        this.payMethodValue = payMethodValue;
    }

    public String getBedTypeValue() {
        return bedTypeValue;
    }

    public void setBedTypeValue(String bedTypeValue) {
        this.bedTypeValue = bedTypeValue;
    }
}
