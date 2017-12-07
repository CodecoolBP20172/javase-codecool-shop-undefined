package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {
    private List<Order> ORDER = new ArrayList<>();
    private static OrderDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private OrderDaoMem() {
    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        order.setId(ORDER.size() + 1);
        ORDER.add(order);
    }

    @Override
    public Order find(int orderId) {
        return ORDER.stream().filter(t -> t.getId() == orderId).findFirst().orElse(null);
    }

    @Override
    public List<Order> getAll() {
        return ORDER;
    }
}
