package com.example.customerservice.mapper;

import com.example.customerservice.dto.CustomerDTO;
import com.example.customerservice.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerDTO toDto(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCreatedAt()
        );
    }

    public List<CustomerDTO> toDtoList(List<Customer> customers) {
        return customers.stream().map(this::toDto).toList();
    }
}
