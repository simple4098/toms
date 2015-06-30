package com.fanqielaile.toms.dto;

import com.fanqie.core.dto.InnActiveDto;
import com.fanqie.util.Pagination;

import java.util.List;

/**
 * DESC : 第三方活跃客栈dto
 * @author : 番茄木-ZLin
 * @data : 2015/6/1
 * @version: v1.0.0
 */
public class ActiveInnDto {
    private List<InnActiveDto> innActiveList;
    private Pagination pagination;
    private Integer maxLen;

    public Integer getMaxLen() {
        return maxLen;
    }

    public void setMaxLen(Integer maxLen) {
        this.maxLen = maxLen;
    }

    public ActiveInnDto(List<InnActiveDto> innActiveList, Pagination pagination,Integer maxLen) {
        this.innActiveList = innActiveList;
        this.pagination = pagination;
        this.maxLen = maxLen;
    }

    public List<InnActiveDto> getInnActiveList() {
        return innActiveList;
    }

    public void setInnActiveList(List<InnActiveDto> innActiveList) {
        this.innActiveList = innActiveList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
