package org.example.transactional.controller;

import org.example.transactional.entity.Customer;
import org.example.transactional.entity.Order;
import org.example.transactional.entity.Product;
import org.example.transactional.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/new")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order){
        return new ResponseEntity<>(orderService.save(order), HttpStatus.CREATED);
    }

    @PostMapping("/{customer_id}/new")
    public ResponseEntity<Order> placeOrder(@PathVariable("customer_id") Long customer_id,
                                            @RequestBody List<Product> products) {
        return new ResponseEntity<>(orderService.placeOrder(customer_id, products), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll(){
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order){
        return new ResponseEntity<>(orderService.update(id, order), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/delete")
    public HttpStatus deleteById(@PathVariable("id") Long id){
        orderService.deleteById(id);
        return HttpStatus.OK;
    }
}
