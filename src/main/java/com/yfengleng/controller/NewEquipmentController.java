package com.yfengleng.controller;

import com.yfengleng.bean.*;
import com.yfengleng.dao.*;
import com.yfengleng.util.MybatisUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.ibatis.session.SqlSession;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewEquipmentController implements Initializable {

    private EquipmentDao equipmentDao;
    private EquipmentTypeDao equipmentTypeDao;
    private FactoryDao factoryDao;

    @FXML
    TextField equipmentName,equipmentSpecification,equipmentTypeName,factoryName;

    public void newEquipment() {
        SqlSession session = MybatisUtil.getSession();
        equipmentDao=session.getMapper(EquipmentDao.class);
        equipmentTypeDao=session.getMapper(EquipmentTypeDao.class);
        factoryDao=session.getMapper(FactoryDao.class);

        Factory factory = factoryDao.findByFactoryName(factoryName.getText());
        List<EquipmentType> equipmentTypeList = equipmentTypeDao.findByTypeName(equipmentTypeName.getText());
        if(factory!=null){
            if(equipmentTypeList.size()==1){
                Equipment equipment=new Equipment(equipmentTypeList.get(0).getTypeId(),equipmentName.getText(),equipmentSpecification.getText());
                equipment.setEquipmentStatus("关机");
                equipment.setFid(factory.getFactoryId());
                if(factory.getFactoryId()==1){
                    equipment.setRentalStatus("工厂私有");
                }else{
                    equipment.setRentalStatus("个人私有");
                }

                equipmentDao.insert(equipment);
                session.commit();
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("添加成功");
                alert.show();
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("设备类别错误");
                alert.show();
            }
        }else{
            if(equipmentTypeList.size()==1){
                Equipment equipment=new Equipment(equipmentTypeList.get(0).getTypeId(),equipmentName.getText(),equipmentSpecification.getText());
                equipment.setEquipmentStatus("闲置");
                equipment.setRentalStatus("工厂私有");
                equipmentDao.insert(equipment);
                session.commit();
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("添加成功,未设置所属工厂或工厂名称有误");
                alert.show();
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("设备类别错误");
                alert.show();
            }
        }

        //提交事务
        session.commit();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
