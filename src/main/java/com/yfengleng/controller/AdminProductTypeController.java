package com.yfengleng.controller;

import com.yfengleng.bean.ProductType;
import com.yfengleng.dao.ProductTypeDao;
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

public class AdminProductTypeController implements Initializable {

    private ProductTypeDao productTypeDao;

    @FXML
    TableView<ProductType> typeTable;
    @FXML
    TableColumn<ProductType, String> typeId;
    @FXML
    TableColumn<ProductType, String> typeName;
    @FXML
    TextField searchText;

    public void refresh() {
        SqlSession session = MybatisUtil.getSession();
        productTypeDao = session.getMapper(ProductTypeDao.class);
        ObservableList<ProductType> list = FXCollections.observableArrayList();
        List<ProductType> productTypeList= productTypeDao.findAll();

        list.addAll(productTypeList);

        typeId.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        typeName.setCellValueFactory(new PropertyValueFactory<>("typeName"));

        typeTable.setItems(list);

        //提交事务
        session.commit();
    }

    public void nType() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/newProductType.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 340);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("新建产品类别");
        stage.show();
    }

    public void rType() throws IOException {
        ProductType selectedItem = typeTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        productTypeDao = session.getMapper(ProductTypeDao.class);
        productTypeDao.deleteByTypeId(selectedItem.getTypeId());
        refresh();
    }

    public void mType() {
        typeTable.setEditable(true);

        SqlSession session = MybatisUtil.getSession();
        productTypeDao = session.getMapper(ProductTypeDao.class);

        typeName.setCellFactory(TextFieldTableCell.<ProductType>forTableColumn());
        typeName.setOnEditCommit(
                (TableColumn.CellEditEvent<ProductType, String> t) -> {
                    ProductType productType = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    productType.setTypeName(t.getNewValue());
                    productTypeDao.update(productType);
                });

        //提交事务
        session.commit();

    }

    public void sType() {
        SqlSession session = MybatisUtil.getSession();
        productTypeDao = session.getMapper(ProductTypeDao.class);
        ObservableList<ProductType> list = FXCollections.observableArrayList();
        List<ProductType> productTypeList = productTypeDao.findByTypeName(searchText.getText());

        list.addAll(productTypeList);

        typeId.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        typeName.setCellValueFactory(new PropertyValueFactory<>("typeName"));

        typeTable.setItems(list);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }
}
