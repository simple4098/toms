package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dao.IOtaInnOtaDao;
import com.fanqielaile.toms.service.ICommissionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   /* @Resource
    private BusinLogClient businLogClient;*/

    @Override
   /* @Log(descr ="渠道佣金更新")*/
    public void updateCommission(TBParam tbParam) {
        List<String> list = otaInnOtaDao.findOtaInnOtaIdsByCompanyCode(tbParam.getCompanyCode());
        if (!CollectionUtils.isEmpty(list) && tbParam.getCommissionPercent()!=null){
           otaInnOtaDao.updateOtaInnOtaCommission(list,tbParam.getCommissionPercent(),tbParam.getCommissionType());
            Map<String,Object> param = new HashMap<String, Object>();
            param.put("companyCode",tbParam.getCompanyCode());
            param.put("commissionPercent",tbParam.getCommissionPercent());
            /*String event = JacksonUtil.obj2json(param);
            try {
                BusinLog businLog = new BusinLog();
                businLog.setDescr("渠道佣金更新");
                businLog.setEvent(event);
                businLogClient.save(businLog);
            } catch (Exception e) {
                log.error(e.getMessage());
            }*/
        }

    }
}
