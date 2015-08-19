package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.OrderConfig;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/8/18
 * @version: v1.0.0
 */
public class OrderConfigDto extends OrderConfig {

    private String innName;
    private String labelName;
    private List<OrderConfig>  orderConfigList;
    private List<String>  value;
    //渠道名称
    private String otaInfo;

    public OrderConfigDto(String otaInfoId,int status, String otaInfo) {
        super.setStatus(status);
        super.setOtaInfoId(otaInfoId);
        this.otaInfo = otaInfo;
    }

    public List<OrderConfig> getOrderConfigList() {
        return orderConfigList;
    }

    public void setOrderConfigList(List<OrderConfig> orderConfigList) {
        this.orderConfigList = orderConfigList;
    }

    public String getInnName() {
        return innName;
    }

    public void setInnName(String innName) {
        this.innName = innName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public String getOtaInfo() {
        return otaInfo;
    }

    public void setOtaInfo(String otaInfo) {
        this.otaInfo = otaInfo;
    }
}
