package com.fanqielaile.toms.helper;

import com.fanqie.util.Pagination;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

/**
 * 分页帮助类
 * Created by wangdayin on 2015/6/1.
 */
public class PaginationHelper {
    public static Pagination toPagination(Paginator paginator) {
        Pagination pagination = new Pagination();
        pagination.setPage(paginator.getPage());
        pagination.setPageCount(paginator.getTotalPages());
        pagination.setRows(paginator.getLimit());
        pagination.setRowsCount(paginator.getTotalCount());
        return pagination;
    }
}
