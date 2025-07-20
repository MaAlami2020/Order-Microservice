package com.example.webapp1a.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.User;

public interface OrderRepo extends JpaRepository<Order, Integer>{

    Page<Order> findByUser(User user, Pageable page);
    
    @Query("select o from Order o where o.code like %:name%")
    Page<Order> findByName(String name, Pageable page);

    @Query("select p from Order p where p.creationDate like %:date%")
    Page<Order> findByDate(String date, Pageable page);
}
