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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyFactoryController implements Initializable {

    private Manager manager;
    private List<Equipment> equipmentList=new ArrayList<>();

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
    TextField searchText;

    public void init(Manager manager){
        this.manager=manager;
        refresh();
    }

   public void refresh() {
        SqlSession session = MybatisUtil.getSession();
        equipmentDao = session.getMapper(EquipmentDao.class);
        equipmentTypeDao = session.getMapper(EquipmentTypeDao.class);
        factoryDao = session.getMapper(FactoryDao.class);
        ObservableList<EquipmentTemp> list = FXCollections.observableArrayList();

        List<Factory> factoryList = factoryDao.findByUid(manager.getUserId());
        for(Factory factory:factoryList){
            List<Equipment> eList = equipmentDao.findByFid(factory.getFactoryId());
            equipmentList.addAll(eList);
        }

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

    public void sEquipment() {
        SqlSession session = MybatisUtil.getSession();
        equipmentDao = session.getMapper(EquipmentDao.class);
        equipmentTypeDao = session.getMapper(EquipmentTypeDao.class);
        factoryDao = session.getMapper(FactoryDao.class);
        ObservableList<EquipmentTemp> list = FXCollections.observableArrayList();

        List<Factory> factoryList = factoryDao.findByUid(manager.getUserId());
        List<Equipment> equipmentList = new ArrayList<>();
        for(Factory factory:factoryList){
            List<Equipment> eList = equipmentDao.findByInfoFid(String.valueOf(factory.getFactoryId()),searchText.getText());
            equipmentList.addAll(eList);
        }
        for (Equipment equipment : equipmentList) {
            EquipmentType equipmentType = equipmentTypeDao.findByTypeId(equipment.getEtId());
            Factory factory = factoryDao.findByFactoryId(equipment.getFid());
            list.add(new EquipmentTemp(equipment.getEtId(),equipment.getOid(), equipment.getFid(), equipment.getEquipmentId()
                    , equipment.getEquipmentName(), equipment.getEquipmentSpecification(), equipment.getEquipmentStatus(),
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

    public void nEquipment() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/newEquipment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 340);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("新建设备");
        stage.show();
    }

    public void rEquipment()  {
        EquipmentTemp selectedItem = equipmentTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        equipmentDao.deleteByEquipmentId(selectedItem.getEquipmentId());
        refresh();
    }

    public void mEquipment() {
        equipmentTable.setEditable(true);

        SqlSession session = MybatisUtil.getSession();
        equipmentDao = session.getMapper(EquipmentDao.class);
        equipmentTypeDao=session.getMapper(EquipmentTypeDao.class);

        equipmentName.setCellFactory(TextFieldTableCell.<EquipmentTemp>forTableColumn());
        equipmentName.setOnEditCommit(
                (TableColumn.CellEditEvent<EquipmentTemp, String> t) -> {
                    EquipmentTemp equipmentTemp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    Equipment equipment = new Equipment(equipmentTemp.getEtid(), t.getNewValue(), equipmentTemp.getEquipmentSpecification());
                    equipment.setFid(equipmentTemp.getFid());
                    equipment.setRentalStatus(equipmentTemp.getRentalStatus());
                    equipment.setEquipmentStatus(equipmentTemp.getEquipmentStatus());
                    equipment.setOid(equipmentTemp.getOid());
                    equipment.setEquipmentId(equipmentTemp.getEquipmentId());

                    equipmentDao.update(equipment);
                });
        equipmentSpecification.setCellFactory(TextFieldTableCell.forTableColumn());
        equipmentSpecification.setOnEditCommit(
                (TableColumn.CellEditEvent<EquipmentTemp, String> t) -> {
                    EquipmentTemp equipmentTemp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    Equipment equipment = new Equipment(equipmentTemp.getEtid(), equipmentTemp.getEquipmentName(), t.getNewValue());
                    equipment.setFid(equipmentTemp.getFid());
                    equipment.setRentalStatus(equipmentTemp.getRentalStatus());
                    equipment.setEquipmentStatus(equipmentTemp.getEquipmentStatus());
                    equipment.setOid(equipmentTemp.getOid());
                    equipment.setEquipmentId(equipmentTemp.getEquipmentId());

                    equipmentDao.update(equipment);
                });
        equipmentTypeName.setCellFactory(TextFieldTableCell.forTableColumn());
        equipmentTypeName.setOnEditCommit(
                (TableColumn.CellEditEvent<EquipmentTemp, String> t) -> {
                    EquipmentTemp equipmentTemp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    List<EquipmentType> typeList = equipmentTypeDao.findByTypeName(t.getNewValue());
                    if (typeList.size() == 1) {
                        Equipment equipment = new Equipment(typeList.get(0).getTypeId(), equipmentTemp.getEquipmentName(), equipmentTemp.getEquipmentSpecification());
                        equipment.setFid(equipmentTemp.getFid());
                        equipment.setRentalStatus(equipmentTemp.getRentalStatus());
                        equipment.setEquipmentStatus(equipmentTemp.getEquipmentStatus());
                        equipment.setOid(equipmentTemp.getOid());
                        equipment.setEquipmentId(equipmentTemp.getEquipmentId());

                        equipmentDao.update(equipment);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("类型名称不存在");
                        alert.show();
                    }

                });

        //提交事务
        session.commit();
    }

    public void onClose() throws IOException {
        EquipmentTemp selectedItem = equipmentTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        Equipment equipment=new Equipment(selectedItem.getEtid(),selectedItem.getEquipmentName(),selectedItem.getEquipmentSpecification());
        equipment.setFid(selectedItem.getFid());
        equipment.setOid(selectedItem.getOid());
        equipment.setEquipmentId(selectedItem.getEquipmentId());
        equipment.setRentalStatus(selectedItem.getRentalStatus());
        if(selectedItem.getEquipmentStatus().equals("闲置")){
            equipment.setEquipmentStatus("关机");
        }else if(selectedItem.getEquipmentStatus().equals("关机")){
            equipment.setEquipmentStatus("闲置");
        }

        equipmentDao.update(equipment);
        session.commit();
        refresh();
    }

    public void rent() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/rent.fxml"));
        Parent root = loader.load();
        RentController controller = loader.getController();
        controller.init(this.manager);
        Scene scene = new Scene(root, 800, 450);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("租借");
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
