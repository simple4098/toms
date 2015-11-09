package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.model.OtaCommissionPercent;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/11/6
 * @version: v1.0.0
 */
public interface IOtaCommissionPercentDao {

    OtaCommissionPercentDto selectCommission(OtaCommissionPercent commissionPercent);

    void saveOtaCommission(OtaCommissionPercent commissionPercent);

    void updateOtaCommission(OtaCommissionPercent commissionPercent);

}
