package org.example.transactional.controller;

import org.example.transactional.entity.Customer;
import org.example.transactional.entity.Order;
import org.example.transactional.entity.Product;
import org.example.transactional.service.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/place-order")
public class PlaceOrderController {

    private final PlaceOrderService placeOrderService;

    @Autowired
    public PlaceOrderController(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    @PostMapping("/{customer_id}/new")
    public ResponseEntity<Order> placeOrder(@PathVariable("customer_id") Long customer_id,
                                            @RequestBody List<Product> products) {
        return new ResponseEntity<>(placeOrderService.placeOrder(customer_id, products), HttpStatus.CREATED);
    }
}
