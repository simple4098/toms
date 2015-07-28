package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtaInnRoomTypeGoodsDto;
import org.apache.ibatis.annotations.Param;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public interface IOtaInnRoomTypeGoodsDao {

    void saveRoomTypeGoodsRp(OtaInnRoomTypeGoodsDto goodsDto);

    OtaInnRoomTypeGoodsDto findRoomTypeByRid(@Param("rid")Long rid);

    /**
     * 更新 商品推送时间
     * @param innRoomTypeGoodsDto
     */
    void updateRoomTypeGoodsProductDate(OtaInnRoomTypeGoodsDto innRoomTypeGoodsDto);

    //删除客栈宝贝关系
    void deletedGoods(@Param("otaWgId")String otaWgId);

    OtaInnRoomTypeGoodsDto selectGoodsByRoomTypeIdAndCompany(@Param("companyId")String companyId, @Param("roomTypeId")Integer roomTypeId);
}
