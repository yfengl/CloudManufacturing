package com.yfengleng.bean;

import java.io.Serializable;

public class User implements Serializable {

    private String account;
    private String password;
    private String name;
    private String email;
    private String tel;
    private String type;
    private int userId;

    public User() {
    }

    public User(String account, String password, String name, String email, String tel, String type) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", type='" + type + '\'' +
                ", userId=" + userId +
                '}';
    }
}
