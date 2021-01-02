package com.yfengleng.controller;

import com.yfengleng.bean.Factory;
import com.yfengleng.dao.FactoryDao;
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

public class AdminFactoryController implements Initializable {

    private FactoryDao factoryDao;

    @FXML
    TableView<Factory> factoryTable;
    @FXML
    TableColumn<Factory, String> name;
    @FXML
    TableColumn<Factory, String> info;
    @FXML
    TableColumn<Factory, String> status;
    @FXML
    TextField searchText;

    public void refresh() {
        SqlSession session = MybatisUtil.getSession();
        factoryDao = session.getMapper(FactoryDao.class);
        ObservableList<Factory> list = FXCollections.observableArrayList();
        List<Factory> factoryList = factoryDao.findAll();

        list.addAll(factoryList);

        name.setCellValueFactory(new PropertyValueFactory<>("factoryName"));
        info.setCellValueFactory(new PropertyValueFactory<>("factoryInfo"));
        status.setCellValueFactory(new PropertyValueFactory<>("factoryStatus"));

        factoryTable.setItems(list);

        //提交事务
        session.commit();
    }

    public void nFactory() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/newFactory.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 340);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("新建工厂");
        stage.show();
    }

    public void rFactory() {
        Factory selectedItem = factoryTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        factoryDao = session.getMapper(FactoryDao.class);
        factoryDao.deleteByFactoryName(selectedItem.getFactoryName());
        refresh();
    }

    public void mFactory() {
        factoryTable.setEditable(true);

        SqlSession session = MybatisUtil.getSession();
        factoryDao = session.getMapper(FactoryDao.class);

        name.setCellFactory(TextFieldTableCell.<Factory>forTableColumn());
        name.setOnEditCommit(
                (TableColumn.CellEditEvent<Factory, String> t) -> {
                    Factory factory = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    factory.setFactoryName(t.getNewValue());
                    factoryDao.update(factory);
                });

        info.setCellFactory(TextFieldTableCell.forTableColumn());
        info.setOnEditCommit(
                (TableColumn.CellEditEvent<Factory, String> t) -> {
                    Factory factory = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    factory.setFactoryInfo(t.getNewValue());
                    factoryDao.update(factory);
                });
        status.setCellFactory(TextFieldTableCell.forTableColumn());
        status.setOnEditCommit(
                (TableColumn.CellEditEvent<Factory, String> t) -> {
                    Factory factory = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    factory.setFactoryStatus(t.getNewValue());
                    factoryDao.update(factory);
                });

        //提交事务
        session.commit();

    }

    public void sFactory() {
        SqlSession session = MybatisUtil.getSession();
        factoryDao = session.getMapper(FactoryDao.class);
        ObservableList<Factory> list = FXCollections.observableArrayList();
        List<Factory> factoryList = factoryDao.findByInfo(searchText.getText());

        list.addAll(factoryList);

        name.setCellValueFactory(new PropertyValueFactory<>("factoryName"));
        info.setCellValueFactory(new PropertyValueFactory<>("factoryInfo"));
        status.setCellValueFactory(new PropertyValueFactory<>("factoryStatus"));

        factoryTable.setItems(list);

        //提交事务
        session.commit();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }
}
