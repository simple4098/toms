package com.fanqielaile.toms.model;

import com.fanqie.core.Domain;

/**
 * Created by wangdayin on 2015/5/12.
 */
public class NoticeTemplate extends Domain {
    //通知标题
    private String noticeTitle;
    //通知内容
    private String noticeContent;
    //所属公司ID
    private String companyId;

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
