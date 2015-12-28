package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.model.fc.FcRoomTypeFq;

import java.util.List;

import com.fanqielaile.toms.model.fc.FcRoomTypeFq;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/22
 * @version: v1.0.0
 */
public class FcRoomTypeFqDto extends FcRoomTypeFq {

    private  List<FcRoomTypeFq> fcRoomTypeFqs;
    private OtaRatePlanDto fcRatePlanDto;


    public OtaRatePlanDto getFcRatePlanDto() {
        return fcRatePlanDto;
    }

    public void setFcRatePlanDto(OtaRatePlanDto fcRatePlanDto) {
        this.fcRatePlanDto = fcRatePlanDto;
    }

    public List<FcRoomTypeFq> getFcRoomTypeFqs() {
        return fcRoomTypeFqs;
    }

    public void setFcRoomTypeFqs(List<FcRoomTypeFq> fcRoomTypeFqs) {
        this.fcRoomTypeFqs = fcRoomTypeFqs;
    }

    public FcRoomTypeFqDto() {
    }

    public FcRoomTypeFqDto(List<FcRoomTypeFq> fcRoomTypeFqs) {
        this.fcRoomTypeFqs = fcRoomTypeFqs;
    }

    public FcRoomTypeFqDto(String innId, String companyId, String otaInfoId) {
        super(innId, companyId, otaInfoId);
    }
    public FcRoomTypeFqDto(Integer sj,String innId, String companyId, String otaInfoId) {
        super(sj,innId, companyId, otaInfoId);
    }
    public FcRoomTypeFqDto(Integer sj,String innId,String fqRoomTypeId ,String companyId,String otaInfoId) {
        super( sj,  innId, fqRoomTypeId,  companyId,  otaInfoId);

    }
}
