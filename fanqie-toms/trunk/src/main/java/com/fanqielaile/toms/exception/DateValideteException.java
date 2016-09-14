package com.fanqielaile.toms.exception;

/**
 * Created by jame on 2016/3/30.
 */
public class DateValideteException extends RuntimeException {
    private static String MSG = "不是有效的日期格式(年/月/日)：";
    private static String MSG1 = "结束日期应该不早于开始日期：";

    public DateValideteException() {
    }

    public DateValideteException(String e) {
        super(MSG + e);
    }

    public DateValideteException(String date1, String date2) {
        super(MSG1 + "date1=" + date1 + ",date2=" + date2);
    }
}
