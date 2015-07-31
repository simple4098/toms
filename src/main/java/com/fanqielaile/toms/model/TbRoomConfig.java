package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;
import com.fanqielaile.toms.enums.ObjType;

/**
 * DESC : 淘宝配置跟房型关联表
 * @author : 番茄木-ZLin
 * @data : 2015/7/30
 * @version: v1.0.0
 */
public class TbRoomConfig extends Domain {
    //公司id
    private String companyId;
    //客栈id
    private Integer innId;
    //房型id
    private Integer roomTypeId;
    //关联类型
    private ObjType objType;
    //某一个配置id
    private String objId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public ObjType getObjType() {
        return objType;
    }

    public void setObjType(ObjType objType) {
        this.objType = objType;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
}
