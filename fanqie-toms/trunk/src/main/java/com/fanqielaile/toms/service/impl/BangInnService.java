package com.fanqielaile.toms.service.impl;

import com.fanqie.util.DcUtil;
import com.fanqie.util.HttpClientUtil;
import com.fanqie.util.JacksonUtil;
import com.fanqielaile.toms.common.CommonApi;
import com.fanqielaile.toms.dao.*;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.InnDto;
import com.fanqielaile.toms.dto.OmsImg;
import com.fanqielaile.toms.dto.RoomTypeInfo;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import com.fanqielaile.toms.model.InnLabel;
import com.fanqielaile.toms.model.UserInfo;
import com.fanqielaile.toms.service.IBangInnService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Resource
    private CompanyDao companyDao;

    @Override
    public List<BangInn> findBangInnByInnLabelId(String innLabelId) {
        return this.bangInnDao.selectBangInnByInnLabelId(innLabelId,new UserInfo());
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
                List<BangInn> inns = this.bangInnDao.selectBangInnByInnLabelId(bangInn.getInnLabelId(),userInfo);
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

    @Override
    public BangInn findBangInnByUserAndCode(UserInfo userInfo, String code) {
        return this.bangInnDao.selectBangInnByUserAndCode(userInfo, code);
    }

    @Override
    public List<BangInn> findBangInnImages(String companyId) throws IOException {
        List<BangInn> bangInns = this.bangInnDao.selectBangInnByCompanyId(companyId);
        Company company = this.companyDao.selectCompanyById(companyId);
        if (ArrayUtils.isNotEmpty(bangInns.toArray()) && null != company) {
            for (BangInn bangInn : bangInns) {
                //封装访问的路径与参数
                String timestamp = String.valueOf(new Date().getTime());
                String signature = DcUtil.obtMd5(company.getOtaId() + timestamp + company.getUserAccount() + company.getUserPassword());
                String url = CommonApi.getInnInfo() + "?timestamp=" + timestamp + "&otaId=" + company.getOtaId() + "&accountId=" + bangInn.getAccountId() + "&signature=" + signature;
                String response = HttpClientUtil.httpGets(url, null);
                JSONObject jsonInn = JSONObject.fromObject(response);
                if ("200".equals(jsonInn.get("status").toString()) && jsonInn.get("list") != null) {
                    InnDto omsInnDto = JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
                    bangInn.setInnDto(omsInnDto);
                }
            }
        }
        return bangInns;
    }

    @Override
    public List<RoomTypeInfo> findBangInnRoomImage(BangInnDto bangInnDto) throws IOException {
        Company company = this.companyDao.selectCompanyById(bangInnDto.getCompanyId());
        List<RoomTypeInfo> result = new ArrayList<>();
        if (null != company) {
            String timestamp = String.valueOf(new Date().getTime());
            String signature = DcUtil.obtMd5(company.getOtaId() + timestamp + company.getUserAccount() + company.getUserPassword());
            String url = CommonApi.getRoomType() + "?timestamp=" + timestamp + "&from=" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "&to=" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "&otaId=" + company.getOtaId() + "&accountId=" + bangInnDto.getAccountId() + "&signature=" + signature;
            String response = HttpClientUtil.httpGets(url, null);
            JSONObject jsonObject = JSONObject.fromObject(response);
            if ("200".equals(jsonObject.get("status").toString()) && jsonObject.get("list") != null) {
                result = JacksonUtil.json2list(jsonObject.get("list").toString(), RoomTypeInfo.class);
                if (ArrayUtils.isNotEmpty(result.toArray())) {
                    for (RoomTypeInfo roomTypeInfo : result) {
                        if (ArrayUtils.isNotEmpty(roomTypeInfo.getImgList().toArray())) {
                            for (OmsImg omsImg : roomTypeInfo.getImgList()) {
                                omsImg.setSuffix(omsImg.getImgUrl().split("\\.")[1]);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public InnDto selectBangInnImage(BangInnDto bangInnDto) throws IOException {
        Company company = this.companyDao.selectCompanyById(bangInnDto.getCompanyId());
        //封装访问的路径与参数
        String timestamp = String.valueOf(new Date().getTime());
        String signature = DcUtil.obtMd5(company.getOtaId() + timestamp + company.getUserAccount() + company.getUserPassword());
        String url = CommonApi.getInnInfo() + "?timestamp=" + timestamp + "&otaId=" + company.getOtaId() + "&accountId=" + bangInnDto.getAccountId() + "&signature=" + signature;
        String response = HttpClientUtil.httpGets(url, null);
        JSONObject jsonInn = JSONObject.fromObject(response);
        if ("200".equals(jsonInn.get("status").toString()) && jsonInn.get("list") != null) {
            InnDto omsInnDto = JacksonUtil.json2list(jsonInn.get("list").toString(), InnDto.class).get(0);
            if (ArrayUtils.isNotEmpty(omsInnDto.getImgList().toArray())) {
                for (OmsImg omsImg : omsInnDto.getImgList()) {
                    omsImg.setSuffix(omsImg.getImgUrl().split("\\.")[1]);
                }
            }
            return omsInnDto;
        }
        return null;
    }
}
