package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.model.fc.FcRoomTypeFq;

import java.util.List;

/**
 * DESC : 房型匹配信息
 * @author : 番茄木-ZLin
 * @data : 2015/9/23
 * @version: v1.0.0
 */
public interface IFcRoomTypeFqService {

    /**
     * 查询客栈房型匹配信息
     * @param fcRoomTypeFq 查询条件集合
     * @return
     */
    List<FcRoomTypeFqDto> findFcRoomTypeFq(FcRoomTypeFqDto fcRoomTypeFq);

    /**
     * 更新匹配房型的 价格计划 - 如果此价格计划房仓上没有要同步到房仓
     * @param fcRoomTypeFqId 匹配房型对象id
     * @param ratePlanId 价格计划id
     */
    void updateRoomTypeRatePlan(String fcRoomTypeFqId, String ratePlanId)throws Exception;

    void updateSjMatchRoomType(String companyId, String matchRoomTypeId) throws Exception;
}
