package com.yfengleng.controller;

import com.yfengleng.MyApplication;
import com.yfengleng.bean.Administrator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorController implements Initializable {

    private MyApplication app;
    Administrator administrator=null;

    @FXML
    AnchorPane table;


    public void setStage(MyApplication app) {
        this.app = app;
    }

    public void init(Administrator administrator){
        this.administrator=administrator;
    }

    public void closeStage(){
        app.close();
    }

    public void userAdmin() throws IOException {
        table.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/admin/adminUser.fxml"));
        Parent root = loader.load();
        table.getChildren().add(root);
    }

    public void factoryAdmin() throws IOException {
        table.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/admin/adminFactory.fxml"));
        Parent root = loader.load();
        table.getChildren().add(root);
    }

    public void productTypeAdmin() throws IOException {
        table.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/admin/adminProductType.fxml"));
        Parent root = loader.load();
        table.getChildren().add(root);
    }

    public void productAdmin() throws IOException {
        table.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/admin/adminProduct.fxml"));
        Parent root = loader.load();
        table.getChildren().add(root);
    }

    public void equipmentAdmin() throws IOException {
        table.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/admin/adminEquipment.fxml"));
        Parent root = loader.load();
        table.getChildren().add(root);
    }

    public void equipmentTypeAdmin() throws IOException {
        table.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/admin/adminEquipmentType.fxml"));
        Parent root = loader.load();
        table.getChildren().add(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
