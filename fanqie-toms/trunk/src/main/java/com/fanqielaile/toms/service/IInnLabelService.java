package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.InnLabel;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/12.
 */
public interface IInnLabelService {
    /**
     * 创建客栈标签
     *
     * @param innLabel
     * @return
     */
    int createInnLabel(InnLabel innLabel);

    /**
     * 查询客栈标签
     *
     * @param companyId 所属公司ID
     * @return
     */
    List<InnLabel> findLabelsByCompanyId(String companyId);

    /**
     * 根据ID查询客栈标签
     *
     * @param id 客栈标签ID
     * @return
     */
    InnLabel findLabelById(String id);

    /**
     * 编辑客栈标签
     *
     * @param innLabel 标签
     * @return
     */
    void modifyLableById(InnLabel innLabel);

    /**
     * 删除标签
     *
     * @param id 标签ID
     * @return
     */
    void removeLabelById(String id);
}
