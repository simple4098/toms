package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/15.
 */
public interface CompanyDao {
    /**
     * 新增公司信息
     *
     * @param company
     * @return
     */
    int insertCompany(Company company);

    /**
     * 根据ID查询公司信息
     *
     * @param Id
     * @return
     */
    Company selectCompanyById(String Id);

    /**
     * 新增公司的权限
     *
     * @param company
     */
    void insertCompanyPermission(Company company);

    /**
     * 根据公司姓名或者唯一码查询公司
     *
     * @param company
     * @return
     */
    List<Company> selectCompanyByCompany(Company company);

    /**
     * 根据公司唯一码查询公司
     *
     * @param companyCode
     * @return
     */
    Company selectCompanyByCompanyCode(@Param("companyCode") String companyCode);
}
