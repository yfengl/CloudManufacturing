package com.yfengleng.dao;

import com.yfengleng.bean.Consignee;

import java.util.List;

public interface ConsigneeDao {

    /**
     * 经销商登陆
     * @param account 账号
     * @return 经销商对象
     */
    Consignee login(String account);

    /**
     * 新增经销商
     * @param consignee 经销商对象
     */
    void insert(Consignee consignee);

    /**
     * 根据ID删除经销商
     * @param userId id
     */
    void deleteById(int userId);

    /**
     * 根据账号删除经销商
     * @param account
     */
    void deleteByAccount(String account);

    /**
     * 查询所有
     * @return 经销商列表
     */
    List<Consignee> findAll();

    /**
     * 根据account或name或tel或email查询
     * @param info
     * @return
     */
    List<Consignee> findByInfo(String info);

    /**
     * 更新数据
     * @param consignee 供销商
     */
    void update(Consignee consignee);
}
