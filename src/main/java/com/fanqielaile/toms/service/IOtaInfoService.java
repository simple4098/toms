package com.fanqielaile.toms.service;

import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.enums.OtaType;

import java.util.List;

/**
 * DESC : 查询企业开通渠道
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
public interface IOtaInfoService {

    List<OtaInfoRefDto> findAllOtaByCompany(String companyCode);

    OtaInfoRefDto findAllOtaByCompanyAndType(String companyCode,OtaType otaType);

    List<OtaInfoRefDto> findOtaInfoList();
    //渠道列表（没有开通的渠道也要显示出来）
    List<OtaInfoRefDto> findOtaInfoListByCompanyId(String companyId);

    //企业开通渠道
    void saveOtaInfo(OtaInfoRefDto otaInfoRefDto)throws Exception;
}
