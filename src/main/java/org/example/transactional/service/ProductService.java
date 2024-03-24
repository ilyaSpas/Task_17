package org.example.transactional.service;

import org.example.transactional.entity.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    List<Product> getAll();

    Product getById(Long id);

    Product update(Long id, Product product);

    void deleteById(Long id);
}
