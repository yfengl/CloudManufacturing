package com.yfengleng.dao;

import com.yfengleng.bean.Factory;

import java.util.List;

public interface FactoryDao {

    /**
     * 查询所有
     * @return 工厂列表
     */
    List<Factory> findAll();

    /**
     * 根据userId查询
     * @return 工厂列表
     * @param uid userId
     */
    List<Factory> findByUid(int uid);

    /**
     * 根据factoryId查询
     * @param factoryId
     * @return
     */
    Factory findByFactoryId(int factoryId);

    /**
     * 根据工厂信息查询
     * @return 工厂列表
     * @param info 信息
     */
    List<Factory> findByInfo(String info);

    /**
     * 根据工厂名称查找
     * @param factoryName
     * @return
     */
    Factory findByFactoryName(String factoryName);

    /**
     * 更新工厂
     * @param factory 更新的工厂
     */
    void update(Factory factory);

    /**
     * 新增工厂
     * @param factory
     */
    void insert(Factory factory);

    /**
     * 根据工厂名称删除工厂
     * @param factoryName
     */
    void deleteByFactoryName(String factoryName);
}
