package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.fc.FcRatePlan;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
public interface IFcRatePlanService {

    void saveFcRatePlan(FcRatePlan fcRatePlan);

    List<FcRatePlan> findFcRatePlan(FcRatePlan fcRatePlan);

    void deletedRatePlan(String companyId,String ratePlanId)throws Exception;
}
