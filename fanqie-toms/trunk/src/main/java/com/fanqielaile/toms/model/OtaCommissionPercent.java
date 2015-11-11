package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.OperateType;

import java.util.Date;

/**
 * DESC : 渠道佣金
 * @author : 番茄木-ZLin
 * @data : 2015/11/6
 * @version: v1.0.0
 */
public class OtaCommissionPercent extends Domain {
    private String  companyId;
    private Integer otaId;
    private Double commissionPercent;
    private String  sJiaModel;
    private OperateType operateType;

    public OperateType getOperateType() {
        return operateType;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    public OtaCommissionPercent() {
    }

    public OtaCommissionPercent(Integer otaId, String companyId, String sJiaModel) {
        this.otaId = otaId;
        this.companyId = companyId;
        this.sJiaModel = sJiaModel;
    }
    public OtaCommissionPercent(Integer otaId, String companyId, String sJiaModel,OperateType operateType) {
        this.otaId = otaId;
        this.companyId = companyId;
        this.sJiaModel = sJiaModel;
        this.operateType = operateType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getOtaId() {
        return otaId;
    }

    public void setOtaId(Integer otaId) {
        this.otaId = otaId;
    }

    public Double getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(Double commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getsJiaModel() {
        return sJiaModel;
    }

    public void setsJiaModel(String sJiaModel) {
        this.sJiaModel = sJiaModel;
    }
}
