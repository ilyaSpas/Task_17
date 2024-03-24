package org.example.transactional.service.imp;

import org.example.transactional.entity.Order;
import org.example.transactional.exception.OrderNotFoundException;
import org.example.transactional.repository.OrderRepository;
import org.example.transactional.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public Order update(Long id, Order order) {
        Order orderFromDB = getById(id);
        orderFromDB.setCustomer(order.getCustomer());
        orderFromDB.setProducts(order.getProducts());
        orderFromDB.setTotalAmount(order.getTotalAmount());
        return orderRepository.save(orderFromDB);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
