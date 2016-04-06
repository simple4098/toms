package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.OtherConsumerInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/3/29
 * @version: v1.0.0
 */
public class OtherConsumerInfoDto  {


    private String companyId;
    private String id;
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
    private int deleted;
    private List<OtherConsumerInfoDto> otherList;

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OtherConsumerInfoDto> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<OtherConsumerInfoDto> otherList) {
        this.otherList = otherList;
    }
}
