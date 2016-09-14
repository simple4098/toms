package com.fanqielaile.toms.vo.ctrip.homestay;

/**
 * Create by jame
 * Date: 2016/9/6 10:46
 * Version: 1.0
 * Description: 阐述
 * guests		object[]
 * name	string
 * idCode	string
 * idType	int
 */
public class SubmitOrderGuestsVo {
    private String name;
    private String idCode;
    private int idType;

    public SubmitOrderGuestsVo() {
        super();
    }

    public SubmitOrderGuestsVo(String name, String idCode, int idType) {
        this.name = name;
        this.idCode = idCode;
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }
}
