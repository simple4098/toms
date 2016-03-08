package com.fanqielaile.toms.dto;

import java.util.List;

/**
 * DESC : 房型库存
 * @author : 番茄木-ZLin
 * @data : 2015/6/24
 * @version: v1.0.0
 */
public class InventoryRateIncrement{
    private String out_rid;

    List<Inventory> roomQuota;

    public InventoryRateIncrement(String out_rid) {
        this.out_rid = out_rid;

    }

    public InventoryRateIncrement() {
    }

    public String getOut_rid() {
        return out_rid;
    }

    public void setOut_rid(String out_rid) {
        this.out_rid = out_rid;
    }



    public List<Inventory> getRoomQuota() {
        return roomQuota;
    }

    public void setRoomQuota(List<Inventory> roomQuota) {
        this.roomQuota = roomQuota;
    }
}
