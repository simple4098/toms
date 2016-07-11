package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.IOtherConsumerInfoDao;
import com.fanqielaile.toms.dao.MyselfChannelDao;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.MyselfChannel;
import com.fanqielaile.toms.model.OtherConsumerFunction;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IMyselfChannelService;
import com.fanqielaile.toms.support.util.Constants;
import com.fanqielaile.toms.support.util.TomsUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2016/7/5.
 */
@Service
public class MyselfChannelServiceImpl implements IMyselfChannelService {
    @Resource
    private MyselfChannelDao myselfChannelDao;
    @Resource
    private CompanyDao companyDao;

    @Resource
    private IOtherConsumerInfoDao otherConsumerInfoDao;

    @Override
    public void createMyselfChannel(MyselfChannel myselfChannel, UserInfo currentUser) {
        Company company = this.companyDao.selectCompanyById(currentUser.getCompanyId());
        //1.生成自定义渠道code
        String myselfChannelCode = "";
        while (true){
            myselfChannelCode = TomsUtil.getMyselfChannelCode(company.getCompanyCode(), myselfChannel.getChannelName());
            MyselfChannel myselfChannelByCode = this.myselfChannelDao.selectMyselfChannelCode(myselfChannelCode);
            if (null==myselfChannelByCode){
                break;
            }
        }
        myselfChannel.setChannelCode(myselfChannelCode);
        myselfChannel.setCompanyId(currentUser.getCompanyId());
        myselfChannel.setCreatorId(currentUser.getId());
        this.myselfChannelDao.insertIntoMyselfChannel(myselfChannel);
    }

    @Override
    public MyselfChannel findMyselfChannelByName(String channelName) {
        return this.myselfChannelDao.selectMyselfChannelByChannelName(channelName);
    }

    @Override
    public List<MyselfChannel> findMyselfChannelList(UserInfo currentUser) {
        //查询公司是否开启自定义渠道
        OtherConsumerFunction otherConsumerFunction = otherConsumerInfoDao.selectFunction(currentUser.getCompanyId());
        if (otherConsumerFunction.getMyselfChannelStatus()) {
            return this.myselfChannelDao.selectMyselfChannelByCompanyId(currentUser.getCompanyId());
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> modifyMyselfChannel(String id, String channelName, UserInfo currentUser) {
        Map<String,Object> result = new HashMap<>();
        MyselfChannel myselfChannel = this.myselfChannelDao.selectMyselfChannelById(id);
        Company company = this.companyDao.selectCompanyById(currentUser.getCompanyId());
        if (null != myselfChannel){
            myselfChannel.setChannelName(channelName);
            myselfChannel.setChannelCode(TomsUtil.getMyselfChannelCode(company.getCompanyCode(),channelName));
            myselfChannel.setCreatorId(currentUser.getId());
            this.myselfChannelDao.updateMySelfChannel(myselfChannel);
            result.put(Constants.STATUS,true);
            result.put(Constants.MESSAGE,"修改成功");
        }else {
            result.put(Constants.STATUS,false);
            result.put(Constants.MESSAGE,"没有找到该自定义渠道，请重试!");
        }
        return result;
    }

    @Override
    public void removeMyselfChannelById(String id) {
        this.myselfChannelDao.deletedMySelfChannel(id);
    }

    @Override
    public void modifyCompanyChannelStatus(UserInfo currentUser,Boolean status) {
        this.myselfChannelDao.updateCompanyChannelStatus(currentUser.getCompanyId(),status);
    }

    @Override
    public void modifyPmsCompanyChannelStatus(UserInfo currentUser, Boolean status) {
        this.myselfChannelDao.updatePmsCompanyChannelStatus(currentUser.getCompanyId(),status);
    }
}
