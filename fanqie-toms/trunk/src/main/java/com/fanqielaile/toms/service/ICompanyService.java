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

    /**
     * 修改公司信息
     * @param company 公司基本信息
     */
    void modifyCompanyInfo(Company company)throws  Exception;

    void removeCompany(String companyId);

    /**
     * 创建公司
     *
     * @param company
     * @param permissionIds
     */
    void addCompany(Company company, String permissionIds);

    /**
     * 更新公司权限
     *
     * @param company
     * @param permissionIds
     */
    void modifyCompanyPermission(Company company, String permissionIds);
}
