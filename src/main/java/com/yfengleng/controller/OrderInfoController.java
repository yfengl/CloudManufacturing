package com.yfengleng.controller;

import com.yfengleng.bean.Order;
import com.yfengleng.bean.OrderRecord;
import com.yfengleng.controller.temp.OrderTemp;
import com.yfengleng.dao.OrderDao;
import com.yfengleng.dao.OrderRecordDao;
import com.yfengleng.util.MybatisUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.ibatis.session.SqlSession;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderInfoController implements Initializable {

    private OrderTemp orderTemp;
    private OrderRecordDao orderRecordDao;
    private OrderDao orderDao;

    @FXML
    TableView<OrderRecord> orderTable;
    @FXML
    TableColumn<OrderRecord,String> managerId,price;

    public void init(OrderTemp orderTemp){
        this.orderTemp=orderTemp;
        refresh();
    }

    public void refresh(){
        SqlSession session = MybatisUtil.getSession();
        orderRecordDao=session.getMapper(OrderRecordDao.class);
        ObservableList<OrderRecord> list = FXCollections.observableArrayList();

        List<OrderRecord> orderRecordList = orderRecordDao.findByOid(orderTemp.getOrderId());

        list.addAll(orderRecordList);

        managerId.setCellValueFactory(new PropertyValueFactory<>("managerId"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderTable.setItems(list);

        //提交事务
        session.commit();
    }

    public void sOrder(){

    }

    public void selectOrder(){
        OrderRecord selectedItem = orderTable.getSelectionModel().getSelectedItem();
        SqlSession session = MybatisUtil.getSession();
        orderDao=session.getMapper(OrderDao.class);
        Order order = orderDao.findByOrderId(selectedItem.getOid());
        order.setOrderStatus("已中标");
        orderDao.update(order);
        session.commit();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("选标成功");
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
