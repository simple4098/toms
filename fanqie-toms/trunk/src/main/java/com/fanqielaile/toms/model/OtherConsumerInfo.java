package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DESC : 其他消费domain
 * @author : 番茄木-ZLin
 * @data : 2016/3/25
 * @version: v1.0.0
 */
public class OtherConsumerInfo extends Domain{
    private String companyId;
    //配置id
    private String consumerFunId;
    //级别
    private int level;
    //项目名称
    private String consumerProjectName;
    //价格名称
    private String priceName;
    //价格
    private BigDecimal price;
    //是否必填
    private Boolean status;
    //父id
    private String parentId;

    public OtherConsumerInfo() {
    }

    public OtherConsumerInfo( String companyId, String consumerFunId,
                             int level, String consumerProjectName,
                             String priceName, BigDecimal price, Boolean status, String parentId) {

        this.companyId = companyId;
        this.consumerFunId = consumerFunId;
        this.level = level;
        this.consumerProjectName = consumerProjectName;
        this.priceName = priceName;
        this.price = price;
        this.status = status;
        this.parentId = parentId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getConsumerFunId() {
        return consumerFunId;
    }

    public void setConsumerFunId(String consumerFunId) {
        this.consumerFunId = consumerFunId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getConsumerProjectName() {
        return consumerProjectName;
    }

    public void setConsumerProjectName(String consumerProjectName) {
        this.consumerProjectName = consumerProjectName;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
