package com.yfengleng.dao;

import com.yfengleng.bean.Administrator;

public interface AdministratorDao {

    /**
     * Administrator的登陆方法
     * @param account 超级管理员的账号
     * @return 超级管理员对象
     */
    Administrator login(String account);
}
