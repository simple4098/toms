package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.fc.OtaRatePlan;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
public interface IFcRatePlanService {

    void saveFcRatePlan(OtaRatePlan otaRatePlan);

    List<OtaRatePlan> findFcRatePlan(OtaRatePlan otaRatePlan);

    void deletedRatePlan(String companyId,String ratePlanId)throws Exception;
}
