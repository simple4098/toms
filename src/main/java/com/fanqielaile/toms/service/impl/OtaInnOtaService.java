package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.IOtaInnOtaDao;
import com.fanqielaile.toms.dto.OtaInnOtaDto;
import com.fanqielaile.toms.service.IOtaInnOtaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/15
 * @version: v1.0.0
 */
@Service
public class OtaInnOtaService implements IOtaInnOtaService {
    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Override
    public OtaInnOtaDto findOtaInnOtaByTBHotelId(String id,String companyId) {
        return otaInnOtaDao.selectOtaInnOtaById(id,companyId);
    }

    @Override
    public OtaInnOtaDto findOtaInnOtaByOtaId(String bangInnId, String otaInfoId, String companyId) {
        return  otaInnOtaDao.selectOtaInnOtaByBangId(bangInnId,companyId,otaInfoId);

    }
}
