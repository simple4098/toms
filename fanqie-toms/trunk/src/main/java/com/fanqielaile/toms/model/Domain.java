package com.fanqielaile.toms.model;

import java.util.Date;

/**
 * Created by wangdayin on 2015/5/11.
 */
public class Domain {
    //ID
    private String id;
    //创建时间
    private Date createdDate;
    //更新时间
    private Date updatedDate;
    //修改者ID
    private String modifierId;
    //版本号
    private float version;
    //创建者ID
    private String creatorId;
    //是否删除
    private boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Domain() {
    }

    public Domain(String id, Date createdDate, Date updatedDate, String modifierId, float version, String creatorId, boolean deleted) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.modifierId = modifierId;
        this.version = version;
        this.creatorId = creatorId;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "id='" + id + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", modifierId='" + modifierId + '\'' +
                ", version=" + version +
                ", creatorId='" + creatorId + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
