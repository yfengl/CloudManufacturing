package com.yfengleng.bean;

import java.io.Serializable;

public class OrderRecord implements Serializable {

    private int oid;
    private int price;
    private int managerId;

    public OrderRecord(int oid, int price, int managerId) {
        this.oid = oid;
        this.price = price;
        this.managerId = managerId;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
