package com.example.webapp1a.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp1a.model.Order;
import com.example.webapp1a.service.OrderService;




@RestController
@RequestMapping("/api")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<Page<Order>> getOrders(Pageable page) {
        return new ResponseEntity<>(orderService.findAll(page),HttpStatus.OK);
    }

    @GetMapping("/orders/{name}")
    public ResponseEntity<Page<Order>> getOrderByName(@PathVariable String name, Pageable page) {
        return new ResponseEntity<>(orderService.findByName(name, page), HttpStatus.OK);
    }
    
    @GetMapping("/orders/date/{name}")
    public ResponseEntity<Page<Order>> getOrderByDate(@PathVariable String name, Pageable page) {
        return new ResponseEntity<>(orderService.findByDate(name, page), HttpStatus.OK);
    }
}   
