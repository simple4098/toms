package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.OtherConsumerInfoDto;

import java.util.List;

/**
 * DESC : 其他消费service
 * @author : 番茄木-ZLin
 * @data : 2016/3/29
 * @version: v1.0.0
 */
public interface IOtherConsumerInfoService {

    /**
     * 根据公司id查询 其他消费数据
     * @param companyId 公司id
     * @param consumerInfoId 其他消费的父id， 如果为null，查询全部的
     */
    List<OtherConsumerInfoDto> findOtherConsumerInfo(String companyId,String consumerInfoId);

    /**
     * 查询此公司是否开通其他消费管理
     * @param companyId 公司id
     */
    boolean findOtherConsumerFunction(String companyId);

    /**
     *
     * @param priceRecordJsonBeans  新增、编辑
     */
    void saveOtherConsumerInfo(OtherConsumerInfoDto priceRecordJsonBeans);
}

