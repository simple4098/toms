package com.fanqielaile.toms.dao;

import com.fanqielaile.toms.dto.OtherConsumerInfoDto;
import com.fanqielaile.toms.model.OtherConsumerFunction;
import com.fanqielaile.toms.model.OtherConsumerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DESC : 其他消费功能模块dao
 * @author : 番茄木-ZLin
 * @data : 2016/3/29
 * @version: v1.0.0
 */
public interface IOtherConsumerInfoDao {

    OtherConsumerFunction selectFunction(@Param("companyId") String companyId);

    /**
     * 新增公司的其他消费的配置
     */
    void insertConsumerFunction(OtherConsumerFunction consumerFunction);

    /**
     * 更新其他消费的开通状态
     * @param consumerFunction
     */
    void updateFunction(OtherConsumerFunction consumerFunction);

    /**
     *  parentId 为null的时候就查询父节点 如果 parentId不为null 查询子节点
     * @param companyId 公司id
     * @param parentId 父id
     */
    List<OtherConsumerInfoDto> selectConsumerInfo(@Param("companyId") String companyId,@Param("parentId") String parentId);

    /**
     * @param id ConsumerInfo id
     */
    List<OtherConsumerInfoDto> selectConsumerInfoById(@Param("id") String id);
}
