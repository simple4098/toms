package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.IOtaCommissionPercentDao;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.dto.ParamJson;
import com.fanqielaile.toms.enums.OperateType;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.OtaCommissionPercent;
import com.fanqielaile.toms.service.ICommissionService;
import com.fanqielaile.toms.support.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/26
 * @version: v1.0.0
 */
@Service
/*@LogModule("绿番茄更新渠道佣金")*/
public class CommissionService implements ICommissionService {
    private static final Logger log = Logger.getLogger(CommissionService.class);
    @Resource
    private IOtaCommissionPercentDao commissionPercentDao;
    @Resource
    private CompanyDao companyDao;


    @Override
    public void updateCommission(ParamJson paramJson)throws Exception {
        Company company = companyDao.selectCompanyByCompanyCode(paramJson.getCompanyCode());
        List<OtaCommissionPercent> list = new ArrayList<OtaCommissionPercent>();
        OtaCommissionPercent commissionPercent = null;
        Map<String,Double> commission = paramJson.getCommission();
        for (Map.Entry<String, Double> entry : commission.entrySet()){
            commissionPercent = new OtaCommissionPercent();
            commissionPercent.setCommissionPercent(Double.valueOf(entry.getValue()));
            commissionPercent.setCompanyId(company.getId());
            commissionPercent.setsJiaModel(entry.getKey());
            commissionPercent.setOtaId(company.getOtaId());
            OtaCommissionPercentDto commissionDto = commissionPercentDao.selectCommission(commissionPercent);
            if (commissionDto!=null){
                commissionDto.setCommissionPercent(commissionPercent.getCommissionPercent());
                commissionPercentDao.updateOtaCommission(commissionDto);
                log.info("====佣金比更新===="+commissionDto.getCommissionPercent()+" type:"+commissionDto.getsJiaModel()+" otaId:"+commissionDto.getOtaId());
            }else {
                commissionPercentDao.saveOtaCommission(commissionPercent);
                log.info("====佣金比新增===="+commissionPercent.getCommissionPercent()+" type:"+commissionPercent.getsJiaModel()+" otaId:"+commissionPercent.getOtaId());
            }
            list.add(commissionPercent);
            addNewModel(list,commissionPercent);
        }
        /*OtaCommissionPercent percent = new OtaCommissionPercent(company.getOtaId(), company.getId(), null, OperateType.NEW);
        List<OtaCommissionPercentDto> percents = commissionPercentDao.selectCommissionList(percent);
        if (percents!=null && percents.size() != list.size()){
            for (OtaCommissionPercentDto c:percents){
                boolean b = isContains(list, c);
                if (!b){
                    commissionPercentDao.deletedOtaCommission(c);
                }
            }

        }*/
    }

    private void addNewModel(List<OtaCommissionPercent> list, OtaCommissionPercent commissionPercent) {
        if (Constants.MAI.equals(commissionPercent.getsJiaModel())){
            commissionPercent.setsJiaModel(Constants.MAI2DI);
            commission(list,commissionPercent);
        }
        if (Constants.MAI2DI.equals(commissionPercent.getsJiaModel())){
            commissionPercent.setsJiaModel(Constants.MAI);
            commission(list,commissionPercent);
        }
    }

    public void  commission(List<OtaCommissionPercent> list,OtaCommissionPercent commissionPercent){
        OtaCommissionPercentDto commissionDto = commissionPercentDao.selectCommission(commissionPercent);
        if (commissionDto!=null){
            commissionDto.setCommissionPercent(commissionPercent.getCommissionPercent());
            commissionPercentDao.updateOtaCommission(commissionDto);
            log.info("佣金比更新===="+commissionDto.getCommissionPercent()+" type:"+commissionDto.getsJiaModel()+" otaId:"+commissionDto.getOtaId());
        }else {
            commissionPercent.setUuid(UUID.randomUUID().toString());
            commissionPercentDao.saveOtaCommission(commissionPercent);
            log.info("佣金比新增===="+commissionPercent.getCommissionPercent()+" type:"+commissionPercent.getsJiaModel()+" otaId:"+commissionPercent.getOtaId());
        }
        list.add(commissionPercent);
    }

    private boolean isContains( List<OtaCommissionPercent> list,OtaCommissionPercentDto commissionPercent){
        if (!CollectionUtils.isEmpty(list)){
            for (OtaCommissionPercent dto:list){
                if ( dto.getsJiaModel().equals(commissionPercent.getsJiaModel())){
                    return true;
                }
            }
        }
        return false;
    }
}
