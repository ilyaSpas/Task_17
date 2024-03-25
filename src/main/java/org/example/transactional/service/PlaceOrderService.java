package org.example.transactional.service;

import org.example.transactional.entity.Order;
import org.example.transactional.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceOrderService {
    Order placeOrder(Long customerId, List<Product> products);
}
