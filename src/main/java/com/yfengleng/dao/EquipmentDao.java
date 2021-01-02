package com.yfengleng.dao;

import com.yfengleng.bean.Equipment;
import com.yfengleng.bean.Product;

import java.util.List;

public interface EquipmentDao {
    /**
     * 查询所有
     * @return 设备列表
     */
    List<Equipment> findAll();

    /**
     * 根据设备id查询
     * @param equipmentId 设备id
     * @return 设备
     */
    Product findByEquipmentId(int equipmentId);

    /**
     * 通过设备类别id查询
     * @param etId 设备类别id
     * @return 设备列表
     */
    List<Equipment> findByEquipmentTypeId(int etId);

    /**
     * 根据设备名称租用设备
     * @param name
     * @return
     */
    Equipment findByEquipmentName(String equipmentName);

    /**
     * 根据工厂id查询
     * @param fid
     * @return
     */
    List<Equipment> findByFid(int fid);

    /**
     * 根据信息模糊查询
     * @param info
     * @return
     */
    List<Equipment> findByInfo(String info);

    /**
     * 附带条件的根据信息模糊查询
     * @param info
     * @return
     */
    List<Equipment> findByInfoFid(String fid,String info);

    /**
     * 新增设备
     * @param equipment 设备
     */
    void insert(Equipment equipment);

    /**
     * 修改设备信息
     * @param equipment 设备
     */
    void update(Equipment equipment);

    /**
     * 根据设备id删除设备
     * @param equipmentId 设备id
     */
    void deleteByEquipmentId(int equipmentId);

    /**
     * 根据设备名称删除设备
     * @param equipmentName
     */
    void deleteByEquipmentName(String equipmentName);
}
