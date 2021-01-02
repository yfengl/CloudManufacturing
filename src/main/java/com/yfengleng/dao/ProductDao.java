package com.yfengleng.dao;

import com.yfengleng.bean.Product;

import java.util.List;

public interface ProductDao {

    /**
     * 查询所有
     * @return 产品列表
     */
    List<Product> findAll();

    /**
     * 根据产品id查询
     * @param productId 产品id
     * @return 产品
     */
    Product findByProductId(int productId);

    /**
     * 通过产品类别id查询
     * @param ptId 差评类别id
     * @return 产品列表
     */
    List<Product> findByProductTypeId(int ptId);

    /**
     * 根据产品信息查询
     * @param info 产品信息
     * @return 产品列表
     */
    List<Product> findByInfo(String info);

    /**
     * 根据产品名称查询
     * @param productName
     * @return
     */
    Product findByProductName(String productName);

    /**
     * 新增产品
     * @param product 产品
     */
    void insert(Product product);

    /**
     * 修改产品信息
     * @param product 产品
     */
    void update(Product product);

    /**
     * 根据产品id删除产品
     * @param productId 产品id
     */
    void deleteByProductId(int productId);

    /**
     * 根据产品名称删除产品
     * @param productName
     */
    void deleteByProductName(String productName);
}
