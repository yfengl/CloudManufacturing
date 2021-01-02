package com.yfengleng.controller;

import com.yfengleng.MyApplication;
import com.yfengleng.bean.Administrator;
import com.yfengleng.bean.Consignee;
import com.yfengleng.bean.Manager;
import com.yfengleng.dao.AdministratorDao;
import com.yfengleng.dao.ConsigneeDao;
import com.yfengleng.dao.ManagerDao;
import com.yfengleng.util.Md5SaltTool;
import com.yfengleng.util.MybatisUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private MyApplication app;
    private AdministratorDao administratorDao;
    private ConsigneeDao consigneeDao;
    private ManagerDao managerDao;
    ObservableList<Node> children;

    @FXML
    AnchorPane loginContain,loginPanel;
    @FXML
    Button close, submit;
    @FXML
    TextField account;
    @FXML
    PasswordField password;

    public void setStage(MyApplication app) {
        children= loginPanel.getChildren();
        this.app = app;
    }

    public void closeStage() {
        app.close();
    }

    public void login() throws IOException, NoSuchAlgorithmException {
        SqlSession session = MybatisUtil.getSession();
        administratorDao = session.getMapper(AdministratorDao.class);
        managerDao = session.getMapper(ManagerDao.class);
        consigneeDao = session.getMapper(ConsigneeDao.class);

        Administrator administrator = administratorDao.login(account.getText());
        Manager manager = managerDao.login(account.getText());
        Consignee consignee = consigneeDao.login(account.getText());

        if(administrator!=null&&Md5SaltTool.validPassword
                (password.getText(),administrator.getPassword())){
            app.goToAdministrator(administrator);
        }else if(manager!=null&&Md5SaltTool.validPassword
                (password.getText(),manager.getPassword())){
            app.goToManager(manager);
        }else if(consignee!=null&&Md5SaltTool.validPassword
                (password.getText(),consignee.getPassword())){
            app.goToConsignee(consignee);
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("账号或密码错误");
            alert.show();
        }
        //提交事务
        session.commit();
    }

    public void findPassword() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/login/forgetPassword.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 340);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("修改密码");
        stage.show();
    }

    public void signUp() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/newUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 650, 340);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("注册");
        stage.show();
    }

    public void back() throws IOException {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
