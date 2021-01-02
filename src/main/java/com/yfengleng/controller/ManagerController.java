package com.yfengleng.controller;

import com.yfengleng.MyApplication;
import com.yfengleng.bean.Manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    private MyApplication app;
    Manager manager=null;

    @FXML
    Label info;
    @FXML
    AnchorPane table;

    public void setStage(MyApplication app) {
        this.app = app;
    }

    public void init(Manager manager){
        this.manager=manager;
        info.setText("你好，"+manager.getName());
    }

    public void closeStage(){
        app.close();
    }

    public void myFactory() throws IOException {
        table.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/myFactory.fxml"));
        Parent root = loader.load();
        MyFactoryController controller = loader.getController();
        controller.init(manager);
        table.getChildren().add(root);
    }

    public void orderAdmin() throws IOException {
        table.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/managerOrder.fxml"));
        Parent root = loader.load();
        ManagerOrderController controller = loader.getController();
        controller.init(manager);
        table.getChildren().add(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
