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
}
