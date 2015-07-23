package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.enums.BangType;

import java.util.Date;
import java.util.List;

/**
 * 绑定客栈信息
 * Created by wangdayin on 2015/5/15.
 */
public class BangInn extends Domain {
    //所属公司ID
    private String companyId;
    //绑定时间
    private Date bangDate;
    //失效时间
    private Date loseDate;
    //发起时间
    private Date startDate;
    //客栈ID
    private Integer innId;
    //客栈名称
    private String innName;
    //编号
    private String code;
    //标签ID
    private String innLabelId;
    //客栈电话
    private String mobile;
    //客栈账户
    private String innCode;
    //管理员ID
    private String userId;
    //绑定类型（状态）
    private BangType type;
    //标签名称
    private String innLabelName;
    //account id （卖价）
    private Integer accountId;
    //account id （低价）
    private Integer accountIdDi;
    //绑定关联id
    private String otaWgId;

    private List<BangInn> bangInnList;
    //绑定客栈图片信息
    private InnDto innDto;
    //是否上架 0 下架  1 上架
    private int sj;

    public int getSj() {
        return sj;
    }

    public void setSj(int sj) {
        this.sj = sj;
    }

    public InnDto getInnDto() {
        return innDto;
    }

    public void setInnDto(InnDto innDto) {
        this.innDto = innDto;
    }

    public Integer getAccountIdDi() {
        return accountIdDi;
    }

    public void setAccountIdDi(Integer accountIdDi) {
        this.accountIdDi = accountIdDi;
    }

    public String getOtaWgId() {
        return otaWgId;
    }

    public void setOtaWgId(String otaWgId) {
        this.otaWgId = otaWgId;
    }

    public String getInnLabelName() {
        return innLabelName;
    }

    public void setInnLabelName(String innLabelName) {
        this.innLabelName = innLabelName;
    }

    public List<BangInn> getBangInnList() {
        return bangInnList;
    }

    public void setBangInnList(List<BangInn> bangInnList) {
        this.bangInnList = bangInnList;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getBangDate() {
        return bangDate;
    }

    public void setBangDate(Date bangDate) {
        this.bangDate = bangDate;
    }

    public Date getLoseDate() {
        return loseDate;
    }

    public void setLoseDate(Date loseDate) {
        this.loseDate = loseDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public String getInnName() {
        return innName;
    }

    public void setInnName(String innName) {
        this.innName = innName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInnLabelId() {
        return innLabelId;
    }

    public void setInnLabelId(String innLabelId) {
        this.innLabelId = innLabelId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInnCode() {
        return innCode;
    }

    public void setInnCode(String innCode) {
        this.innCode = innCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BangType getType() {
        return type;
    }

    public void setType(BangType type) {
        this.type = type;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
