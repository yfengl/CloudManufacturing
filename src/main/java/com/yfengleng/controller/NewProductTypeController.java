package com.yfengleng.controller;

import com.yfengleng.bean.ProductType;
import com.yfengleng.dao.ProductTypeDao;
import com.yfengleng.util.MybatisUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.ibatis.session.SqlSession;

import java.net.URL;
import java.util.ResourceBundle;

public class NewProductTypeController implements Initializable {

    private ProductTypeDao productTypeDao;

    @FXML
    TextField typeName;

    public void newType() {
        SqlSession session = MybatisUtil.getSession();
        productTypeDao=session.getMapper(ProductTypeDao.class);

        try {
            productTypeDao.insert(new ProductType(typeName.getText()));
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("添加成功");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("新增失败");
            alert.show();
        }


        //提交事务
        session.commit();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
