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

    FcRoomTypeFqDto selectFcRoomTypeFqById(@Param("id")String fcRoomTypeFqId);

    //匹配房型跟价格计划关联
    void updateRoomTypeFqRatePlan(@Param("fcRoomTypeFqId")String fcRoomTypeFqId, @Param("ratePlanId")String ratePlanId);

    /**
     * 根据价格计划id 查询已经关联过后的 匹配房型
     * @param ratePlanId 价格计划id
     */
    List<FcRoomTypeFqDto> selectFcRoomTypeFqByRatePlanId(@Param("ratePlanId")String ratePlanId);

    /**
     * 是否上架
     * @param matchRoomTypeId id
     * @param sj 1上架  0 下架  -1 没有上架
     */
    void updateRoomTypeFqSj(@Param("id")String matchRoomTypeId,@Param("sj") int sj);

    //根据id删除匹配关系
    void deletedFcRoomTypeById(@Param("id")String fqRoomTypeFcId);

    /**
     *
     * @param innId 客栈id
     * @param companyId 公司id
     * @param otaInfoId 渠道id
     */
    void deletedFcRoomTypeFqByInnIdAndCompanyId(@Param("innId")String innId, @Param("companyId")String companyId, @Param("otaInfoId")String otaInfoId);

    /**
     * 根据客栈id 房型id 渠道id 查询房仓 房型匹配情况
     * @param innId 客栈id
     * @param roomTypeId 房型id
     * @param otaInfoId 渠道id
     * @param companyId 公司id
     */

    FcRoomTypeFqDto findRoomTypeFqInnIdRoomIdOtaInfoId(@Param("innId")Integer innId, @Param("roomTypeId")Integer roomTypeId, @Param("otaInfoId")String otaInfoId,@Param("companyId")String companyId);

    //查询（客栈）上/下架状态
    List<FcRoomTypeFqDto> selectFcRoomTypeFqBySJ(FcRoomTypeFqDto fcRoomTypeFqDto);
}
