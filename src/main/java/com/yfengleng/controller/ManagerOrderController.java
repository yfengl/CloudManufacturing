package com.yfengleng.controller;

import com.yfengleng.bean.*;
import com.yfengleng.controller.temp.OrderTemp;
import com.yfengleng.dao.ConsigneeDao;
import com.yfengleng.dao.OrderDao;
import com.yfengleng.dao.ProductDao;
import com.yfengleng.util.MybatisUtil;
import com.yfengleng.util.TimeUtil;
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
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerOrderController implements Initializable {

    private Manager manager;
    private OrderDao orderDao;
    private ConsigneeDao consigneeDao;
    private ProductDao productDao;

    @FXML
    TableView<OrderTemp> orderTable;
    @FXML
    TableColumn<OrderTemp, String> productName, amount, accomplishDeadline, tenderDeadline, orderStatus;
    @FXML
    TextField searchText;

    public void init(Manager manager) {
        this.manager= manager;
        refresh();
    }

    public void refresh() {
        SqlSession session = MybatisUtil.getSession();
        orderDao = session.getMapper(OrderDao.class);
        consigneeDao = session.getMapper(ConsigneeDao.class);
        productDao = session.getMapper(ProductDao.class);
        ObservableList<OrderTemp> list = FXCollections.observableArrayList();

        List<Order> orderList = orderDao.findAllPublishedOrder();


        for (Order order : orderList) {
            Product product = productDao.findByProductId(order.getPid());
            OrderTemp orderTemp = new OrderTemp(order.getOrderId(), order.getPid(), String.valueOf(order.getProductAmount()),
                    TimeUtil.getLongTimeAsString(order.getAccomplishDeadline()), TimeUtil.getLongTimeAsString(order.getTenderDeadline()), order.getConsigneeId(),
                    order.getOrderStatus(), product.getProductName());
            list.add(orderTemp);
        }

        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        amount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        accomplishDeadline.setCellValueFactory(new PropertyValueFactory<>("accomplishDeadline"));
        tenderDeadline.setCellValueFactory(new PropertyValueFactory<>("tenderDeadline"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        orderTable.setItems(list);

        //提交事务
        session.commit();
    }


    public void sOrder() {
        SqlSession session = MybatisUtil.getSession();
        orderDao = session.getMapper(OrderDao.class);
        consigneeDao = session.getMapper(ConsigneeDao.class);
        productDao = session.getMapper(ProductDao.class);
        ObservableList<OrderTemp> list = FXCollections.observableArrayList();

        List<Order> orderList = orderDao.findAllPublishedOrder();
        String reges = ".*" + searchText.getText() + ".*";

        for (Order order : orderList) {
            Product product = productDao.findByProductId(order.getPid());
            if(product.getProductName().matches(reges)){
                OrderTemp orderTemp = new OrderTemp(order.getOrderId(), order.getPid(), String.valueOf(order.getProductAmount()),
                        TimeUtil.getLongTimeAsString(order.getAccomplishDeadline()), TimeUtil.getLongTimeAsString(order.getTenderDeadline()), order.getConsigneeId(),
                        order.getOrderStatus(), product.getProductName());
                list.add(orderTemp);
            }
        }

        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        amount.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        accomplishDeadline.setCellValueFactory(new PropertyValueFactory<>("accomplishDeadline"));
        tenderDeadline.setCellValueFactory(new PropertyValueFactory<>("tenderDeadline"));
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        orderTable.setItems(list);

        //提交事务
        session.commit();
    }

    public void tender() throws IOException {
        OrderTemp selectedItem = orderTable.getSelectionModel().getSelectedItem();
        if(selectedItem.getOrderStatus().equals("已发布")){
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/tender.fxml"));
            Parent root = loader.load();
            TenderController controller = loader.getController();
            controller.init(selectedItem,manager);
            Scene scene = new Scene(root, 600, 340);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("投标");
            stage.show();
        }
    }

    public void accomplish(){
        OrderTemp selectedItem = orderTable.getSelectionModel().getSelectedItem();
        if(selectedItem.getOrderStatus().equals("已中标")){
            SqlSession session = MybatisUtil.getSession();
            orderDao=session.getMapper(OrderDao.class);
            Order order = orderDao.findByOrderId(selectedItem.getOrderId());
            order.setOrderStatus("已发货");
            orderDao.update(order);
            session.commit();
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("订单完成，发货成功");
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
