package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.OtaInnOtaDto;
import org.apache.ibatis.annotations.Param;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/15
 * @version: v1.0.0
 */
public interface IOtaInnOtaService {

    /**
     *
     * @param id 客栈渠道关联对象id
     * @param companyId 公司id
     */
    OtaInnOtaDto findOtaInnOtaByTBHotelId( String id,String companyId);

    /**
     * 根据客栈id ... 查询绑定定期。
     * @param bangInnId 客栈绑定id
     * @param otaInfoId 渠道id
     * @param companyId 公司id
     */
    OtaInnOtaDto findOtaInnOtaByOtaId(String bangInnId, String otaInfoId, String companyId);
}
