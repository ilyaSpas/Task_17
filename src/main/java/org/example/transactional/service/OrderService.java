package org.example.transactional.service;

import org.example.transactional.entity.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);

    List<Order> getAll();

    Order getById(Long id);

    Order update(Long id, Order order);

    void deleteById(Long id);
}
