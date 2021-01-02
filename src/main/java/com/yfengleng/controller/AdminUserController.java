package com.yfengleng.controller;

import com.yfengleng.bean.Consignee;
import com.yfengleng.bean.Manager;
import com.yfengleng.bean.User;
import com.yfengleng.dao.ConsigneeDao;
import com.yfengleng.dao.ManagerDao;
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


public class AdminUserController implements Initializable {

    private ManagerDao managerDao;
    private ConsigneeDao consigneeDao;

    @FXML
    TableView<User> userTable;
    @FXML
    TableColumn<User, String> name;
    @FXML
    TableColumn<User, String> account;
    @FXML
    TableColumn<User, String> type;
    @FXML
    TableColumn<User, String> email;
    @FXML
    TableColumn<User, String> tel;
    @FXML
    TextField searchText;

    public void refresh() {
        SqlSession session = MybatisUtil.getSession();
        managerDao = session.getMapper(ManagerDao.class);
        consigneeDao = session.getMapper(ConsigneeDao.class);
        ObservableList<User> list = FXCollections.observableArrayList();
        List<Manager> managerList = managerDao.findAll();
        List<Consignee> consigneeList = consigneeDao.findAll();

        list.addAll(managerList);
        list.addAll(consigneeList);

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        account.setCellValueFactory(new PropertyValueFactory<>("account"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));

        userTable.setItems(list);

        //提交事务
        session.commit();

    }

    public void nUser() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/newUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 340);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("新建用户");
        stage.show();
    }

    public void rUser()  {
        User selectedItem = userTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        managerDao = session.getMapper(ManagerDao.class);
        consigneeDao=session.getMapper(ConsigneeDao.class);
        managerDao.deleteByAccount(selectedItem.getAccount());
        consigneeDao.deleteByAccount(selectedItem.getAccount());
        refresh();
    }

    public void mUser() {
        userTable.setEditable(true);
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    User user = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    if(user.getType().equals("工厂管理员")){
                        user.setName(t.getNewValue());
                        managerDao.update((Manager) user);
                    }else if(user.getType().equals("经销商")){
                        consigneeDao.update((Consignee) user);
                    }

                });
        account.setCellFactory(TextFieldTableCell.forTableColumn());
        account.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    User user = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    if(user.getType().equals("工厂管理员")){
                        user.setAccount(t.getNewValue());
                        managerDao.update((Manager) user);
                    }else if(user.getType().equals("经销商")){
                        consigneeDao.update((Consignee) user);
                    }

                });
        email.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    User user = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    if(user.getType().equals("工厂管理员")){
                        user.setEmail(t.getNewValue());
                        managerDao.update((Manager) user);
                    }else if(user.getType().equals("经销商")){
                        consigneeDao.update((Consignee) user);
                    }

                });
        tel.setCellFactory(TextFieldTableCell.forTableColumn());
        tel.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    User user = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    if(user.getType().equals("工厂管理员")){
                        user.setTel(t.getNewValue());
                        managerDao.update((Manager) user);
                    }else if(user.getType().equals("经销商")){
                        consigneeDao.update((Consignee) user);
                    }

                });

    }

    public void sUser() {
        SqlSession session = MybatisUtil.getSession();
        managerDao = session.getMapper(ManagerDao.class);
        consigneeDao = session.getMapper(ConsigneeDao.class);
        ObservableList<User> list = FXCollections.observableArrayList();
        List<Manager> managerList = managerDao.findByInfo(searchText.getText());
        List<Consignee> consigneeList = consigneeDao.findByInfo(searchText.getText());

        list.addAll(managerList);
        list.addAll(consigneeList);

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        account.setCellValueFactory(new PropertyValueFactory<>("account"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));

        userTable.setItems(list);

        //提交事务
        session.commit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }
}
