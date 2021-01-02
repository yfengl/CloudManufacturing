package com.yfengleng.test;

import com.yfengleng.bean.Factory;
import com.yfengleng.dao.FactoryDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FactoryDaoTest {
    private InputStream in;
    private SqlSession session;
    private FactoryDao factoryDao;

    @Before
    public void init() throws IOException {
        //1.读取主配置文件，返回文件流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        //3.创建SqlSessionFactory工厂
        SqlSessionFactory factory = builder.build(in);
        //4.使用工厂生产SqlSession对象
        session = factory.openSession();
        //5.创建dao的代理对象
        factoryDao = session.getMapper(FactoryDao.class);
    }

    @After
    public void destroy() throws Exception {
        //提交事务
        session.commit();
        //8.关闭资源
        session.close();
        in.close();
    }

    @Test
    public void testFindAll(){
        List<Factory> all = factoryDao.findAll();
        for(Factory factory:all){
            System.out.println(factory);
        }
    }

    @Test
    public void testFindByUid(){
        List<Factory> factoryList = factoryDao.findByUid(1);
        for(Factory factory:factoryList){
            System.out.println(factory);
        }
    }

    @Test
    public void testFindByInfo(){
        List<Factory> factoryList = factoryDao.findByInfo("棉花");
        for(Factory factory:factoryList){
            System.out.println(factory);
        }
    }

    @Test
    public void testUpdate(){
        List<Factory> factoryList = factoryDao.findByUid(2);
        Factory f=null;
        for(Factory factory:factoryList){
            f=factory;
        }
        f.setFactoryName("test4");
        factoryDao.update(f);
    }

}
