package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.InnLabelDao;
import com.fanqielaile.toms.dao.UserInfoDao;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdayin on 2015/5/15.
 */
@Service
public class BangInnService implements IBangInnService {
    @Resource
    private BangInnDao bangInnDao;
    @Resource
    private InnLabelDao innLabelDao;
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public List<BangInn> findBangInnByInnLabelId(String innLabelId) {
        return this.bangInnDao.selectBangInnByInnLabelId(innLabelId);
    }

    @Override
    public List<BangInn> findBangInnBuUser(UserInfo userInfo) {
        return this.bangInnDao.selectBangInnByUser(userInfo);
    }

    @Override
    public List<BangInn> findBangInnAndLabel(UserInfo userInfo) {
        List<BangInn> results = new ArrayList<>();
        List<BangInn> labels = this.bangInnDao.selectBangInnByUser(userInfo);
        if (null != labels) {
            for (BangInn bangInn : labels) {
                List<BangInn> inns = this.bangInnDao.selectBangInnByInnLabelId(bangInn.getInnLabelId());
                InnLabel innLabel = this.innLabelDao.selectLabelById(bangInn.getInnLabelId());
                if (null != innLabel && null != inns) {
                    BangInn inn = new BangInn();
                    inn.setInnLabelId(innLabel.getId());
                    inn.setInnLabelName(innLabel.getLabelName());
                    inn.setBangInnList(inns);
                    results.add(inn);
                }
            }
        }
        return results;
    }

    @Override
    public List<BangInnDto> findBangInnListByUserInfo(UserInfo userInfo, PageBounds pageBounds) {
        List<BangInnDto> bangInnDtoList = this.bangInnDao.selectBangInnListByUserInfo(userInfo, pageBounds);
        if (ArrayUtils.isNotEmpty(bangInnDtoList.toArray())) {
            for (BangInnDto bangInnDto : bangInnDtoList) {
                if (StringUtils.isNotEmpty(bangInnDto.getInnLabelId())) {
                    //标签
                    InnLabel innLabel = this.innLabelDao.selectLabelById(bangInnDto.getInnLabelId());
                    bangInnDto.setLabelName(innLabel.getLabelName());
                }
                if (StringUtils.isNotEmpty(bangInnDto.getUserId())) {
                    //所属管理员
                    UserInfo info = this.userInfoDao.selectUserInfoById(bangInnDto.getUserId());
                    bangInnDto.setUserName(info.getUserName());
                }
            }
        }
        return bangInnDtoList;
    }

    @Override
    public BangInnDto findBangInnById(String id) {
        BangInnDto bangInnDto = this.bangInnDao.selectBangInnById(id);
        if (null != bangInnDto) {
            if (StringUtils.isNotEmpty(bangInnDto.getInnLabelId())) {
                InnLabel innLabel = this.innLabelDao.selectLabelById(bangInnDto.getInnLabelId());
                bangInnDto.setLabelName(innLabel.getLabelName());
            }
            if (StringUtils.isNotEmpty(bangInnDto.getUserId())) {
                UserInfo userInfo = this.userInfoDao.selectUserInfoById(bangInnDto.getUserId());
                bangInnDto.setUserName(userInfo.getUserName());
            }
        }
        return bangInnDto;
    }

    @Override
    public void modifiyBangInn(BangInnDto bangInnDto) {
        this.bangInnDao.updateBangInn(bangInnDto);
    }

    @Override
    public List<BangInnDto> findCompanyByInnId(int innId) {
        return this.bangInnDao.selectCompanyByInnId(innId);
    }

    @Override
    public void addBanginn(BangInnDto bangInnDto) {
        this.bangInnDao.createBangInn(bangInnDto);
    }

    @Override
    public BangInn findBangInnByCompanyIdAndInnId(String companyId, int innId) {
        return this.bangInnDao.selectBangInnByCompanyIdAndInnId(companyId, innId);
    }

    @Override
    public List<BangInn> findBangInnByStringBangInn(List<BangInn> bangInnList) {
        List<BangInn> bangInns = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(bangInnList.toArray())) {
            for (BangInn bangInn : bangInnList) {
                BangInnDto bangInnDto = this.bangInnDao.selectBangInnById(bangInn.getId());
                bangInns.add(bangInnDto);
            }
        }
        return bangInns;
    }

    @Override
    public List<BangInn> findBangInnByCompanyId(String companyId) {
        return this.bangInnDao.selectBangInnByCompanyId(companyId);
    }
}
