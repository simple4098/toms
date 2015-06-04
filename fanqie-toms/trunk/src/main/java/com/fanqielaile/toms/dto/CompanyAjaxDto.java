package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.Company;
import org.apache.commons.lang3.StringUtils;

/**
 * 提供给OMS接口返回数据封装
 * Created by wangdayin on 2015/6/3.
 */
public class CompanyAjaxDto {
    private String companyName;
    private int otaId;
    private String companyCode;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getOtaId() {
        return otaId;
    }

    public void setOtaId(int otaId) {
        this.otaId = otaId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public static CompanyAjaxDto toAjaxBangInnDto(Company company) {
        CompanyAjaxDto companyAjaxDto = new CompanyAjaxDto();
        if (StringUtils.isNotEmpty(company.getCompanyName())) {
            companyAjaxDto.setCompanyName(company.getCompanyName());
        }
        if (StringUtils.isNotEmpty(company.getCompanyCode())) {
            companyAjaxDto.setCompanyCode(company.getCompanyCode());
        }
        if (StringUtils.isNotEmpty(company.getOtaId() + "")) {
            companyAjaxDto.setOtaId(company.getOtaId());
        }
        return companyAjaxDto;
    }
}
