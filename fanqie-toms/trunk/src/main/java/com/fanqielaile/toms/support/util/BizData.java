package com.fanqielaile.toms.support.util;

import com.fanqie.util.DateUtil;
import com.fanqielaile.toms.enums.ChannelSource;
import com.fanqielaile.toms.enums.LogDec;
import com.fanqielaile.toms.enums.OtaType;

import java.util.Date;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2016/1/28
 * @version: v1.0.0
 */
public class BizData {
    //日志描述
    private LogDec logDec;
    //操作时间
    private String operateTime = DateUtil.format(new Date(),DateUtil.FORMAT_DATE_STR_SECOND);
    //操作者
    private String userName;
    //操作内容
    private String content;
    //客栈id
    private Integer innId;
    //房型id
    private Integer roomTypeId;
    private String otaType;

    public BizData(LogDec logDec, String userName, String content, Integer innId, Integer roomTypeId, OtaType otaType) {
        this.logDec = logDec;
        this.userName = userName;
        this.content = content;
        this.innId = innId;
        this.roomTypeId = roomTypeId;
        this.otaType = otaType.name();
    }
    public BizData(LogDec logDec, String userName, String content, Integer innId, Integer roomTypeId, ChannelSource channelSource) {
        this.logDec = logDec;
        this.userName = userName;
        this.content = content;
        this.innId = innId;
        this.roomTypeId = roomTypeId;
        this.otaType = channelSource.name();
    }

    public String getOtaType() {
        return otaType;
    }

    public void setOtaType(String otaType) {
        this.otaType = otaType;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public LogDec getLogDec() {
        return logDec;
    }

    public void setLogDec(LogDec logDec) {
        this.logDec = logDec;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }
}
