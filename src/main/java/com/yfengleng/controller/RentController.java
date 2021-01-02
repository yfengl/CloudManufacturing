package com.yfengleng.controller;

import com.yfengleng.bean.Equipment;
import com.yfengleng.bean.EquipmentType;
import com.yfengleng.bean.Factory;
import com.yfengleng.bean.Manager;
import com.yfengleng.controller.temp.EquipmentTemp;
import com.yfengleng.dao.EquipmentDao;
import com.yfengleng.dao.EquipmentTypeDao;
import com.yfengleng.dao.FactoryDao;
import com.yfengleng.util.MybatisUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.ibatis.session.SqlSession;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RentController implements Initializable {

    private Manager manager;
    private EquipmentDao equipmentDao;
    private EquipmentTypeDao equipmentTypeDao;
    private FactoryDao factoryDao;

    @FXML
    TableView<EquipmentTemp> equipmentTable;
    @FXML
    TableColumn<EquipmentTemp, String> equipmentName;
    @FXML
    TableColumn<EquipmentTemp, String> equipmentTypeName;
    @FXML
    TableColumn<EquipmentTemp, String> equipmentSpecification;
    @FXML
    TableColumn<EquipmentTemp, String> equipmentStatus;
    @FXML
    TableColumn<EquipmentTemp, String> rentalStatus;
    @FXML
    TextField rentText;

    public void init(Manager manager){
        this.manager=manager;
    }

    public void refresh() {
        SqlSession session = MybatisUtil.getSession();
        equipmentDao = session.getMapper(EquipmentDao.class);
        equipmentTypeDao = session.getMapper(EquipmentTypeDao.class);
        factoryDao=session.getMapper(FactoryDao.class);
        ObservableList<EquipmentTemp> list = FXCollections.observableArrayList();

        List<Equipment> equipmentList = equipmentDao.findByInfo("工厂私有");

        for (Equipment equipment : equipmentList) {
            EquipmentType equipmentType = equipmentTypeDao.findByTypeId(equipment.getEtId());
            Factory factory = factoryDao.findByFactoryId(equipment.getFid());
            list.add(new EquipmentTemp(equipment.getEtId(), equipment.getOid(),equipment.getFid(), equipment.getEquipmentId(),
                    equipment.getEquipmentName(), equipment.getEquipmentSpecification(), equipment.getEquipmentStatus(),
                    equipment.getRentalStatus(), equipmentType.getTypeName(), factory.getFactoryName()));
        }


        equipmentName.setCellValueFactory(new PropertyValueFactory<>("equipmentName"));
        equipmentTypeName.setCellValueFactory(new PropertyValueFactory<>("equipmentTypeName"));
        equipmentSpecification.setCellValueFactory(new PropertyValueFactory<>("equipmentSpecification"));
        equipmentStatus.setCellValueFactory(new PropertyValueFactory<>("equipmentStatus"));
        rentalStatus.setCellValueFactory(new PropertyValueFactory<>("rentalStatus"));

        equipmentTable.setItems(list);

        //提交事务
        session.commit();
    }

    public void rentEquipment(){
        try {
            SqlSession session = MybatisUtil.getSession();
            equipmentDao = session.getMapper(EquipmentDao.class);
            Equipment equipment = equipmentDao.findByEquipmentName(rentText.getText());
            equipment.setRentalStatus("工厂租借");
            List<Factory> factoryList = factoryDao.findByUid(manager.getUserId());
            if(factoryList.size()==1){
                Factory factory = factoryList.get(0);
                equipment.setFid(factory.getFactoryId());
            }
            equipmentDao.update(equipment);
            session.commit();
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("租用成功");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("租用失败");
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }
}
