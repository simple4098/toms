package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.fc.FcProvince;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/8/12
 * @version: v1.0.0
 */
public interface IFcProvinceDao {

    void  saveFcProvince(FcProvince fcProvince);

    FcProvince selectProvince(String province);
}
