package com.fanqielaile.toms.model.fc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by wangdayin on 2015/9/24.
 * 天下房仓同步价格信息接口
 */
@XmlRootElement(name = "Request")
@XmlType(propOrder = {"header", "syncRateInfoDataRequest"})
public class SyncRateInfoRequest extends FcRequest {
    //头部
    private Header header;
    //同步价格数据信息
    private SyncRateInfoDataRequest syncRateInfoDataRequest;

    @XmlElement(name = "Header")
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @XmlElement(name = "SyncRateInfoRequest")
    public SyncRateInfoDataRequest getSyncRateInfoDataRequest() {
        return syncRateInfoDataRequest;
    }

    public void setSyncRateInfoDataRequest(SyncRateInfoDataRequest syncRateInfoDataRequest) {
        this.syncRateInfoDataRequest = syncRateInfoDataRequest;
    }
}
