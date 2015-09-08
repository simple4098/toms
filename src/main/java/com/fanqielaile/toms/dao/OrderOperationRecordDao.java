package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.OrderOperationRecord;

/**
 * Created by wangdayin on 2015/9/7.
 */
public interface OrderOperationRecordDao {
    /**
     * 插入订单操作记录
     *
     * @param orderOperationRecord
     */
    void insertOrderOperationRecord(OrderOperationRecord orderOperationRecord);
}
