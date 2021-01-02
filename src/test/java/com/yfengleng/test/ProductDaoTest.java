package com.yfengleng.test;

import com.yfengleng.bean.Product;
import com.yfengleng.dao.ProductDao;
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

public class ProductDaoTest {
    private InputStream in;
    private SqlSession session;
    private ProductDao productDao;

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
        productDao = session.getMapper(ProductDao.class);
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
        List<Product> productList = productDao.findAll();
        for(Product product:productList){
            System.out.println(product);
        }
    }

    @Test
    public void testFindByProductId(){
        Product product = productDao.findByProductId(1);
        System.out.println(product);
    }

    @Test
    public void testFindByTypeId(){
        List<Product> productList = productDao.findByProductTypeId(2);
        for(Product product:productList){
            System.out.println(product);
        }
    }

    @Test
    public void testFindByInfo(){
        List<Product> list = productDao.findByInfo("武器");
        for(Product product:list){
            System.out.println(product);
        }
    }

    @Test
    public void testInsert(){
        Product product=productDao.findByProductId(2);
        product.setProductName("铁刀");
        product.setProductId(4);
        productDao.insert(product);
    }

    @Test
    public void testUpdate(){
        Product product=productDao.findByProductId(4);
        product.setProductName("铁枪");
        productDao.update(product);
    }

    @Test
    public void testDeleteByProductId(){
        productDao.deleteByProductId(4);
    }
}
