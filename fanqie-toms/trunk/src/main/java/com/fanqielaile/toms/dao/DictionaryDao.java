package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.Dictionary;

/**
 * Created by wangdayin on 2015/6/24.
 */
public interface DictionaryDao {
    /**
     * 根据type查询字典表
     *
     * @param type
     * @return
     */
    Dictionary selectDictionaryByType(String type);
}
