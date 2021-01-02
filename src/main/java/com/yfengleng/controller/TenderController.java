package com.yfengleng.controller;

import com.yfengleng.bean.Manager;
import com.yfengleng.bean.OrderRecord;
import com.yfengleng.controller.temp.OrderTemp;
import com.yfengleng.dao.OrderRecordDao;
import com.yfengleng.util.MybatisUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.ibatis.session.SqlSession;

public class TenderController {

    private OrderTemp orderTemp;
    private Manager manager;

    @FXML
    TextField tenderPrice;

    public void init(OrderTemp orderTemp,Manager manager){
        this.manager=manager;
        this.orderTemp=orderTemp;
    }

    public void tenderOrder(){
        SqlSession session = MybatisUtil.getSession();
        OrderRecordDao orderRecordDao = session.getMapper(OrderRecordDao.class);
        orderRecordDao.insert(new OrderRecord(orderTemp.getOrderId(),Integer.parseInt(tenderPrice.getText()),manager.getUserId()));
        session.commit();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("投标成功");
        alert.show();
    }
}
