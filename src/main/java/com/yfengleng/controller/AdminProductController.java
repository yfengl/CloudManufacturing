package com.yfengleng.controller;

import com.yfengleng.bean.Product;
import com.yfengleng.bean.ProductType;
import com.yfengleng.controller.temp.ProductTemp;
import com.yfengleng.dao.ProductDao;
import com.yfengleng.dao.ProductTypeDao;
import com.yfengleng.util.MybatisUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class AdminProductController implements Initializable {

    private ProductDao productDao;
    private ProductTypeDao productTypeDao;

    @FXML
    TableView<ProductTemp> productTable;
    @FXML
    TableColumn<ProductTemp, String> productName;
    @FXML
    TableColumn<ProductTemp, String> productType;
    @FXML
    TableColumn<ProductTemp, String> productInfo;
    @FXML
    TableColumn<ProductTemp, String> productSpecification;
    @FXML
    TextField searchText;

    public void refresh() {
        SqlSession session = MybatisUtil.getSession();
        productDao = session.getMapper(ProductDao.class);
        productTypeDao = session.getMapper(ProductTypeDao.class);
        ObservableList<ProductTemp> list = FXCollections.observableArrayList();

        List<Product> productList = productDao.findAll();
        for (Product product : productList) {
            ProductType productType = productTypeDao.findByTypeId(product.getPtId());
            list.add(new ProductTemp(product.getProductId(),product.getProductName(), product.getProductInfo(), product.getProductSpecification(), productType.getTypeName(),productType.getTypeId()));
        }


        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productInfo.setCellValueFactory(new PropertyValueFactory<>("productInfo"));
        productSpecification.setCellValueFactory(new PropertyValueFactory<>("productSpecification"));
        productType.setCellValueFactory(new PropertyValueFactory<>("productType"));

        productTable.setItems(list);

        //提交事务
        session.commit();
    }


    public void sProduct() {
        SqlSession session = MybatisUtil.getSession();
        productDao = session.getMapper(ProductDao.class);
        ObservableList<ProductTemp> list = FXCollections.observableArrayList();
        List<Product> productList = productDao.findByInfo(searchText.getText());
        for (Product product : productList) {
            ProductType productType = productTypeDao.findByTypeId(product.getPtId());
            list.add(new ProductTemp(product.getProductId(),product.getProductName(), product.getProductInfo(), product.getProductSpecification(), productType.getTypeName(),productType.getTypeId()));
        }


        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productInfo.setCellValueFactory(new PropertyValueFactory<>("productInfo"));
        productSpecification.setCellValueFactory(new PropertyValueFactory<>("productSpecification"));
        productType.setCellValueFactory(new PropertyValueFactory<>("productType"));

        productTable.setItems(list);

        //提交事务
        session.commit();
    }

    public void nProduct() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/newProduct.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 340);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("新建产品");
        stage.show();
    }

    public void rProduct() throws IOException {
        ProductTemp selectedItem = productTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        productDao = session.getMapper(ProductDao.class);
        productDao.deleteByProductId(selectedItem.getProductId());
        refresh();
    }

    public void mProduct() {
        productTable.setEditable(true);

        SqlSession session = MybatisUtil.getSession();
        productDao = session.getMapper(ProductDao.class);

        productName.setCellFactory(TextFieldTableCell.<ProductTemp>forTableColumn());
        productName.setOnEditCommit(
                (TableColumn.CellEditEvent<ProductTemp, String> t) -> {
                    ProductTemp temp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    Product product=new Product(temp.getProductId(),temp.getPtId(),t.getNewValue(),temp.getProductInfo(),temp.getProductSpecification());
                    System.out.println(product);
                    productDao.update(product);
                });
        productInfo.setCellFactory(TextFieldTableCell.forTableColumn());
        productInfo.setOnEditCommit(
                (TableColumn.CellEditEvent<ProductTemp, String> t) -> {
                    ProductTemp temp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    Product product=new Product(temp.getProductId(),temp.getPtId(),temp.getProductName(),t.getNewValue(),temp.getProductSpecification());
                    productDao.update(product);
                });
        productSpecification.setCellFactory(TextFieldTableCell.forTableColumn());
        productSpecification.setOnEditCommit(
                (TableColumn.CellEditEvent<ProductTemp, String> t) -> {
                    ProductTemp temp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    Product product=new Product(temp.getProductId(),temp.getPtId(),temp.getProductName(),temp.getProductInfo(),t.getNewValue());
                    productDao.update(product);
                });
        productType.setCellFactory(TextFieldTableCell.forTableColumn());
        productType.setOnEditCommit(
                (TableColumn.CellEditEvent<ProductTemp, String> t) -> {
                    ProductTemp temp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    List<ProductType> typeList = productTypeDao.findByTypeName(t.getNewValue());
                    if(typeList.size()==1){
                        Product product=new Product(temp.getProductId(),typeList.get(0).getTypeId(),temp.getProductName(),temp.getProductInfo(),temp.getProductSpecification());
                        productDao.update(product);
                    }else{
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("类型名称不存在");
                        alert.show();
                    }

                });

        //提交事务
        session.commit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }
}
