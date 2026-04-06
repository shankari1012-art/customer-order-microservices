package com.example.customerservice.service;

import com.example.customerservice.entity.Customer;
import com.example.customerservice.entity.Order;
import com.example.customerservice.repository.CustomerRepository;
import com.example.customerservice.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public Order createOrder(Long customerId, BigDecimal amount) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDate.now());
        order.setAmount(amount);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomer(Long customerId) {
        ensureCustomerExists(customerId);
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByDateRange(Long customerId, LocalDate start, LocalDate end) {
        ensureCustomerExists(customerId);
        return orderRepository.findByCustomerIdAndOrderDateBetween(customerId, start, end);
    }

    public Page<Order> getOrdersPaged(Long customerId, Pageable pageable) {
        ensureCustomerExists(customerId);
        return orderRepository.findByCustomerId(customerId, pageable);
    }

    public Order updateOrder(Long customerId, Long orderId, BigDecimal amount) {
        ensureCustomerExists(customerId);

        Order order = orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        order.setAmount(amount);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long customerId, Long orderId) {
        ensureCustomerExists(customerId);

        Order order = orderRepository.findByIdAndCustomerId(orderId, customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        orderRepository.delete(order);
    }

    private void ensureCustomerExists(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
    }
}
