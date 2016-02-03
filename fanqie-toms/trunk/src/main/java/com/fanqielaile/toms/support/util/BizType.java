package com.fanqielaile.toms.support.util;

public class BizType {

    private Integer typeId;
    private String typeName;
    private BizType parent;

    public BizType() {
    }

    public BizType(Integer typeId, String typeName, BizType parent) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.parent = parent;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BizType getParent() {
        return parent;
    }

    public void setParent(BizType parent) {
        this.parent = parent;
    }
}