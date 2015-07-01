package com.fanqielaile.toms.dto;


import com.fanqie.core.dto.RoomSwitchCalStatus;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/7/1
 * @version: v1.0.0
 */
public class RoomSwitchCal {
    private  String date;
    private  int room_status;

    public RoomSwitchCal(String date) {
        this.date = date;
    }

    public RoomSwitchCal() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRoom_status() {
        return room_status;
    }

    public void setRoom_status(int room_status) {
        this.room_status = room_status;
    }
    public void setRoomSwitchCalStatus(RoomSwitchCalStatus switchCalStatus){
        if (switchCalStatus!=null){
            if (RoomSwitchCalStatus.SJ.equals(switchCalStatus)){
                setRoom_status(1);
            }if (RoomSwitchCalStatus.XJ.equals(switchCalStatus)){
                setRoom_status(2);
            }if (RoomSwitchCalStatus.DEL.equals(switchCalStatus)){
                setRoom_status(3);
            }
        }
    }
}
