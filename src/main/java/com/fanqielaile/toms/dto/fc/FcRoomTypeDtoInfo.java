package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.FacilitiesVo;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.enums.BedType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/9/30
 * @version: v1.0.0
 */
public class FcRoomTypeDtoInfo extends RoomTypeInfo {

    private BedType bedType;

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public Map toMap() {
       /* String json = JacksonUtil.objectToString(this);
        Map<String, ExportInnRoomType> typeMap = JacksonUtil.json2map(json, ExportInnRoomType.class);*/
        Map map = new HashMap();
        map.put("innId", getInnId());
        map.put("roomArea", getRoomArea());
        map.put("roomTypeName",getRoomTypeName());
        map.put("roomTypeId",getRoomTypeId());
        map.put("floorNum",getFloorNum());
        map.put("roomInfo", getRoomInfo());
        map.put("bedLen", getBedLen());
        map.put("bedWid", getBedWid());
        map.put("bedType",getBedType().getValue());
        List<FacilitiesVo> facilitiesMap = getFacilitiesMap();
        StringBuilder sb = new StringBuilder();
        if (facilitiesMap!=null){
            for (FacilitiesVo v:facilitiesMap){
                sb.append(v.getName()).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        map.put("facilities", sb.toString());
        return map;
    }
}
