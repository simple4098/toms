package com.fanqielaile.toms.dto;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.model.OtaRoomPrice;

import java.util.Date;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/8/20
 * @version: v1.0.0
 */
public class OtaRoomPriceDto extends OtaRoomPrice {

    private String startDateStr;
    private String endDateStr;

    public OtaRoomPriceDto(String companyId,Integer roomTypeId,String otaInfoId) {
        setCompanyId(companyId);
        setOtaInfoId(otaInfoId);
        setRoomTypeId(roomTypeId);
    }

    public OtaRoomPriceDto() {
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {

        setStartDate(DateUtil.parseDate(startDateStr));
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        setEndDate(DateUtil.parseDate(endDateStr));
        this.endDateStr = endDateStr;
    }
}
