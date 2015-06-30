package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.InnLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/12.
 */
public interface InnLabelDao {
    /**
     * 创建客栈标签
     *
     * @param innLabel
     * @return
     */
    int insertInnLabel(InnLabel innLabel);

    /**
     * 根据所属公司ID查询客栈标签
     *
     * @param companyId 所属公司ID
     * @return
     */
    List<InnLabel> selectInnLabelsByCompanyId(@Param("companyId") String companyId);

    /**
     * 查询客栈标签根据标签ID
     *
     * @param id 标签ID
     * @return
     */
    InnLabel selectLabelById(@Param("id") String id);

    /**
     * 更新标签信息
     *
     * @param innLabel 标签
     * @return
     */
    int updateLabelById(InnLabel innLabel);

    /**
     * 删除客栈标签
     *
     * @param id 标签ID
     * @return
     */
    int deletedLabelId(@Param("id") String id);
}
