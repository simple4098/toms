package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.IFcRatePlanDao;
import com.fanqielaile.toms.model.fc.FcRatePlan;
import com.fanqielaile.toms.service.IFcRatePlanService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
@Service
public class FcRatePlanService implements IFcRatePlanService {
    @Resource
    private IFcRatePlanDao fcRatePlanDao;

    @Override
    public void saveFcRatePlan(FcRatePlan fcRatePlan) {
        List<FcRatePlan> ratePlans = fcRatePlanDao.selectFcRatePlan(fcRatePlan);
        if (CollectionUtils.isEmpty(ratePlans)){
            fcRatePlanDao.insertFcRatePlan(fcRatePlan);
        }

    }

    @Override
    public List<FcRatePlan> findFcRatePlan(FcRatePlan fcRatePlan) {
        return fcRatePlanDao.selectFcRatePlan(fcRatePlan);
    }

    @Override
    public void deletedRatePlan(String ratePlanId) {
        fcRatePlanDao.deletedRatePlan(ratePlanId);
    }
}
