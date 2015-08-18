package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
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
    public List<OtaInfoRefDto> findAllOtaByCompany(String companyCode) {
        return otaInfoDao.selectAllOtaByCompany(companyCode);
    }

    @Override
    public OtaInfoRefDto findAllOtaByCompanyAndType(String companyId, OtaType otaType) {
        return otaInfoDao.selectAllOtaByCompanyAndType(companyId,otaType.name());
    }

    @Override
    public List<OtaInfoRefDto> findOtaInfoList() {
        return otaInfoDao.selectOtaInfoList();
    }

    @Override
    public List<OtaInfoRefDto> findOtaInfoListByCompanyId(String companyId) {
        return otaInfoDao.selectOtaInfoListByCompanyId(companyId);
    }

    @Override
    public void saveOtaInfo(OtaInfoRefDto otaInfoRefDto)throws Exception{
        OtaInfoRefDto otaInfo = otaInfoDao.findOtaInfoByOtaIdAndCompanyId(otaInfoRefDto);
        if (otaInfo==null){
            otaInfoDao.saveOtaInfo(otaInfoRefDto);
        }else {
            throw new  TomsRuntimeException("此渠道已经开通了!");
        }
    }
}
