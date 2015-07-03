package com.fanqielaile.toms.dto;

import com.fanqie.core.dto.RoomSwitchCalStatus;

import java.util.Date;

/**
 * DESC : 房型库存
 * @author : 番茄木-ZLin
 * @data : 2015/6/24
 * @version: v1.0.0
 */
public class Inventory {
    private String date;

    private int quota;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public void setRoomSwitchCalStatus(RoomSwitchCalStatus switchCalStatus){
        if (switchCalStatus!=null){
            //更新库存为0  当删除删除房源，下架房源
           if (RoomSwitchCalStatus.XJ.equals(switchCalStatus) || RoomSwitchCalStatus.DEL.equals(switchCalStatus)){
                setQuota(0);
            }
        }
    }
}
