package com.yfengleng.test;

import com.yfengleng.bean.ProductType;
import com.yfengleng.dao.ProductTypeDao;
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

public class ProductTypeDaoTest {
    private InputStream in;
    private SqlSession session;
    private ProductTypeDao productTypeDao;

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
        productTypeDao = session.getMapper(ProductTypeDao.class);
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
        List<ProductType> all = productTypeDao.findAll();
        for(ProductType productType:all){
            System.out.println(productType);
        }
    }

    @Test
    public void testFindByTypeId(){
        ProductType productType = productTypeDao.findByTypeId(1);
        System.out.println(productType);
    }

    @Test
    public void testFindByTypeName(){
        List<ProductType> productTypeList = productTypeDao.findByTypeName("棉制品");
        for(ProductType productType:productTypeList){
            System.out.println(productType);
        }
    }

    @Test
    public void testInsert(){
        ProductType p = productTypeDao.findByTypeId(1);
        p.setTypeId(3);
        productTypeDao.insert(p);
    }

    @Test
    public void testUpdate(){
        ProductType p= productTypeDao.findByTypeId(2);
        p.setTypeName("铁器");
        productTypeDao.update(p);
    }

    @Test
    public void testDeleteByTypeId(){
        productTypeDao.deleteByTypeId(2);
    }
}
