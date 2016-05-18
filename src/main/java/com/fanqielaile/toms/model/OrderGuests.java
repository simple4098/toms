package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

/**
 * Created by wangdayin on 2015/6/19.
 * 订单入住人信息
 */
public class OrderGuests extends Domain {
	//id
	private String id;
	//入住人姓名
    private String name;
    //房间序号
    private int roomPos;
    //订单ID
    private String orderId;
    //国籍
    private String nationality;
    //-入住人英文名
    private String firstName;
    //入住人英文名姓
    private String lastName;
    //订单客人所属省份
    private String guestProvince;
    //订单客人所属城市
    private String guestCity;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomPos() {
        return roomPos;
    }

    public void setRoomPos(int roomPos) {
        this.roomPos = roomPos;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

	public String getGuestProvince() {
		return guestProvince;
	}

	public void setGuestProvince(String guestProvince) {
		this.guestProvince = guestProvince;
	}

	public String getGuestCity() {
		return guestCity;
	}

	public void setGuestCity(String guestCity) {
		this.guestCity = guestCity;
	}
}
