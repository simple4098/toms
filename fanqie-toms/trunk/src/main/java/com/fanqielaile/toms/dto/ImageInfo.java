package com.fanqielaile.toms.dto;

import com.fanqie.core.Domain;

import java.util.Date;

/**
 * Created by Administrator on 2015/11/3.
 */
public class ImageInfo extends Domain {
    private String info;
    private Date createDate;

    public ImageInfo() {
    }

    public ImageInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
