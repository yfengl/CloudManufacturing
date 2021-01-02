package com.yfengleng.bean;

import java.io.Serializable;

public class Manager extends User implements Serializable {

    public Manager() {
    }

    public Manager(String account, String password, String name, String email, String tel, String type) {
        super(account, password, name, email, tel, type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
