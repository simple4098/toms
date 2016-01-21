package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/11.
 * 价格与房型的对应关系
 */
@XmlRootElement(name = "RoomRate", namespace = "http://www.opentravel.org/OTA/2003/05")
public class RoomRate {
    private String roomTypeCode;//房型code
    private String ratePlanCode;//价格code
    private List<Rate> rates;//价格
    private Total total;
    private String numberOfUnits;

    @XmlAttribute(name = "NumberOfUnits")
    public String getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(String numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    @XmlElement(name = "Total")
    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    @XmlElement(name = "Rate")
    @XmlElementWrapper(name = "Rates")
    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @XmlAttribute(name = "RoomTypeCode")
    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    @XmlAttribute(name = "RatePlanCode")
    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }
}
