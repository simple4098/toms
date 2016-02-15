package com.fanqielaile.toms.task;

import com.fanqielaile.toms.model.Order;
import com.fanqielaile.toms.service.IExceptionOrderService;
import com.fanqielaile.toms.service.IOrderService;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2016/1/28.
 */
@Component
public class ExceptionOrderTask {
    Logger logger = LoggerFactory.getLogger(ExceptionOrderTask.class);
    @Resource
    private IOrderService orderService;
    @Resource
    private IExceptionOrderService exceptionOrderService;

    //    @Scheduled(cron = "0 0/1 * * * ? ") //间隔60秒执行
    public void taskCycle() {
        logger.info(new Date() + "开始执行定时任务=======>");
        Map<String, String> map = TomsUtil.getFifteenDate();
        List<Order> exceptionOrderList = this.orderService.findExceptionOrderList(map);
        Order order = new Order().getOrderToExceptionOrder(exceptionOrderList);
        logger.info("插入异常订单一共" + (null == order.getExceptionOrderList() ? 0 : order.getExceptionOrderList().size()));
        if (ArrayUtils.isNotEmpty(order.getExceptionOrderList().toArray())) {
            //插入之前先删除已经存在异常订单
            this.exceptionOrderService.deleteExceptionOrder(order);
            //插入异常订单
            this.exceptionOrderService.createExceptionOrder(order);
        }
        logger.info(new Date() + "结束执行定时任务======>");
    }
}
