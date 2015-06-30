package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.OtaPriceModel;
import com.fanqielaile.toms.model.PriceModelEnum;

import java.math.BigDecimal;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public class OtaPriceModelDto extends OtaPriceModel {
    public static  OtaPriceModelDto toDto(String otaInnOtaId){
        OtaPriceModelDto otaPriceModel = new OtaPriceModelDto();
        otaPriceModel.setOtaWgId(otaInnOtaId);
        otaPriceModel.setPriceModelEnum(PriceModelEnum.GU_B);
        otaPriceModel.setPriceModelValue(new BigDecimal(1));
        return otaPriceModel;
    }
}
