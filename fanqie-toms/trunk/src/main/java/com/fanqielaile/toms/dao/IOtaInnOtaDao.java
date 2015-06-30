package com.fanqielaile.toms.dao;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dto.OtaInnOtaDto;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/19
 * @version: v1.0.0
 */
public interface IOtaInnOtaDao {

    void saveOtaInnOta(OtaInnOtaDto otaInnOtaDto);

    OtaInnOtaDto findOtaInnOta(String id);

    /**
     * 查询某一个企业下所关联的 ota_inn_ota id
     * @param companyCode 企业唯一标示
     */
    List<String> findOtaInnOtaIdsByCompanyCode(String companyCode);

    void updateOtaInnOtaCommission(@Param("listIds")List<String> ids,@Param("commissionPercent")BigDecimal commissionPercent,@Param("commissionType")String type);

    /**
     * 根据参数查询 客栈往TP店推的一些信息 比如 hid
     * @param tbParam
     */
    OtaInnOtaDto findOtaInnOtaByParams(TBParam tbParam);

    OtaInnOtaDto selectOtaInnOtaByTBHotelId(@Param("otaHotelId") String otaHotelId);

}
