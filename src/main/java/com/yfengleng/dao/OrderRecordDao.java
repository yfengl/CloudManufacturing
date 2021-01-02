package com.yfengleng.dao;

import com.yfengleng.bean.OrderRecord;

import java.util.List;

public interface OrderRecordDao {

    void insert(OrderRecord orderRecord);

    List<OrderRecord> findByOid(int oid);
}
