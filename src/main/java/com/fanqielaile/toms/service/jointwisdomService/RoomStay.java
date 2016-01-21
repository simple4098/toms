package com.fanqielaile.toms.service.jointwisdomService;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/11.
 */
@XmlRootElement(name = "RoomStay", namespace = "http://www.opentravel.org/OTA/2003/05")
public class RoomStay {
    private List<RoomType> roomTypes;//房型信息
    private List<RatePlan> ratePlans;//价格计划信息
    private List<RoomRate> roomRates;
    private List<GuestCount> guestCounts;
    private TimeSpan timeSpan;
    private BasicPropertyInfo basicPropertyInfo;
    private Guarantee guarantee;
    private Total total;
    private String resGuestRPHs;
    private List<SpecialRequest> specialRequests;

    @XmlElement(name = "SpecialRequest", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "SpecialRequests")
    public List<SpecialRequest> getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(List<SpecialRequest> specialRequests) {
        this.specialRequests = specialRequests;
    }

    @XmlElement(name = "ResGuestRPHs", namespace = "http://www.opentravel.org/OTA/2003/05")
    public String getResGuestRPHs() {
        return resGuestRPHs;
    }

    public void setResGuestRPHs(String resGuestRPHs) {
        this.resGuestRPHs = resGuestRPHs;
    }

    @XmlElement(name = "Total", namespace = "http://www.opentravel.org/OTA/2003/05")
    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    @XmlElement(name = "Guarantee", namespace = "http://www.opentravel.org/OTA/2003/05")
    public Guarantee getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
    }

    @XmlElement(name = "BasicPropertyInfo")
    public BasicPropertyInfo getBasicPropertyInfo() {
        return basicPropertyInfo;
    }

    public void setBasicPropertyInfo(BasicPropertyInfo basicPropertyInfo) {
        this.basicPropertyInfo = basicPropertyInfo;
    }

    @XmlElement(name = "TimeSpan")
    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        this.timeSpan = timeSpan;
    }

    @XmlElement(name = "GuestCount")
    @XmlElementWrapper(name = "GuestCounts")
    public List<GuestCount> getGuestCounts() {
        return guestCounts;
    }

    public void setGuestCounts(List<GuestCount> guestCounts) {
        this.guestCounts = guestCounts;
    }

    @XmlElement(name = "RoomRate")
    @XmlElementWrapper(name = "RoomRates")
    public List<RoomRate> getRoomRates() {
        return roomRates;
    }

    public void setRoomRates(List<RoomRate> roomRates) {
        this.roomRates = roomRates;
    }

    @XmlElement(name = "RatePlan")
    @XmlElementWrapper(name = "RatePlans")
    public List<RatePlan> getRatePlans() {
        return ratePlans;
    }

    public void setRatePlans(List<RatePlan> ratePlans) {
        this.ratePlans = ratePlans;
    }

    @XmlElement(name = "RoomType")
    @XmlElementWrapper(name = "RoomTypes")
    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }
}
