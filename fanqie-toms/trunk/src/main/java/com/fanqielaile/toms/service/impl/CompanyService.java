package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.service.ICompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdayin on 2015/6/2.
 */
@Service
public class CompanyService implements ICompanyService {
    @Resource
    private CompanyDao companyDao;

    @Override
    public List<Company> findCompanyByCompany(Company company) {
        return this.companyDao.selectCompanyByCompany(company);
    }

    @Override
    public Company findCompanyByCompanyCode(String companyCode) {
        return this.companyDao.selectCompanyByCompanyCode(companyCode);
    }

    @Override
    public Company findCompanyByid(String id) {
        return this.companyDao.selectCompanyById(id);
    }
}
