package com.yfengleng.dao;

import com.yfengleng.bean.Order;

import java.util.List;

public interface OrderDao {

    /**
     * 查询所有
     * @return 订单列表
     */
    List<Order> findAll();

    /**
     * 根据订单id查询
     * @param orderId 订单id
     * @return 订单
     */
    Order findByOrderId(int orderId);

    /**
     * 根据经销商id查询
     * @param consigneeId
     * @return
     */
    List<Order> findByConsigneeId(int consigneeId);

    /**
     * 查询所有未发布的订单
     * @return
     */
    List<Order> findAllPublishedOrder();

    /**
     * 新增订单
     * @param order 订单
     */
    void insert(Order order);

    /**
     * 更新订单
     * @param order 订单
     */
    void update(Order order);

    /**
     * 根据订单id删除
     * @param orderId
     */
    void deleteByOrderId(int orderId);
}
