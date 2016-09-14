package com.fanqielaile.toms.vo.ctrip.homestay;

import java.util.List;

/**
 * Create by jame
 * Date: 2016/9/12 9:58
 * Version: 1.0
 * Description: 阐述
 */
public class GetOrderReturnVo extends ReturnBaseVo {
    private List<GetOrderDetailVo> orders;

    public GetOrderReturnVo(List<GetOrderDetailVo> orders) {
        this.orders = orders;
    }

    public GetOrderReturnVo(){
        super();
    }

    public List<GetOrderDetailVo> getOrders() {
        return orders;
    }

    public void setOrders(List<GetOrderDetailVo> orders) {
        this.orders = orders;
    }
}
