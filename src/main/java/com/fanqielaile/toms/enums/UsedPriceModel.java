package com.fanqielaile.toms.enums;

public enum UsedPriceModel {
    MAI("卖价"),
    DI("底价"),
    MAI2DI("卖转底");

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    UsedPriceModel(String text) {
        this.text = text;
    }
}
