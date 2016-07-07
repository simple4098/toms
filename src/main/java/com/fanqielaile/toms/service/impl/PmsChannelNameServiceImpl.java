package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.PmsChannelNameDao;
import com.fanqielaile.toms.model.PmsChannelNameBean;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IPmsChannelNameService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangdayin on 2016/7/6.
 */
@Service
public class PmsChannelNameServiceImpl implements IPmsChannelNameService {
    @Resource
    private PmsChannelNameDao pmsChannelNameDao;
    @Override
    public PmsChannelNameBean findPmsChannelNameByCompanyId(UserInfo currentUser) {
        return this.pmsChannelNameDao.selectPmsChannelNameByCompanyId(currentUser.getCompanyId());
    }

    @Override
    public void createPmsChannelName(PmsChannelNameBean pmsChannelNameBean, UserInfo currentUser) {
        pmsChannelNameBean.setCompanyId(currentUser.getCompanyId());
        pmsChannelNameBean.setCreatorId(currentUser.getId());
        this.pmsChannelNameDao.insertIntoPmsChannelName(pmsChannelNameBean);
    }
}
