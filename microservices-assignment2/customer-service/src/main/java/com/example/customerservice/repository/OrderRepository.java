package com.example.customerservice.repository;

import com.example.customerservice.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    Page<Order> findByCustomerId(Long customerId, Pageable pageable);

    List<Order> findByCustomerIdAndOrderDateBetween(Long customerId, LocalDate start, LocalDate end);

    Optional<Order> findByIdAndCustomerId(Long id, Long customerId);
}
