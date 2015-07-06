package com.fanqielaile.toms.dto;

import java.util.List;

/**
 * DESC :
 * @author : 番茄木-ZLin
 * @data : 2015/7/6
 * @version: v1.0.0
 */
public class ProxyInns {
    private Integer innId;
    private List<PricePattern> pricePatterns;

    public Integer getInnId() {
        return innId;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public List<PricePattern> getPricePatterns() {
        return pricePatterns;
    }

    public void setPricePatterns(List<PricePattern> pricePatterns) {
        this.pricePatterns = pricePatterns;
    }
}
