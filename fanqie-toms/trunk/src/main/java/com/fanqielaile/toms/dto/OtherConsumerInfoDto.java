package com.fanqielaile.toms.dto;

import com.fanqielaile.toms.model.OtherConsumerInfo;

import java.util.List;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/3/29
 * @version: v1.0.0
 */
public class OtherConsumerInfoDto extends OtherConsumerInfo {

    private List<OtherConsumerInfo> otherList;

    public List<OtherConsumerInfo> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<OtherConsumerInfo> otherList) {
        this.otherList = otherList;
    }
}
