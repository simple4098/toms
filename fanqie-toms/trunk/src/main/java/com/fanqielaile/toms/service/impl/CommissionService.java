package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.IOtaCommissionPercentDao;
import com.fanqielaile.toms.dao.IOtaInnOtaDao;
import com.fanqielaile.toms.dto.OtaCommissionPercentDto;
import com.fanqielaile.toms.model.OtaCommissionPercent;
import com.fanqielaile.toms.service.ICommissionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    private IOtaInnOtaDao otaInnOtaDao;
    @Resource
    private IOtaCommissionPercentDao commissionPercentDao;
    @Resource
    private CompanyDao companyDao;


    @Override
    public void updateCommission(OtaCommissionPercent commissionPercent)throws Exception {
        OtaCommissionPercentDto commission = commissionPercentDao.selectCommission(commissionPercent);
        if (commission!=null){
            commission.setCommissionPercent(commissionPercent.getCommissionPercent());
            commissionPercentDao.updateOtaCommission(commission);
            log.info("====佣金比更新===="+commission.getCommissionPercent()+" type:"+commission.getsJiaModel()+" otaId:"+commission.getOtaId());
        }else {
            commissionPercentDao.saveOtaCommission(commissionPercent);
            log.info("====佣金比新增===="+commissionPercent.getCommissionPercent()+" type:"+commissionPercent.getsJiaModel()+" otaId:"+commissionPercent.getOtaId());
        }

    }
}
