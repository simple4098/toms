package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.fc.FcRatePlanDto;
import com.fanqielaile.toms.model.fc.FcRatePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC : 价格计划
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
public interface IFcRatePlanDao {

    void insertFcRatePlan(FcRatePlan  fcRatePlan);

    List<FcRatePlan> selectFcRatePlan(FcRatePlan fcRatePlan);

    void deletedRatePlan(@Param("ratePlanId")String ratePlanId);

    FcRatePlan selectFcRatePlanById(@Param("ratePlanId")String ratePlanId);
}
