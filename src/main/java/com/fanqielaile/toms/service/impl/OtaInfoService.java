package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.IOtaCommissionPercentDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaCommissionPercent;
import com.fanqielaile.toms.model.Result;
import com.fanqielaile.toms.service.IOtaInfoService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import com.fanqielaile.toms.support.util.Constants;
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
    @Resource
    private IOtaCommissionPercentDao commissionPercentDao;
    @Resource
    private CompanyDao companyDao;

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
    public List<OtaInfoRefDto> findAllOtaInfo() {
        return  otaInfoDao.selectAllOtaInfo();
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

    @Override
    public OtaInfoRefDto findOtaInfoByCompanyIdAndOtaInnOtaId(String companyId, String otaInfoId) {
        OtaInfoRefDto infoRefDto = otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(companyId, otaInfoId);
        if (infoRefDto!=null){
            Company company = companyDao.selectCompanyById(companyId);
            OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(new OtaCommissionPercent(company.getOtaId(), company.getId(), infoRefDto.getUsedPriceModel().name()));
            infoRefDto.setCommissionPercentDto(commission);
            infoRefDto.setCompanyCode(company.getCompanyCode());
            return infoRefDto;
        }
        return null;
    }

    @Override
    public OtaInfoRefDto findOtaInfo(String otaInfoId) {
        return otaInfoDao.selectOtaInfo(otaInfoId);
    }

    @Override
    public Result updateOtaInfoTbType(OtaInfoRefDto otaInfoRefDto) {
        Result result = new Result();
        if (otaInfoRefDto.getTbType()!=null){
            otaInfoDao.updateOtaInfoTbType(otaInfoRefDto);
            result.setStatus(Constants.SUCCESS200);
        }else {
            result.setStatus(Constants.ERROR400);
        }
        return result;
    }

    @Override
    public List<String> findOtaByAppKey(OtaInfoRefDto otaInfo) {
        return otaInfoDao.selectOtaByAppKey(otaInfo);
    }

    @Override
    public OtaInfoRefDto findOtaInfoRefDtoById(String otaInfoRefId) {
        return otaInfoDao.selectOtaInfoRefDtoById(otaInfoRefId);
    }

    @Override
    public void updateOtaInfo(OtaInfoRefDto otaInfoRefDto) {
        otaInfoDao.updateOtaInfo(otaInfoRefDto);
    }

    @Override
    public List<OtaInfoRefDto> findAllOtaByType(OtaType credit) {
        return otaInfoDao.selectAllOtaByType(credit.name());
    }
}
