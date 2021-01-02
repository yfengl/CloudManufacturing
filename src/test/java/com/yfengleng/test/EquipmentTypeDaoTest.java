package com.yfengleng.test;

import com.yfengleng.bean.EquipmentType;
import com.yfengleng.dao.EquipmentTypeDao;
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

public class EquipmentTypeDaoTest {
    private InputStream in;
    private SqlSession session;
    private EquipmentTypeDao equipmentTypeDao;

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
        equipmentTypeDao = session.getMapper(EquipmentTypeDao.class);
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
        List<EquipmentType> list = equipmentTypeDao.findAll();
        for(EquipmentType equipmentType:list){
            System.out.println(equipmentType);
        }
    }

    @Test
    public void testFindByTypeId(){
        EquipmentType equipmentType = equipmentTypeDao.findByTypeId(1);
        System.out.println(equipmentType);
    }

    @Test
    public void testFindByTypeName(){
        List<EquipmentType> equipmentTypeList = equipmentTypeDao.findByTypeName("机床");
        for(EquipmentType equipmentType:equipmentTypeList){
            System.out.println(equipmentType);
        }
    }

    @Test
    public void testInsert(){
        EquipmentType equipmentType =new EquipmentType("机身");
        equipmentTypeDao.insert(equipmentType);
    }

    @Test
    public void testUpdate(){
        EquipmentType equipmentType = equipmentTypeDao.findByTypeId(5);
        equipmentType.setTypeId(4);
        equipmentTypeDao.update(equipmentType);
    }

    @Test
    public void testDeleteByTypeId(){
        equipmentTypeDao.deleteByTypeId(5);
    }
}
