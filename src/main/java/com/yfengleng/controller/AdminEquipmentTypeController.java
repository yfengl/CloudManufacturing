package com.yfengleng.controller;

import com.yfengleng.bean.EquipmentType;
import com.yfengleng.dao.EquipmentTypeDao;
import com.yfengleng.util.MybatisUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.List;
import java.util.ResourceBundle;

public class AdminEquipmentTypeController implements Initializable {

    private EquipmentTypeDao equipmentTypeDao;

    @FXML
    TableView<EquipmentType> typeTable;
    @FXML
    TableColumn<EquipmentType, String> typeId;
    @FXML
    TableColumn<EquipmentType, String> typeName;
    @FXML
    TextField searchText;

    public void refresh() {
        SqlSession session = MybatisUtil.getSession();
        equipmentTypeDao = session.getMapper(EquipmentTypeDao.class);
        ObservableList<EquipmentType> list = FXCollections.observableArrayList();
        List<EquipmentType> equipmentTypeList = equipmentTypeDao.findAll();

        list.addAll(equipmentTypeList);

        typeId.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        typeName.setCellValueFactory(new PropertyValueFactory<>("typeName"));

        typeTable.setItems(list);

        //提交事务
        session.commit();
    }

    public void nType() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/newEquipmentType.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 340);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("新建产品类别");
        stage.show();
    }

    public void rType(){
        EquipmentType selectedItem = typeTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        equipmentTypeDao=session.getMapper(EquipmentTypeDao.class);
        equipmentTypeDao.deleteByTypeId(selectedItem.getTypeId());
        refresh();
    }

    public void mType() {
        typeTable.setEditable(true);

        SqlSession session = MybatisUtil.getSession();
        equipmentTypeDao = session.getMapper(EquipmentTypeDao.class);

        typeName.setCellFactory(TextFieldTableCell.<EquipmentType>forTableColumn());
        typeName.setOnEditCommit(
                (TableColumn.CellEditEvent<EquipmentType, String> t) -> {
                    EquipmentType equipmentType = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    equipmentType.setTypeName(t.getNewValue());
                    equipmentTypeDao.update(equipmentType);
                });

        //提交事务
        session.commit();

    }

    public void sType() {
        SqlSession session = MybatisUtil.getSession();
        equipmentTypeDao = session.getMapper(EquipmentTypeDao.class);
        ObservableList<EquipmentType> list = FXCollections.observableArrayList();
        List<EquipmentType> equipmentTypeList = equipmentTypeDao.findByTypeName(searchText.getText());

        list.addAll(equipmentTypeList);

        typeId.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        typeName.setCellValueFactory(new PropertyValueFactory<>("typeName"));

        typeTable.setItems(list);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }
}
