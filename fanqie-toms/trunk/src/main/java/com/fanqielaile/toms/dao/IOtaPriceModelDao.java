package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtaPriceModelDto;
import org.apache.ibatis.annotations.Param;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public interface IOtaPriceModelDao {

    void  savePriceModel(OtaPriceModelDto otaPriceModelDto);

    OtaPriceModelDto findOtaPriceModelByWgOtaId(@Param("wgOtaId")String id);
}
