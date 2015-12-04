package com.fanqielaile.toms.support.holder;

import com.fanqie.core.dto.TBParam;
import com.fanqielaile.toms.dao.BangInnDao;
import com.fanqielaile.toms.dao.CompanyDao;
import com.fanqielaile.toms.dao.IOtaInfoDao;
import com.fanqielaile.toms.dto.BangInnDto;
import com.fanqielaile.toms.dto.OtaInfoRefDto;
import com.fanqielaile.toms.dto.RoomDetail;
import com.fanqielaile.toms.model.BangInn;
import com.fanqielaile.toms.model.Company;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DESC : 渠道上、下架、改价 Holder，主要判断不同公司用相同appKey的情况
 * @author : 番茄木-ZLin
 * @data : 2015/11/29
 * @version: v1.0.0
 */
@Component
public class TPHolder {
    @Resource
    private IOtaInfoDao otaInfoDao;
    @Resource
    private BangInnDao bangInnDao;

    /**
     * 上、下 架 进行一个验证
     * @param company 当前登录者属于的公司
     * @param innId 客栈id
     * @param otaInfoId 渠道id
     */
    public void  validate(Company company ,String  innId,String otaInfoId)throws Exception{
        Integer inn = Integer.valueOf(innId);
        //此公司当前的渠道信息
        OtaInfoRefDto otaInfo = otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(company.getId(),otaInfoId);
        if (otaInfo!=null){
            //查询当前appKey 还有哪些公司再用。找出拥有时间最早appKey
            OtaInfoRefDto refDto = otaInfoDao.selectOtaInfoByAppKey(otaInfo);
            BangInn bangInn = bangInnDao.selectBangInnByCompanyIdAndInnId(company.getId(), inn);
            //bangInn 为空，说明是新进来的客栈
            if (refDto!=null && refDto.getCompanyId().equals(otaInfo.getCompanyId()) && bangInn==null ){
                validateCommon(otaInfo,inn);
            }
        }else {
            throw  new Exception("otaInfoId 参数有误!");
        }

    }


    /**
     * 渠道增减价进行严重
     * @param company 公司
     * @param innId 客栈id
     * @param otaInfoId 渠道id
     */
    public void  validatePrice(Company company ,String  innId,String otaInfoId)throws Exception{
        Integer inn = Integer.valueOf(innId);
        OtaInfoRefDto otaInfo = otaInfoDao.selectOtaInfoByCompanyIdAndOtaInnOtaId(company.getId(),otaInfoId);
        if (otaInfo!=null){
            //查询当前appKey 还有哪些公司再用。找出拥有时间最早appKey
            OtaInfoRefDto refDto = otaInfoDao.selectOtaInfoByAppKey(otaInfo);
            if (refDto!=null && !refDto.getCompanyId().equals(otaInfo.getCompanyId())){
                validateCommon(otaInfo,inn);
            }
        }else {
            throw  new Exception("otaInfoId 参数有误!");
        }
    }

    /**
     * @param otaInfo 当前渠道
     * @param inn 客栈id
     */
    private void validateCommon(  OtaInfoRefDto otaInfo,Integer inn)throws Exception{
        List<String> list = otaInfoDao.selectOtaByAppKey(otaInfo);
        List<BangInn> bangInnList = bangInnDao.selectBangInnByCompanyListInnId(new BangInnDto(list, inn));
        if (!CollectionUtils.isEmpty(bangInnList)) {
            throw new Exception(otaInfo.getTbType().name()+" 此客栈在相同appKey的另一个公司存在!");
        }
    }

    /**
     * 渠道加减价验证
     * @param value 加减价的值
     * @param roomDetailList 要加减价的时间集合
     */
    public boolean checkRooPrice(double value,List<RoomDetail> roomDetailList) {
        if (value < 0) {
            if (!CollectionUtils.isEmpty(roomDetailList)) {
                List<Double> priceList = new ArrayList<>();
                for (RoomDetail roomDetail : roomDetailList) {
                    priceList.add(roomDetail.getRoomPrice());
                }
                Collections.sort(priceList);
                Double price = priceList.get(0);
                //value本来为负数, 转化为整数比较
                if (!(price + value >= 1)) {
                        return false;
                }
            }
        }
        return true;
    }
}
