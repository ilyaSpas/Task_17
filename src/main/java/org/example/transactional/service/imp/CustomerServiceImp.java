package org.example.transactional.service.imp;

import org.example.transactional.entity.Customer;
import org.example.transactional.exception.CustomerNotFoundException;
import org.example.transactional.repository.CustomerRepository;
import org.example.transactional.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImp(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Customer customerFromDB = getById(id);
        customerFromDB.setUsername(customerFromDB.getUsername());
        customerFromDB.setBalance(customer.getBalance());
        return customerRepository.save(customerFromDB);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
