package com.yfengleng.test;

import com.yfengleng.bean.Consignee;
import com.yfengleng.dao.ConsigneeDao;
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
import java.util.List;

public class ConsigneeDaoTest {
    private InputStream in;
    private SqlSession session;
    private ConsigneeDao consigneeDao;

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
        consigneeDao = session.getMapper(ConsigneeDao.class);
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
        Consignee consignee = consigneeDao.login("testConsignee");
        String password="Consignee";
        if(consignee!=null&& Md5SaltTool.validPassword
                (password,consignee.getPassword())){
            System.out.println("Login Successfully!");
        }else{
            System.out.println("fail");
        }
    }

    @Test
    public void testInsert() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Consignee consignee=new Consignee();
        consignee.setUserId(2);
        consignee.setAccount("testConsignee2");
        consignee.setPassword(Md5SaltTool.getEncryptedPwd("testConsignee2"));
        consignee.setName("test");
        consignee.setEmail("sample@qq.com");
        consignee.setType("经销商");
        consignee.setTel("test");
        consignee.setAddress("test");
        consigneeDao.insert(consignee);
    }

    @Test
    public void testDeleteById(){
        consigneeDao.deleteById(2);
    }

    @Test
    public void testFindAll(){
        List<Consignee> list = consigneeDao.findAll();
        for(Consignee consignee:list){
            System.out.println(consignee.getAccount());
        }
    }

    @Test
    public void testUpdate() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Consignee consignee=new Consignee();
        consignee.setUserId(1);
        consignee.setAccount("testConsignee3");
        consignee.setPassword(Md5SaltTool.getEncryptedPwd("testConsignee3"));
        consignee.setName("test");
        consignee.setEmail("sample@qq.com");
        consignee.setType("经销商");
        consignee.setTel("test");
        consignee.setAddress("test");
        consigneeDao.update(consignee);
    }

    @Test
    public void testFindByInfo(){
        List<Consignee> consigneeList = consigneeDao.findByInfo("15071707098");
        for(Consignee consignee:consigneeList){
            System.out.println(consignee.getName());
        }
    }
}
