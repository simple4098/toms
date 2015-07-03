package com.fanqielaile.toms.service;

import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.OtaInfo;

import java.util.List;

/**
 * DESC : 查询企业开通渠道
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
public interface IOtaInfoService {

    List<OtaInfo> findAllOtaByCompany(String companyCode);

    OtaInfo findAllOtaByCompanyAndType(String companyCode,OtaType otaType);
}
