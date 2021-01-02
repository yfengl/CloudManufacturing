package com.yfengleng.controller.temp;

import java.io.Serializable;

public class EquipmentTemp implements Serializable {

    private int etid;
    private int oid;
    private int fid;
    private int equipmentId;
    private String equipmentName;
    private String equipmentSpecification;
    private String equipmentStatus;
    private String rentalStatus;
    private String equipmentTypeName;
    private String factoryName;

    public EquipmentTemp(int etid,int oid,int fid,int equipmentId,String equipmentName, String equipmentSpecification, String equipmentStatus, String rentalStatus, String equipmentTypeName, String factoryName) {
        this.etid = etid;
        this.oid=oid;
        this.fid = fid;
        this.equipmentId=equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentSpecification = equipmentSpecification;
        this.equipmentStatus = equipmentStatus;
        this.rentalStatus = rentalStatus;
        this.equipmentTypeName = equipmentTypeName;
        this.factoryName = factoryName;
    }

    public int getEtid() {
        return etid;
    }

    public void setEtid(int etid) {
        this.etid = etid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentSpecification() {
        return equipmentSpecification;
    }

    public void setEquipmentSpecification(String equipmentSpecification) {
        this.equipmentSpecification = equipmentSpecification;
    }

    public String getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(String equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }
}
