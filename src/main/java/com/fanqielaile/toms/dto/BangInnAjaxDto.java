package com.fanqielaile.toms.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by wangdayin on 2015/6/3.
 */
public class BangInnAjaxDto {
    private String companyName;
    private String bangDate;
    private String companyCode;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBangDate() {
        return bangDate;
    }

    public void setBangDate(String bangDate) {
        this.bangDate = bangDate;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public static BangInnAjaxDto toAjaxBangInnDto(BangInnDto bangInnDto) {
        BangInnAjaxDto bangInnAjaxDto = new BangInnAjaxDto();
        if (StringUtils.isNotEmpty(bangInnDto.getBangDataFormat())) {
            bangInnAjaxDto.setBangDate(bangInnDto.getBangDataFormat());
        }
        if (StringUtils.isNotEmpty(bangInnDto.getCompanyCode())) {
            bangInnAjaxDto.setCompanyCode(bangInnDto.getCompanyCode());
        }
        if (StringUtils.isNotEmpty(bangInnDto.getCompanyName())) {
            bangInnAjaxDto.setCompanyName(bangInnDto.getCompanyName());
        }
        return bangInnAjaxDto;
    }
}
