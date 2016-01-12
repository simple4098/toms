package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.fc.OtaRatePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC : 价格计划
 * @author : 番茄木-ZLin
 * @data : 2015/9/21
 * @version: v1.0.0
 */
public interface IFcRatePlanDao {

    void insertFcRatePlan(OtaRatePlan otaRatePlan);

    List<OtaRatePlan> selectFcRatePlan(OtaRatePlan otaRatePlan);

    void deletedRatePlan(@Param("ratePlanId")String ratePlanId);

    OtaRatePlan selectFcRatePlanById(@Param("ratePlanId")String ratePlanId);
    
    /**
     *  搜索携程酒店计划计划
     * @param fcRatePlan
     * @return
     */
    List<OtaRatePlan> selectCtripRatePlan();
    
    /**
     * 查询携程默认的价格计划
     * @return
     */
    OtaRatePlan  selectDefaultCtripRatePlan();

    /**
     * 通过公司id，otaid和价格计划code查询价格计划详细信息
     *
     * @param companyId
     * @param otaId
     * @param ratePlanCode
     * @return
     */
    OtaRatePlan selectRatePlanByCompanyIdAndOtaIdAndRateCode(@Param("companyId") String companyId, @Param("otaId") String otaId, @Param("ratePlanCode") String ratePlanCode);
}
