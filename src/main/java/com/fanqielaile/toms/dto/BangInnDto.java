package com.fanqielaile.toms.dto;

import com.fanqie.core.dto.PriceModel;
import com.fanqie.core.dto.TBParam;
import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.enums.InnStatus;
import com.fanqielaile.toms.enums.OtaType;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Role;
import com.fanqielaile.toms.model.UserInfo;
import org.apache.commons.lang3.StringUtils;

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

    private String otaInnOtaId;
    //数据权限
    private Integer dataPermission;
    //所属公司ID
    private String companyId;
    //渠道id
    private String otaInfoId;
    //管理员ID
    //private String userId;
    //关键字
    private String keywords;
    private InnStatus innStatus;
    //要排除企业id集合
    private List<String> companyIdList;
    //上架模式
    private String sJiaModel;
    //所在去哪儿城市的id
    private String qunarCityId;
    //地址
    private String address;
    //去哪儿城市code
    private String qunarCityCode;

    public String getQunarCityCode() {
        return qunarCityCode;
    }

    public void setQunarCityCode(String qunarCityCode) {
        this.qunarCityCode = qunarCityCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQunarCityId() {
        return qunarCityId;
    }

    public void setQunarCityId(String qunarCityId) {
        this.qunarCityId = qunarCityId;
    }

    public List<String> getCompanyIdList() {
        return companyIdList;
    }

    public BangInnDto() {
    }

    public BangInnDto(List<String> companyIdList ,Integer innId) {
        this.companyIdList = companyIdList;
        setInnId(innId);
    }

    public String getsJiaModel() {
        return sJiaModel;
    }

    public void setsJiaModel(String sJiaModel) {
        this.sJiaModel = sJiaModel;
    }

    public void setCompanyIdList(List<String> companyIdList) {
        this.companyIdList = companyIdList;
    }

    public InnStatus getInnStatus() {
        return innStatus;
    }

    public void setInnStatus(InnStatus innStatus) {
        this.innStatus = innStatus;
    }

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

    public String getOtaInnOtaId() {
        return otaInnOtaId;
    }

    public void setOtaInnOtaId(String otaInnOtaId) {
        this.otaInnOtaId = otaInnOtaId;
    }

    public Integer getDataPermission() {
        return dataPermission;
    }

    public void setDataPermission(Integer dataPermission) {
        this.dataPermission = dataPermission;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOtaInfoId() {
        return otaInfoId;
    }

    public void setOtaInfoId(String otaInfoId) {
        this.otaInfoId = otaInfoId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public static BangInnDto toDto(String companyId,TBParam tbParam,InnDto omsInnDto){
        List<PriceModel> priceModelArray = tbParam.getPriceModelArray();
        BangInnDto bangInnDto = new BangInnDto();
        bangInnDto.setId(bangInnDto.getUuid());
        bangInnDto.setCompanyId(companyId);
        bangInnDto.setMobile(omsInnDto.getFrontPhone());
        bangInnDto.setSj(tbParam.isSj()?1:0);
        bangInnDto.setInnId(Integer.valueOf(tbParam.getInnId()));
        bangInnDto.setAddress(omsInnDto.getAddr());
        /*bangInnDto.setAccountId(Integer.valueOf(tbParam.getAccountId()));*/
        /*bangInnDto.setOtaWgId(otaInnOtaId);*/
        /*bangInnDto.setInnName(omsInnDto.getInnName());*/
        bangInnDto.setInnName(omsInnDto.getBrandName());
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

    public static void toUpdateDto(BangInn bangInnDto, TBParam tbParam, InnDto omsInnDto) {
        toUpdateDiDto(bangInnDto,tbParam,omsInnDto);
        bangInnDto.setSj(tbParam.isSj()?1:0);

    }
    public static void toUpdateDiDto(BangInn bangInnDto, TBParam tbParam, InnDto omsInnDto) {
        List<PriceModel> priceModelArray = tbParam.getPriceModelArray();
      /*  bangInnDto.setOtaWgId(otaWgId);*/
        bangInnDto.setMobile(omsInnDto.getFrontPhone());
        bangInnDto.setInnName(omsInnDto.getBrandName());
        if (priceModelArray!=null){
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

    public static BangInnDto userInfoToBangInnDto(UserInfo userInfo,BangInnDto bangInnDto, List<OtaInfoRefDto> infoRefDtoList){
        bangInnDto.setCompanyId(userInfo.getCompanyId());
        bangInnDto.setDataPermission(userInfo.getDataPermission());
        bangInnDto.setUserId(userInfo.getUserId());
        if (StringUtils.isEmpty(bangInnDto.getOtaInfoId())){
            if (infoRefDtoList!=null){
                for (OtaInfoRefDto infoRefDto:infoRefDtoList){
                    if (OtaType.TB.equals(infoRefDto.getOtaType())){
                        bangInnDto.setOtaInfoId(infoRefDto.getOtaInfoId());
                    }
                }
            }
        }
        return  bangInnDto;
    }
}
