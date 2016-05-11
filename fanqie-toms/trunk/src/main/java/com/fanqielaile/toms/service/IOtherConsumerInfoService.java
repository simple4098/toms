package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.OtherConsumerInfoDto;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.model.UserInfo;

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
     * 新增其他消费
     * @param priceRecordJsonBeans 其他消费项目
     * @param currentUser 当前用户
     */
    void saveOtherConsumerInfo(OtherConsumerInfoDto priceRecordJsonBeans,UserInfo currentUser) throws Exception;

    /**
     * 更新
     * @param priceRecordJsonBeans 其他消费项目
     * @param currentUser 当前用户
     */
    void updateOtherConsumerInfo(OtherConsumerInfoDto priceRecordJsonBeans, UserInfo currentUser)throws Exception;

    /**
     * 删除其他消费
     * @param consumerInfoId 其他消费id， 并且删除掉下面的子节点
     * @throws Exception
     */
    void deleteOtherConsumerInfo(String consumerInfoId) throws Exception;

    /**
     * 查询所有的子节点消费项目
     * @param companyId 公司id
     */
    OtherConsumerInfoDto findChildOtherConsumerInfo(String companyId);

    /**
     * 更新公司个性化功能状态
     * @param companyId 公司id
     * @param status 状态
     */
    void updateFunction(String companyId, String status);
    
    /**
     * 根据其他消费项目id获取该消费项目订单记录数
     * @param consumerInfoId 其他消费项目id
     * @return
     */
	Integer getOrderRecordCount(String consumerInfoId);
}

