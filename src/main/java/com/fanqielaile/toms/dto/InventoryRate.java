package com.fanqielaile.toms.dto;

/**
 * DESC : 房型库存
 * @author : 番茄木-ZLin
 * @data : 2015/6/24
 * @version: v1.0.0
 */
public class InventoryRate extends Inventory {
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
