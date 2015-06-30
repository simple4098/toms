package com.fanqielaile.toms.dto;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.model.OtaInnOta;

import java.math.BigDecimal;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaInnOtaDto extends OtaInnOta {

    private Integer innId;
    //价格模式的值
    private BigDecimal priceModelValue;

    public BigDecimal getPriceModelValue() {
        return priceModelValue;
    }

    public void setPriceModelValue(BigDecimal priceModelValue) {
        this.priceModelValue = priceModelValue;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public static OtaInnOtaDto  toDto(Long hid,String innName,String companyId,TBParam tbParam){
        OtaInnOtaDto otaInnOta = new OtaInnOtaDto();
        otaInnOta.setAliasInnName(innName);
        otaInnOta.setCompanyId(companyId);
        otaInnOta.setCommissionPercent(new BigDecimal(1));
        otaInnOta.setPriceModel(tbParam.getPriceModel());
        otaInnOta.setsJiaModel(tbParam.getsJiaModel());
        otaInnOta.setInnId(Integer.valueOf(tbParam.getInnId()));
        otaInnOta.setWgHid(String.valueOf(hid));
        otaInnOta.setOtaId(tbParam.getOtaId());
        return otaInnOta;
    }
}
