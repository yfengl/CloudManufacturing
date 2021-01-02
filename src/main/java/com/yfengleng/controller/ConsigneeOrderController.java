package com.yfengleng.controller;

import com.yfengleng.bean.*;
import com.yfengleng.controller.temp.OrderTemp;
import com.yfengleng.dao.*;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ConsigneeOrderController implements Initializable {

    private Consignee consignee;
    private OrderDao orderDao;
    private ConsigneeDao consigneeDao;
    private ProductDao productDao;

    @FXML
    TableView<OrderTemp> orderTable;
    @FXML
    TableColumn<OrderTemp, String> productName, amount, accomplishDeadline, tenderDeadline, orderStatus;
    @FXML
    TextField searchText;

    public void init(Consignee consignee) {
        this.consignee = consignee;
        refresh();
    }

    public void refresh() {
        SqlSession session = MybatisUtil.getSession();
        orderDao = session.getMapper(OrderDao.class);
        consigneeDao = session.getMapper(ConsigneeDao.class);
        productDao = session.getMapper(ProductDao.class);
        ObservableList<OrderTemp> list = FXCollections.observableArrayList();

        List<Order> orderList = orderDao.findByConsigneeId(consignee.getUserId());

        for (Order order : orderList) {
            Product product = productDao.findByProductId(order.getPid());
            OrderTemp orderTemp = new OrderTemp(order.getOrderId(), order.getPid(), String.valueOf(order.getProductAmount()),
                    TimeUtil.getLongTimeAsString(order.getAccomplishDeadline()), TimeUtil.getLongTimeAsString(order.getTenderDeadline()), order.getConsigneeId(),
                    order.getOrderStatus(), product.getProductName(), consignee.getName(), consignee.getTel());
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

    public void nOrder() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/newOrder.fxml"));
        Parent root = loader.load();
        NewOrderController controller = loader.getController();
        controller.init(consignee);
        Scene scene = new Scene(root, 600, 340);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("新建订单");
        stage.show();
    }

    public void rOrder() {
        OrderTemp selectedItem = orderTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        orderDao = session.getMapper(OrderDao.class);
        orderDao.deleteByOrderId(selectedItem.getOrderId());
        refresh();
    }

    public void sOrder() {
        SqlSession session = MybatisUtil.getSession();
        orderDao = session.getMapper(OrderDao.class);
        consigneeDao = session.getMapper(ConsigneeDao.class);
        productDao = session.getMapper(ProductDao.class);
        ObservableList<OrderTemp> list = FXCollections.observableArrayList();

        List<Order> orderList = new ArrayList<>();
        for (Order order : orderDao.findByConsigneeId(consignee.getUserId())) {
            Product product = productDao.findByProductId(order.getPid());
            String reges = ".*" + searchText.getText() + ".*";
            if (product.getProductName().matches(reges)) {
                orderList.add(order);
            }
        }

        for (Order order : orderList) {
            Product product = productDao.findByProductId(order.getPid());
            OrderTemp orderTemp = new OrderTemp(order.getOrderId(), order.getPid(), String.valueOf(order.getProductAmount()),
                    TimeUtil.getLongTimeAsString(order.getAccomplishDeadline()), TimeUtil.getLongTimeAsString(order.getTenderDeadline()), order.getConsigneeId(),
                    order.getOrderStatus(), product.getProductName(), consignee.getName(), consignee.getTel());
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

    public void mOrder() {
        orderTable.setEditable(true);

        SqlSession session = MybatisUtil.getSession();
        orderDao = session.getMapper(OrderDao.class);
        productDao = session.getMapper(ProductDao.class);


        productName.setCellFactory(TextFieldTableCell.<OrderTemp>forTableColumn());
        productName.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderTemp, String> t) -> {
                    try {
                        OrderTemp orderTemp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        Product product = productDao.findByProductName(t.getNewValue());
                        if (product != null) {
                            Order order = null;

                            order = new Order(orderTemp.getOrderId(), product.getProductId(), Integer.parseInt(orderTemp.getProductAmount()),
                                    TimeUtil.getDate(orderTemp.getAccomplishDeadline()), TimeUtil.getDate(orderTemp.getTenderDeadline()), orderTemp.getConsigneeId(),
                                    orderTemp.getOrderStatus());


                            orderDao.update(order);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("产品不存在");
                            alert.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    session.commit();
                });
        amount.setCellFactory(TextFieldTableCell.<OrderTemp>forTableColumn());
        amount.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderTemp, String> t) -> {
                    try {
                        OrderTemp orderTemp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        Order order = null;
                        order = new Order(orderTemp.getOrderId(), orderTemp.getPid(), Integer.parseInt(t.getNewValue()),
                                TimeUtil.getDate(orderTemp.getAccomplishDeadline()), TimeUtil.getDate(orderTemp.getTenderDeadline()), orderTemp.getConsigneeId(),
                                orderTemp.getOrderStatus());
                        orderDao.update(order);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    session.commit();
                });
        accomplishDeadline.setCellFactory(TextFieldTableCell.<OrderTemp>forTableColumn());
        accomplishDeadline.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderTemp, String> t) -> {
                    try {
                        OrderTemp orderTemp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        String time = t.getNewValue();

                        Date date = TimeUtil.getDate(time);
                        Order order = new Order(orderTemp.getOrderId(), orderTemp.getPid(), Integer.parseInt(orderTemp.getProductAmount())
                                , date, TimeUtil.getDate(orderTemp.getTenderDeadline()), orderTemp.getConsigneeId(),
                                orderTemp.getOrderStatus());
                        orderDao.update(order);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("时间格式错误，请按照yyyy-MM-dd HH:mm:ss的格式更改时间");
                        alert.show();
                    }
                    session.commit();
                });
        tenderDeadline.setCellFactory(TextFieldTableCell.<OrderTemp>forTableColumn());
        tenderDeadline.setOnEditCommit(
                (TableColumn.CellEditEvent<OrderTemp, String> t) -> {
                    try {
                        OrderTemp orderTemp = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        String time = t.getNewValue();

                        Date date = TimeUtil.getDate(time);
                        Order order = new Order(orderTemp.getOrderId(), orderTemp.getPid(), Integer.parseInt(orderTemp.getProductAmount())
                                , TimeUtil.getDate(orderTemp.getAccomplishDeadline()), date, orderTemp.getConsigneeId(),
                                orderTemp.getOrderStatus());
                        orderDao.update(order);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("时间格式错误，请按照yyyy-MM-dd HH:mm:ss的格式更改时间");
                        alert.show();
                    }
                    session.commit();
                });
    }

    public void publishOrder() {
        OrderTemp selectedItem = orderTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        if(selectedItem.getOrderStatus().equals("未发布")){
            orderDao = session.getMapper(OrderDao.class);
            Order order = orderDao.findByOrderId(selectedItem.getOrderId());
            order.setOrderStatus("已发布");
            orderDao.update(order);
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("只能发布状态为未发布的订单！");
            alert.show();
        }
        refresh();
    }

    public void orderInfo() throws IOException {
        OrderTemp selectedItem = orderTable.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/orderInfo.fxml"));
        Parent root = loader.load();
        OrderInfoController controller = loader.getController();
        controller.init(selectedItem);
        Scene scene = new Scene(root, 1000, 600);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("竞标详情");
        stage.show();
    }

    public void takeOver(){
        OrderTemp selectedItem = orderTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        if(selectedItem.getOrderStatus().equals("已发货")){
            orderDao = session.getMapper(OrderDao.class);
            Order order = orderDao.findByOrderId(selectedItem.getOrderId());
            order.setOrderStatus("已完成");
            orderDao.update(order);
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setContentText("只能收取状态为已收货的订单！");
            alert.show();
        }
        refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
