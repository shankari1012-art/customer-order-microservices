package com.example.customerservice.service;

import com.example.customerservice.entity.Customer;
import com.example.customerservice.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // ✅ Create Customer
    public Customer createCustomer(String name, String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setCreatedAt(LocalDate.now());
        return repository.save(customer);
    }

    // ✅ Get all customers
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // ✅ Get customer by ID (with 404 handling)
    public Customer getCustomerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    // ✅ Delete customer (with 404 check)
    public void deleteCustomer(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        repository.delete(customer);
    }
}
