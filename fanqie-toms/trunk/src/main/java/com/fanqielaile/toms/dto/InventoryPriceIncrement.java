package com.fanqielaile.toms.dto;

/**
 * DESC : 房型库存
 * @author : 番茄木-ZLin
 * @data : 2015/6/24
 * @version: v1.0.0
 */
public class InventoryPriceIncrement extends Inventory {
    private Integer price;
    private Integer status;

    public InventoryPriceIncrement(Integer price, Integer status) {
        this.price = price;
        this.status = status;
    }

    public InventoryPriceIncrement() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
