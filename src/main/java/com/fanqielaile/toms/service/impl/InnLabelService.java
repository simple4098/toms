package com.fanqielaile.toms.service.impl;

import com.fanqielaile.toms.dao.InnLabelDao;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.service.IInnLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangdayin on 2015/5/12.
 */
@Service
public class InnLabelService implements IInnLabelService {
    @Resource
    private InnLabelDao innLabelDao;

    @Override
    public int createInnLabel(InnLabel innLabel) {
        innLabel.setId(UUID.randomUUID().toString());
        return innLabelDao.insertInnLabel(innLabel);
    }

    @Override
    public List<InnLabel> findLabelsByCompanyId(String companyId) {
        return this.innLabelDao.selectInnLabelsByCompanyId(companyId);
    }

    @Override
    public InnLabel findLabelById(String id) {
        return this.innLabelDao.selectLabelById(id);
    }

    @Override
    public boolean modifyLableById(InnLabel innLabel) {
        InnLabel innLabel1 = this.innLabelDao.selectLabelById(innLabel.getId());
        if (null != innLabel1) {
            if (!innLabel1.getLabelName().equals(innLabel.getLabelName())) {
                this.innLabelDao.updateLabelById(innLabel);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeLabelById(String id) {
        InnLabel innLabel = this.innLabelDao.selectLabelById(id);
        if (null != innLabel) {
            this.innLabelDao.deletedLabelId(id);
            return true;
        } else {
            return false;
        }
    }
}