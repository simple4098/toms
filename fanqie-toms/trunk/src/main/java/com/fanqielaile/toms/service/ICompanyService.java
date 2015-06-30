package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.Company;

import java.util.List;

/**
 * Created by wangdayin on 2015/6/2.
 */
public interface ICompanyService {
    /**
     * 根据公司名字和唯一码查询公司信息
     *
     * @param company
     * @return
     */
    List<Company> findCompanyByCompany(Company company);

    /**
     * 根据公司code查询公司信息
     *
     * @param companyCode
     * @return
     */
    Company findCompanyByCompanyCode(String companyCode);

    /**
     * 根据公司id查询
     *
     * @param id
     * @return
     */
    Company findCompanyByid(String id);
}
