package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.NoticeTemplateDao;
import com.fanqielaile.toms.model.NoticeTemplate;
import com.fanqielaile.toms.service.INoticeTemplateService;
import com.fanqielaile.toms.support.exception.TomsRuntimeException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/5/12.
 */
@Service
public class NoticeTemplateService implements INoticeTemplateService {
    @Resource
    private NoticeTemplateDao noticeTemplateDao;

    @Override
    public int createNoticeTemplate(NoticeTemplate noticeTemplate) {
        noticeTemplate.setId(UUID.randomUUID().toString());
        return noticeTemplateDao.insertNoticeTemplate(noticeTemplate);
    }

    @Override
    public List<NoticeTemplate> findNoticeTemplates(String companyId) {
        return this.noticeTemplateDao.selectNoticeTemplates(companyId);
    }

    @Override
    public NoticeTemplate findNoticeTemplateById(String id) {
        return this.noticeTemplateDao.selectNoticeTemplateById(id);
    }

    @Override
    public void modifyNoticeTemplate(NoticeTemplate noticeTemplate) {
        NoticeTemplate noticeTemplate1 = this.noticeTemplateDao.selectNoticeTemplateById(noticeTemplate.getId());
        if (null != noticeTemplate1) {
            this.noticeTemplateDao.updateNoticeTemplate(noticeTemplate);
        } else {
            throw new TomsRuntimeException("修改客栈通知模板错误，没有找到该模板");
        }
    }

    @Override
    public void removeNoticeTemplateById(String id) {
        NoticeTemplate noticeTemplate = this.noticeTemplateDao.selectNoticeTemplateById(id);
        if (null != noticeTemplate) {
            this.noticeTemplateDao.deleteNoticeTemplateById(id);
        } else {
            throw new TomsRuntimeException("删除客栈通知模板错误，没有找到该模板");
        }
    }
}
