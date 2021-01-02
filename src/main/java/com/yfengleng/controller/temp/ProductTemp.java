package com.yfengleng.controller.temp;

import java.io.Serializable;

public class ProductTemp implements Serializable {

    private int productId;
    private String productName;
    private String productInfo;
    private String productSpecification;
    private String productType;
    private int ptId;

    public ProductTemp(int productId, String productName, String productInfo, String productSpecification, String productType,int ptId) {
        this.productId = productId;
        this.productName = productName;
        this.productInfo = productInfo;
        this.productSpecification = productSpecification;
        this.productType = productType;
        this.ptId=ptId;
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
