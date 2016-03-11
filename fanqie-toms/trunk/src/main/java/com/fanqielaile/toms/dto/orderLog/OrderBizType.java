package com.fanqielaile.toms.dto.orderLog;

/**
 * Created by wangdayin on 2016/3/1.
 */
public class OrderBizType {
    private Integer id;
    private Integer pid;

    public OrderBizType() {
    }

    public OrderBizType(Integer id, Integer pid) {
        this.id = id;
        this.pid = pid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
