package org.example.transactional.service.imp;

import jakarta.annotation.PostConstruct;
import org.example.transactional.entity.Customer;
import org.example.transactional.entity.Order;
import org.example.transactional.entity.Product;
import org.example.transactional.exception.OrderNotFoundException;
import org.example.transactional.exception.ProductOutOfStockException;
import org.example.transactional.exception.ThereAreNotEnoughFundsException;
import org.example.transactional.repository.OrderRepository;
import org.example.transactional.service.CustomerService;
import org.example.transactional.service.OrderService;
import org.example.transactional.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    private final CustomerService customerService;

    private final ProductService productService;

    @Lazy
    private OrderService orderService;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository,
                           CustomerService customerService,
                           ProductService productService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order placeOrder(Long customerId, List<Product> products) {
        Order order = new Order();
        Customer customer = customerService.getById(customerId);
        order.setCustomer(customer);
        Double totalAmount = 0D;
        for (Product product : products) {
            totalAmount += product.getPrice();
            Product productFromDB = productService.getById(product.getId());
            if (productFromDB.getQuantity() <= 0) {
                throw new ProductOutOfStockException();
            }
            productFromDB.setQuantity(product.getQuantity() - 1);
            productService.save(productFromDB);
        }
        order.setProducts(products);
        order.setTotalAmount(totalAmount);
        if (customer.getBalance() < totalAmount) {
            throw new ThereAreNotEnoughFundsException();
        }
        customer.setBalance(customer.getBalance() - totalAmount);
        customerService.save(customer);
        return orderService.save(order);
    }
}
