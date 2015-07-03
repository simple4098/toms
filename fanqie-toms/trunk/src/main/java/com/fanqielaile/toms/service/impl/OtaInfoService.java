package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.OtaInfo;
import com.fanqielaile.toms.service.IOtaInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/7/3
 * @version: v1.0.0
 */
@Service
public class OtaInfoService implements IOtaInfoService {
    @Resource
    private IOtaInfoDao otaInfoDao;

    @Override
    public List<OtaInfo> findAllOtaByCompany(String companyCode) {
        return otaInfoDao.selectAllOtaByCompany(companyCode);
    }

    @Override
    public OtaInfo findAllOtaByCompanyAndType(String companyId, OtaType otaType) {
        return otaInfoDao.selectAllOtaByCompanyAndType(companyId,otaType.name());
    }
}
