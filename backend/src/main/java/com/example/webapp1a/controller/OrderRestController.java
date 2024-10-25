package com.example.webapp1a.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp1a.model.ItemToBuy;
import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.User;
import com.example.webapp1a.service.ItemToBuyService;
import com.example.webapp1a.service.OrderService;
import com.example.webapp1a.service.UserService;



@RestController
@RequestMapping("/databases")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemToBuyService itemToBuyService;

    @GetMapping("/orders")
    public Page<Order> getOrders(Pageable page) {
        return orderService.findAll(page);
    }

    @GetMapping("/orders/user/{username}")
    public ResponseEntity<Page<Order>> getOrdersByUser(@PathVariable String username, Pageable page) {
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            return new ResponseEntity<>(orderService.findByUser(user.get(),page),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders/{id}/items")
    public ResponseEntity<List<ItemToBuy>> getItemsByOrder(@PathVariable Integer id) {
        Optional<Order> order = orderService.findById(id);

        if(order.isPresent()) {
            return new ResponseEntity<>(itemToBuyService.findByOrder(order.get()), HttpStatus.OK);
        } 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/orders/{ident}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer ident) {
        Optional<Order> order = orderService.findById(ident);

        if(order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } 
        return ResponseEntity.notFound().build();
    }

    
    
}
