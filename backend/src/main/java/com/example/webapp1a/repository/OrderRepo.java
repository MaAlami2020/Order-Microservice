package com.example.webapp1a.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.User;

public interface OrderRepo extends JpaRepository<Order, Integer>{

    Page<Order> findByUser(User user, Pageable page);
    
}
