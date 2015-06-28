package com.fanqielaile.toms.service.impl;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dao.IOtaInnOtaDao;
import com.fanqielaile.toms.service.ICommissionService;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/6/26
 * @version: v1.0.0
 */
public class CommissionService implements ICommissionService {

    @Resource
    private IOtaInnOtaDao otaInnOtaDao;
    @Override
    public void updateCommission(TBParam tbParam) {
        List<String> list = otaInnOtaDao.findOtaInnOtaIdsByCompanyCode(tbParam.getCompanyCode());
        if (!CollectionUtils.isEmpty(list) && tbParam.getCommissionPercent()!=null){
            StringBuilder listIds = new StringBuilder();
            for (String v:list){
                listIds.append("\'").append(v).append("\'").append(",");
            }
            listIds.deleteCharAt(listIds.length()-1);
            otaInnOtaDao.updateOtaInnOtaCommission(listIds.toString(),tbParam.getCommissionPercent(),tbParam.getCommissionType());
        }
    }
}
