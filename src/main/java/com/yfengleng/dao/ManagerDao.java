package com.yfengleng.dao;

import com.yfengleng.bean.Manager;

import java.util.List;

public interface ManagerDao {

    /**
     * 云工厂管理员登陆
     * @param account 管理员账号
     * @return 管理员对象
     */
    Manager login(String account);

    /**
     * 新增云工厂管理员
     * @param manager 新增对象
     */
    void insert(Manager manager);

    /**
     * 根据ID删除云工厂管理员
     * @param userId id
     */
    void deleteById(int userId);

    /**
     * 根据账号删除云工厂管理员
     * @param account
     */
    void deleteByAccount(String account);

    /**
     * 查询所有
     * @return 云工厂管理员列表
     */
    List<Manager> findAll();

    /**
     * 根据account或name或tel或email查询
     * @param info 信息
     * @return 云工厂管理员的列表
     */
    List<Manager> findByInfo(String info);

    /**
     * 更新数据
     * @param manager 云工厂管理员
     */
    void update(Manager manager);
}
