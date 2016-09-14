package com.fanqielaile.toms.util;

public interface HomeStayConstants {
	int minBookingDays = 1;
	int maxBookingDays = 999;
	String checkOutTime = "14:00";
	String checkInTime = "23:00";
	String receptionHours = "23:00";
	int depositAmount = 0;
	String houseModel = "标准间";
	String avatarUrl ="XXX.PNG";
	String depositType = "预付";
	
	int invoiceType = 0;
	int hasLandlord = 0;
	int refundDays = 1;
	int instantBook = 1;
	/**
	 * 线上支付比例
	 */
	int OnlinePayRatio = 100;
	String bedType = "床位";
	
	int DEFAULE_PAGE_INDEX = 0;
	int DEFAULE_PAGE_SIZE = 50;
	Integer BOOKING_STATUS_YES = 1;
	Integer BOOKING_STATUS_NO = 0;
}

