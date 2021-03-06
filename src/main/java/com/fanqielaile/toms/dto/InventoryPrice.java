package com.fanqielaile.toms.dto;

import java.util.List;

/**
 * DESC : 酒店库存
 * @author : 番茄木-ZLin
 * @data : 2015/6/24
 * @version: v1.0.0
 */
public class InventoryPrice<T extends InventoryRate> {
    private boolean use_room_inventory = false;
    private List<T> inventory_price;

    public boolean isUse_room_inventory() {
        return use_room_inventory;
    }

    public void setUse_room_inventory(boolean use_room_inventory) {
        this.use_room_inventory = use_room_inventory;
    }

    public List<T> getInventory_price() {
        return inventory_price;
    }

    public void setInventory_price(List<T> inventory_price) {
        this.inventory_price = inventory_price;
    }
}
