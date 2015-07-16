package com.fanqielaile.toms.dto;

import com.fanqie.core.dto.PriceModel;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.model.BangInn;

import java.util.Date;
import java.util.List;

/**
 * Created by wangdayin on 2015/5/29.
 */
public class BangInnDto extends BangInn {
    //所属管理员
    private String userName;
    //客栈标签
    private String labelName;
    //绑定时间格式化
    private String bangDataFormat;
    //公司名称
    private String companyName;
    //公司唯一识别码
    private String companyCode;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBangDataFormat() {
        if (getBangDate() != null) {
            return DateUtil.formatDateToString(getBangDate(), "yyyy-MM-dd");
        }
        return bangDataFormat;
    }

    public void setBangDataFormat(String bangDataFormat) {
        this.bangDataFormat = bangDataFormat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public static BangInnDto toDto(String companyId,TBParam tbParam,String otaInnOtaId,InnDto omsInnDto){
        List<PriceModel> priceModelArray = tbParam.getPriceModelArray();
        BangInnDto bangInnDto = new BangInnDto();
        bangInnDto.setCompanyId(companyId);
        bangInnDto.setInnId(Integer.valueOf(tbParam.getInnId()));
        bangInnDto.setAccountId(Integer.valueOf(tbParam.getAccountId()));
        bangInnDto.setOtaWgId(otaInnOtaId);
        bangInnDto.setInnName(omsInnDto.getInnName());
        bangInnDto.setBangDate(new Date());
        for (PriceModel p:priceModelArray){
            if(p.getPattern().equals("DI")){
                bangInnDto.setAccountIdDi(Integer.valueOf(p.getAccountId()));
            }
            if(p.getPattern().equals("MAI")){
                bangInnDto.setAccountId(Integer.valueOf(p.getAccountId()));
            }
        }
        return  bangInnDto;
    }

    public static void toUpdateDto(BangInn bangInnDto, TBParam tbParam, String uuid, InnDto omsInnDto) {
        List<PriceModel> priceModelArray = tbParam.getPriceModelArray();
        bangInnDto.setOtaWgId(uuid);
        bangInnDto.setMobile(omsInnDto.getFrontPhone());
        bangInnDto.setInnName(omsInnDto.getInnName());
        for (PriceModel p:priceModelArray){
            if(p.getPattern().equals("DI")){
                bangInnDto.setAccountIdDi(Integer.valueOf(p.getAccountId()));
            }
            if(p.getPattern().equals("MAI")){
                bangInnDto.setAccountId(Integer.valueOf(p.getAccountId()));
            }
        }
    }
}
