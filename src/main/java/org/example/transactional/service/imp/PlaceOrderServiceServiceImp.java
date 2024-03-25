package org.example.transactional.service.imp;

import org.example.transactional.entity.Customer;
import org.example.transactional.entity.Order;
import org.example.transactional.entity.Product;
import org.example.transactional.exception.ProductOutOfStockException;
import org.example.transactional.exception.ThereAreNotEnoughFundsException;
import org.example.transactional.service.CustomerService;
import org.example.transactional.service.OrderService;
import org.example.transactional.service.PlaceOrderService;
import org.example.transactional.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaceOrderServiceServiceImp implements PlaceOrderService {

    private final CustomerService customerService;

    private final OrderService orderService;

    private final ProductService productService;

    @Autowired
    public PlaceOrderServiceServiceImp(CustomerService customerService, OrderService orderService, ProductService productService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order placeOrder(Long customerId, List<Product> products) {
        Order order = new Order();
        Customer customer = customerService.getById(customerId);
        order.setCustomer(customer);
        Double totalAmount = 0D;
        for (Product product : products) {
            totalAmount += product.getPrice();
            Product productFromDB = productService.getById(product.getId());
            if (productFromDB.getQuantity() <= 0){
                throw new ProductOutOfStockException();
            }
            productFromDB.setQuantity(product.getQuantity() - 1);
            productService.save(productFromDB);
        }
        order.setProducts(products);
        order.setTotalAmount(totalAmount);
        if (customer.getBalance() < totalAmount){
            throw new ThereAreNotEnoughFundsException();
        }
        customer.setBalance(customer.getBalance() - totalAmount);
        customerService.save(customer);
        return orderService.save(order);
    }
}
