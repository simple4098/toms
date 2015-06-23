package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtaInnOtaDto;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public interface IOtaInnOtaDao {

    void saveOtaInnOta(OtaInnOtaDto otaInnOtaDto);

    OtaInnOtaDto findOtaInnOta(String id);
}
