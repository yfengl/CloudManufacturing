package com.yfengleng.controller.temp;

public class OrderTemp {

    private int orderId;
    private int pid;
    private String productAmount;
    private String accomplishDeadline;
    private String tenderDeadline;
    private int consigneeId;
    private String orderStatus;
    private String productName;
    private String consigneeName;
    private String consigneeTel;

    public OrderTemp(int orderId, int pid, String productAmount, String accomplishDeadline, String tenderDeadline, int consigneeId, String orderStatus, String productName) {
        this.orderId = orderId;
        this.pid = pid;
        this.productAmount = productAmount;
        this.accomplishDeadline = accomplishDeadline;
        this.tenderDeadline = tenderDeadline;
        this.consigneeId = consigneeId;
        this.orderStatus = orderStatus;
        this.productName = productName;
    }

    public OrderTemp(int orderId, int pid, String productAmount, String accomplishDeadline, String tenderDeadline, int consigneeId, String orderStatus, String productName, String consigneeName, String consigneeTel) {
        this.orderId = orderId;
        this.pid = pid;
        this.productAmount = productAmount;
        this.accomplishDeadline = accomplishDeadline;
        this.tenderDeadline = tenderDeadline;
        this.consigneeId = consigneeId;
        this.orderStatus = orderStatus;
        this.productName = productName;
        this.consigneeName = consigneeName;
        this.consigneeTel = consigneeTel;
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

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getAccomplishDeadline() {
        return accomplishDeadline;
    }

    public void setAccomplishDeadline(String accomplishDeadline) {
        this.accomplishDeadline = accomplishDeadline;
    }

    public String getTenderDeadline() {
        return tenderDeadline;
    }

    public void setTenderDeadline(String tenderDeadline) {
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }
}
