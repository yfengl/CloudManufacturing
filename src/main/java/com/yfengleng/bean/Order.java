package com.yfengleng.bean;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    private int orderId;
    private int pid;
    private int productAmount;
    private Date accomplishDeadline;
    private Date tenderDeadline;
    private int consigneeId;
    private String orderStatus;

    public Order(int pid, int productAmount, Date accomplishDeadline, Date tenderDeadline, int consigneeId, String orderStatus) {
        this.pid = pid;
        this.productAmount = productAmount;
        this.accomplishDeadline = accomplishDeadline;
        this.tenderDeadline = tenderDeadline;
        this.consigneeId = consigneeId;
        this.orderStatus = orderStatus;
    }

    public Order(int orderId, int pid, int productAmount, Date accomplishDeadline, Date tenderDeadline, int consigneeId, String orderStatus) {
        this.orderId = orderId;
        this.pid = pid;
        this.productAmount = productAmount;
        this.accomplishDeadline = accomplishDeadline;
        this.tenderDeadline = tenderDeadline;
        this.consigneeId = consigneeId;
        this.orderStatus = orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public Date getAccomplishDeadline() {
        return accomplishDeadline;
    }

    public void setAccomplishDeadline(Date accomplishDeadline) {
        this.accomplishDeadline = accomplishDeadline;
    }

    public Date getTenderDeadline() {
        return tenderDeadline;
    }

    public void setTenderDeadline(Date tenderDeadline) {
        this.tenderDeadline = tenderDeadline;
    }

    public int getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(int consigneeId) {
        this.consigneeId = consigneeId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", pid=" + pid +
                ", productAmount=" + productAmount +
                ", accomplishDeadline=" + accomplishDeadline +
                ", tenderDeadline=" + tenderDeadline +
                ", consigneeId=" + consigneeId +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
