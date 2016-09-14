package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.model.CtripHomeStayRoomRef;

import java.util.List;

/**
 * Create by jame
 * Date: 2016/9/7 15:54
 * Version: 1.0
 * Description: 阐述
 */
public interface ICtripHomeStayRoomRefDao {
    int insertAll(List<CtripHomeStayRoomRef> list);

    List<CtripHomeStayRoomRef> query(CtripHomeStayRoomRef ctripHomeStayRoomRef);

    int deleteByAccountId(CtripHomeStayRoomRef ctripHomeStayRoomRef);
}
