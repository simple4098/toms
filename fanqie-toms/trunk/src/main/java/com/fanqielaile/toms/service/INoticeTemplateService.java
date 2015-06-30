package com.fanqielaile.toms.service;

import com.fanqielaile.toms.model.NoticeTemplate;

import java.util.List;

/**
 * Created by wangdayin on 2015/5/12.
 */
public interface INoticeTemplateService {
    /**
     * 创建通知模板
     *
     * @param noticeTemplate
     * @return
     */
    int createNoticeTemplate(NoticeTemplate noticeTemplate);

    /**
     * 根据所属公司查询通知模板
     *
     * @param companyId
     * @return
     */
    List<NoticeTemplate> findNoticeTemplates(String companyId);

    /**
     * 根据idc查询通知模板
     *
     * @param id
     * @return
     */
    NoticeTemplate findNoticeTemplateById(String id);

    /**
     * 修改通知模板
     *
     * @param noticeTemplate
     * @return
     */
    void modifyNoticeTemplate(NoticeTemplate noticeTemplate);

    /**
     * 删除通知模板
     *
     * @param id
     * @return
     */
    void removeNoticeTemplateById(String id);
}
