package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.InnLabelDao;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
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
}
