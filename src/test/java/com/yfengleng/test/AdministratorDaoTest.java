package com.yfengleng.test;

import com.yfengleng.bean.Administrator;
import com.yfengleng.dao.AdministratorDao;
import com.yfengleng.util.Md5SaltTool;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class AdministratorDaoTest {
    private InputStream in;
    private SqlSession session;
    private AdministratorDao administratorDao;

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
        administratorDao = session.getMapper(AdministratorDao.class);
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
    public void testLogin() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Administrator administrator = administratorDao.login("admin");
        String password="admin";
        if(administrator!=null&& Md5SaltTool.validPassword
                (password,administrator.getPassword())){
            System.out.println("登陆成功！");
        }else{
            System.out.println("登陆失败");
        }
    }
}
