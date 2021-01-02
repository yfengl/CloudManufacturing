package com.yfengleng.bean;

import java.io.Serializable;

public class Product implements Serializable {

    private int productId;
    private int ptId;
    private String productName;
    private String productInfo;
    private String productSpecification;

    public Product(int productId, int ptId, String productName, String productInfo, String productSpecification) {
        this.productId = productId;
        this.ptId = ptId;
        this.productName = productName;
        this.productInfo = productInfo;
        this.productSpecification = productSpecification;
    }

    public Product(int ptId, String productName, String productInfo, String productSpecification) {
        this.ptId = ptId;
        this.productName = productName;
        this.productInfo = productInfo;
        this.productSpecification = productSpecification;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getPtId() {
        return ptId;
    }

    public void setPtId(int ptId) {
        this.ptId = ptId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", ptId=" + ptId +
                ", productName='" + productName + '\'' +
                ", productInfo='" + productInfo + '\'' +
                ", productSpecification='" + productSpecification + '\'' +
                '}';
    }
}
