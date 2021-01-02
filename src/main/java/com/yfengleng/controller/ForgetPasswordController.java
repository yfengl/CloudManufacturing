package com.yfengleng.controller;

import com.yfengleng.bean.Administrator;
import com.yfengleng.bean.Consignee;
import com.yfengleng.bean.Manager;
import com.yfengleng.dao.ConsigneeDao;
import com.yfengleng.dao.ManagerDao;
import com.yfengleng.util.EmailUtil;
import com.yfengleng.util.Md5SaltTool;
import com.yfengleng.util.MybatisUtil;
import com.yfengleng.util.RandomUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.apache.ibatis.session.SqlSession;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.zip.DataFormatException;

public class ForgetPasswordController {

    private Manager manager;
    private Consignee consignee;

    private ManagerDao managerDao;
    private ConsigneeDao consigneeDao;
    private final int REAL_CODE= RandomUtil.random(1000,9999);

    @FXML
    TextField account,email,code;
    @FXML
    PasswordField newPassword,newPasswords;

    @FXML
    public void modifyPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(code.getText().equals(String.valueOf(REAL_CODE))){
            if(newPassword.getText()!=null){
                if(newPassword.getText().equals(newPasswords.getText())){
                    if(manager!=null){
                        manager.setPassword(Md5SaltTool.getEncryptedPwd(newPassword.getText()));
                        managerDao.update(manager);
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("密码修改成功，您的新密码为 "+newPassword.getText());
                        alert.show();
                    }else if(consignee!=null){
                        consignee.setPassword(Md5SaltTool.getEncryptedPwd(newPassword.getText()));
                        consigneeDao.update(consignee);
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("密码修改成功，您的新密码为 "+newPassword.getText());
                        alert.show();
                    }
                }
                else{
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("密码不一致！");
                    alert.show();
                }
            }
            else{
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("密码不能为空！");
                alert.show();
            }
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("验证码错误");
            alert.show();
        }
    }

    public void send() throws DataFormatException, MessagingException {
        SqlSession session = MybatisUtil.getSession();
        managerDao=session.getMapper(ManagerDao.class);
        consigneeDao=session.getMapper(ConsigneeDao.class);

        List<Manager> managerList = managerDao.findByInfo(account.getText());
        List<Consignee> consigneeList = consigneeDao.findByInfo(account.getText());

        if(managerList.size()==1){
            manager=managerList.get(0);
            if(email.getText().equals(managerList.get(0).getEmail())){
                EmailUtil.send(email.getText(),"智能工厂云工厂提醒您不要将此验证码告诉他人\n"+"验证码："+REAL_CODE);
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("邮箱错误");
                alert.show();
            }
        }else if(consigneeList.size()==1){
            consignee=consigneeList.get(0);
            if(email.getText().equals(consigneeList.get(0).getEmail())){
                EmailUtil.send(email.getText(),"智能工厂云工厂提醒您不要将此验证码告诉他人\n"+"验证码："+REAL_CODE);
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("邮箱错误");
                alert.show();
            }
        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("账号不存在");
            alert.show();
        }
    }
}
