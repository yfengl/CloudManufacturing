package com.yfengleng.bean;

import java.io.Serializable;

public class Consignee extends User implements Serializable {

    private String address;

    public Consignee() {
    }

    public Consignee(String account, String password, String name, String email, String tel, String type,String address) {
        super(account, password, name, email, tel, type);
        this.address=address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
