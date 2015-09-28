package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2015/9/8.
 * 天下房仓推送订单状态请求对象
 */
@XmlRootElement(name = "Request")
public class FcOrderStatusRequest extends FCcheckRoomAvailResponseResult {
    @XmlElement(name = "Header")
    private Header header;
    @XmlElement(name = "SyncOrderStatusRequest")
    private SyncOrderStatusRequest syncOrderStatusRequest;

    public FcOrderStatusRequest(Header header, SyncOrderStatusRequest syncOrderStatusRequest) {
        this.header = header;
        this.syncOrderStatusRequest = syncOrderStatusRequest;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public SyncOrderStatusRequest getSyncOrderStatusRequest() {
        return syncOrderStatusRequest;
    }

    public void setSyncOrderStatusRequest(SyncOrderStatusRequest syncOrderStatusRequest) {
        this.syncOrderStatusRequest = syncOrderStatusRequest;
    }
}
