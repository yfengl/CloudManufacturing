package com.yfengleng.controller;

import com.yfengleng.bean.Product;
import com.yfengleng.bean.ProductType;
import com.yfengleng.dao.ProductDao;
import com.yfengleng.dao.ProductTypeDao;
import com.yfengleng.util.MybatisUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.ibatis.session.SqlSession;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewProductController implements Initializable {

    private ProductDao productDao;
    private ProductTypeDao productTypeDao;

    @FXML
    TextField productName,productSpecification,productTypeName;
    @FXML
    TextArea productInfo;

    public void newFactory() {
        SqlSession session = MybatisUtil.getSession();
        productDao = session.getMapper(ProductDao.class);
        productTypeDao = session.getMapper(ProductTypeDao.class);

        List<ProductType> productTypeList = productTypeDao.findByTypeName(productTypeName.getText());

        if (productTypeList.size()==1){
            int ptId=productTypeList.get(0).getTypeId();
            productDao.insert(new Product(ptId,productName.getText(),productInfo.getText(),productSpecification.getText()));
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("添加成功");
            alert.show();
        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("产品类别设置错误");
            alert.show();
        }

        //提交事务
        session.commit();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
