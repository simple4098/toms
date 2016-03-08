package com.fanqielaile.toms.dto;

/**
 * DESC : 去啊支持多张图片。 图片对象
 * @author : 番茄木-ZLin
 * @data : 2016/1/20
 * @version: v1.0.0
 */
public class TbImgDto {
    private String url;
    private boolean ismain;

    public TbImgDto(String url, boolean ismain) {
        this.url = url;
        this.ismain = ismain;
    }

    public TbImgDto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isIsmain() {
        return ismain;
    }

    public void setIsmain(boolean ismain) {
        this.ismain = ismain;
    }
}
