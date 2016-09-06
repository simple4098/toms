package com.fanqielaile.toms.model.minsu;

/**
 * Created by LZQ on 2016/9/2.
 */
public class Owner {
    private String id;
    private String nickName;
    private String avatarUrl;
    private String tel;
    private String email;
    private String gender;
    private Integer avgConfirm;
    private float responseRate;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAvgConfirm() {
        return avgConfirm;
    }

    public void setAvgConfirm(Integer avgConfirm) {
        this.avgConfirm = avgConfirm;
    }

    public float getResponseRate() {
        return responseRate;
    }

    public void setResponseRate(float responseRate) {
        this.responseRate = responseRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
