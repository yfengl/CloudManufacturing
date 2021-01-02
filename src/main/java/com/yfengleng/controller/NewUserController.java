package com.yfengleng.controller;

import com.yfengleng.bean.Consignee;
import com.yfengleng.bean.Manager;
import com.yfengleng.dao.ConsigneeDao;
import com.yfengleng.dao.ManagerDao;
import com.yfengleng.util.Md5SaltTool;
import com.yfengleng.util.MybatisUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import org.apache.ibatis.session.SqlSession;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class NewUserController implements Initializable {

    private ManagerDao managerDao;
    private ConsigneeDao consigneeDao;

    @FXML
    TextField name, account, password, email, tel;
    @FXML
    ToggleButton factoryAdmin, consignee;

    public void newUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SqlSession session = MybatisUtil.getSession();
        managerDao = session.getMapper(ManagerDao.class);
        consigneeDao = session.getMapper(ConsigneeDao.class);

        if (factoryAdmin.isSelected()) {
            managerDao.insert(new Manager(account.getText(),
                    Md5SaltTool.getEncryptedPwd(password.getText()),
                    name.getText(), email.getText(), tel.getText(), "工厂管理员"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("添加成功");
            alert.show();
        } else if (consignee.isSelected()) {
            consigneeDao.insert(new Consignee(account.getText(),
                    Md5SaltTool.getEncryptedPwd(password.getText()),
                    name.getText(), email.getText(), tel.getText(), "经销商", " "));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("添加成功");
            alert.show();
        }
        //提交事务
        session.commit();

}
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
