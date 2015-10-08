package com.fanqielaile.toms.dto.fc;

import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dto.OmsImg;

import java.util.HashMap;
import java.util.Map;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/9/30
 * @version: v1.0.0
 */
public class FcInnImg extends OmsImg {

    private Integer innId;

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public Map toMap() {
       /* String json = JacksonUtil.objectToString(this);
        Map<String, ExportInnRoomType> typeMap = JacksonUtil.json2map(json, ExportInnRoomType.class);*/
        Map map = new HashMap();
        map.put("roomTypeId", getRoomTypeId());
        map.put("innId", getInnId());
        map.put("imgUrl", CommonApi.getImgUrl()+getImgUrl());
        map.put("imgName", getImgName());
        return map;
    }
}
