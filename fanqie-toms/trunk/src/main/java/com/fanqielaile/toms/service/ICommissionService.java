package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.OtaCommissionPercent;

/**
 * DESC : 更新渠道下所有客栈的佣金比例
 * @author : 番茄木-ZLin
 * @data : 2015/6/26
 * @version: v1.0.0
 */
public interface ICommissionService {


    /**
     * 更新分佣模式
     */
    void updateCommission( OtaCommissionPercent commissionPercent)throws Exception;
}
