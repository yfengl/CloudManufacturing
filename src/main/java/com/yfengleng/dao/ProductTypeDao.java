package com.yfengleng.dao;

import com.yfengleng.bean.ProductType;

import java.util.List;

public interface ProductTypeDao {

    /**
     * 查询所有
     * @return 产品类别列表
     */
    List<ProductType> findAll();

    /**
     * 根据产品类别id查找
     * @return 产品类别
     * @param typeId 产品类型id
     */
    ProductType findByTypeId(int typeId);

    /**
     * 根据产品类别名称查找
     * @return 产品类别列表
     * @param typeName 产品类型名称
     */
    List<ProductType> findByTypeName(String typeName);

    /**
     * 新增产品类别
     * @param productType 产品类型
     */
    void insert(ProductType productType);

    /**
     * 更新产品类别
     * @param productType 产品类型
     */
    void update(ProductType productType);

    /**
     * 根据类型Id删除
     * @param typeId 类型id
     */
    void deleteByTypeId(int typeId);

    /**
     * 根据类型名称删除
     * @param typeName
     */
    void deleteByTypeName(String typeName);
}
