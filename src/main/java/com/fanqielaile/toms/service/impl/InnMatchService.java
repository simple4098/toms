package com.fanqielaile.toms.service.impl;

import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqie.util.TomsConstants;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.service.IInnMatchService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/15
 * @version: v1.0.0
 */
@Service
public class InnMatchService implements IInnMatchService {

    @Resource
    private CompanyDao companyDao;

    @Override
    public InnDto obtOmsInn(BangInn bangInn){
        Company company = companyDao.selectCompanyById(bangInn.getCompanyId());
        String innInfoUrl = DcUtil.omsUrl(company.getOtaId(), company.getUserAccount(), company.getUserPassword(), String.valueOf(bangInn.getAccountId()), CommonApi.INN_INFO);
        String gets = null;
        try {
            gets = HttpClientUtil.httpGets(innInfoUrl, null);
            JSONObject jsonInn = JSONObject.fromObject(gets);
            if (TomsConstants.SUCCESS.equals(jsonInn.get("status").toString()) && jsonInn.get("list")!=null) {
                return JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
            }
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

        return null;
    }
}
