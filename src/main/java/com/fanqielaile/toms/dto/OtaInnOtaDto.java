package com.fanqielaile.toms.dto;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaInnOta;
import com.fanqielaile.toms.support.util.Constants;

import java.math.BigDecimal;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaInnOtaDto extends OtaInnOta {

    //价格模式的值
    private BigDecimal priceModelValue;

    public BigDecimal getPriceModelValue() {
        return priceModelValue;
    }

    public void setPriceModelValue(BigDecimal priceModelValue) {
        this.priceModelValue = priceModelValue;
    }



    public static OtaInnOtaDto  toDto(String hid,String innName,String companyId,TBParam tbParam,String bangInnId,String otaInfoId){
        OtaInnOtaDto otaInnOta = new OtaInnOtaDto();
        otaInnOta.setAliasInnName(innName);
        otaInnOta.setCompanyId(companyId);
        otaInnOta.setCommissionPercent(new BigDecimal(1));
        otaInnOta.setPriceModel(tbParam.getPriceModel());
        otaInnOta.setsJiaModel(tbParam.getsJiaModel());
        otaInnOta.setInnId(Integer.valueOf(tbParam.getInnId()));
        otaInnOta.setWgHid(String.valueOf(hid));
        otaInnOta.setOtaId(tbParam.getOtaId());
        otaInnOta.setBangInnId(bangInnId);
        otaInnOta.setOtaInfoId(otaInfoId);
        otaInnOta.setSj(tbParam.isSj() ? 1 : 0);
        return otaInnOta;
    }
    public static OtaInnOtaDto  toFcDto(Long hid,String innName,Integer innId,Company company,String bangInnId,String otaInfoId){
        OtaInnOtaDto otaInnOta = new OtaInnOtaDto();
        otaInnOta.setAliasInnName(innName);
        otaInnOta.setCompanyId(company.getId());
        otaInnOta.setCommissionPercent(new BigDecimal(1));
        otaInnOta.setInnId(innId);
        otaInnOta.setWgHid(String.valueOf(hid));
        otaInnOta.setBangInnId(bangInnId);
        otaInnOta.setOtaInfoId(otaInfoId);
        otaInnOta.setSj(1);
        otaInnOta.setPriceModel(Constants.MAI);
        otaInnOta.setsJiaModel(Constants.MAI);
        otaInnOta.setOtaId(String.valueOf(company.getOtaId()));
        return otaInnOta;
    }
}
