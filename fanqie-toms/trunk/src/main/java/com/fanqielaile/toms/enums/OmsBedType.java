package com.fanqielaile.toms.enums;

public enum OmsBedType {
	BigBed("1","大床",2),
	DoubleBed("2","双床",2),
	BigOrDoubleBed("3","大/双床",2),
	SignleBed("4","单人床",1),
	CircleBed("5","圆床",2),
	ThreeBed("6","三张床",3),
	Bed("7","床位",1),
	Tatami("8","榻榻米",2),
	Other("9","其他",2);
	private String bedTypeName;
	private String bedTypeValue;
	private int number;
	private OmsBedType(String bedTypeName, String bedTypeValue,int number) {
		this.bedTypeName = bedTypeName;
		this.bedTypeValue = bedTypeValue;
		this.number = number;
	}
	public String getBedTypeName() {
		return bedTypeName;
	}
	public void setBedTypeName(String bedTypeName) {
		this.bedTypeName = bedTypeName;
	}
	public String getBedTypeValue() {
		return bedTypeValue;
	}
	public void setBedTypeValue(String bedTypeValue) {
		this.bedTypeValue = bedTypeValue;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}

