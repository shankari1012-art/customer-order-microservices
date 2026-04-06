package com.example.customerservice.controller;

import com.example.customerservice.dto.CustomerCreateRequestDTO;
import com.example.customerservice.dto.CustomerDTO;
import com.example.customerservice.mapper.CustomerMapper;
import com.example.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    public CustomerController(CustomerService service, CustomerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // ✅ CREATE
    @PostMapping
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerCreateRequestDTO request) {
        return mapper.toDto(service.createCustomer(request.getName(), request.getEmail()));
    }

    // ✅ GET ALL
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return mapper.toDtoList(service.getAllCustomers());
    }

    // ✅ GET ONE
    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return mapper.toDto(service.getCustomerById(id));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
    }

    // 🔥 CALL ORDER SERVICE
    @GetMapping("/{id}/orders")
    public List<String> getCustomerOrders(@PathVariable Long id) {
        return restTemplate.getForObject(
                "http://order-service/orders/" + id,
                List.class
        );
    }
}