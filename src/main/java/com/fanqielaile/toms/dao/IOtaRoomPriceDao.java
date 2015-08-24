package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtaRoomPriceDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/8/20
 * @version: v1.0.0
 */
public interface IOtaRoomPriceDao {

    OtaRoomPriceDto selectOtaRoomPriceDto(OtaRoomPriceDto otaRoomPriceDto);

    void  saveOtaRoomPriceDto(OtaRoomPriceDto otaRoomPriceDto);
}
