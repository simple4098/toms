package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.model.OtaCommissionPercent;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/11/6
 * @version: v1.0.0
 */
public interface IOtaCommissionPercentDao {

    OtaCommissionPercentDto selectCommission(OtaCommissionPercent commissionPercent);

    List<OtaCommissionPercentDto> selectCommissionList(OtaCommissionPercent commissionPercent);

    void saveOtaCommission(OtaCommissionPercent commissionPercent);

    void updateOtaCommission(OtaCommissionPercent commissionPercent);

    void deletedOtaCommission(OtaCommissionPercent commissionPercent);



}
