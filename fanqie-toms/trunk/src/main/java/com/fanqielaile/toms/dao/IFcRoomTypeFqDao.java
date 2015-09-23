package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.fc.FcRoomTypeFqDto;
import com.fanqielaile.toms.model.fc.FcRoomTypeFq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/22
 * @version: v1.0.0
 */
public interface IFcRoomTypeFqDao {


    void saveRoomTypeFq(FcRoomTypeFqDto fcRoomTypeFq);


    List<FcRoomTypeFqDto> selectFcRoomTypeFq(FcRoomTypeFqDto fcRoomTypeFq);

    FcRoomTypeFq selectFcRoomTypeFqById(@Param("id")String fcRoomTypeFqId);

    //匹配房型跟价格计划关联
    void updateRoomTypeFqRatePlan(@Param("fcRoomTypeFqId")String fcRoomTypeFqId, @Param("ratePlanId")String ratePlanId);
}
