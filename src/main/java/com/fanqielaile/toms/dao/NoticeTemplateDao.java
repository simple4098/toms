package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.NoticeTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/12.
 */
public interface NoticeTemplateDao {
    /**
     * 新增通知模板
     *
     * @param noticeTemplate
     * @return
     */
    int insertNoticeTemplate(NoticeTemplate noticeTemplate);

    /**
     * 根据所属公司ID查询客栈的通知模板
     *
     * @param companyId 所属公司ID
     * @return
     */
    List<NoticeTemplate> selectNoticeTemplates(@Param("companyId") String companyId);

    /**
     * 根据id查询通知模板
     *
     * @param id 通知ID
     * @return
     */
    NoticeTemplate selectNoticeTemplateById(@Param("id") String id);

    /**
     * 更新通知模板
     *
     * @param noticeTemplate 通知
     * @return
     */
    int updateNoticeTemplate(NoticeTemplate noticeTemplate);

    /**
     * 删除通知模板
     *
     * @param id 通知ID
     * @return
     */
    int deleteNoticeTemplateById(String id);
}
