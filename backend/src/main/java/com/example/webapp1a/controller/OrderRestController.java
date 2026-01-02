package com.example.webapp1a.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.User;
import com.example.webapp1a.service.OrderService;
import com.example.webapp1a.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    //LOGGED WITH JWT

    @Operation(summary = "Post order state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update order state", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        }),
        @ApiResponse(responseCode = "404", description = "No order updated", content = @Content)
    })
    @PostMapping("/api/orders/{id}/state/update")
    public ResponseEntity<Order> updateOrderState(Model model, @PathVariable Integer id, @RequestBody Order order) {
        Optional<Order> oldOrder = orderService.findById(id);
        if(oldOrder.isPresent()){
            if(order.getState() != null){
                oldOrder.get().setState(order.getState());
            }
            orderService.save(oldOrder.get());
            return new ResponseEntity<>(oldOrder.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get orders")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get orders", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        }),
        @ApiResponse(responseCode = "404", description = "No order founded", content = @Content)
    })
    @GetMapping("/api/orders")
    public ResponseEntity<Page<Order>> getOrders(Pageable page) {
        return new ResponseEntity<>(orderService.findAll(page),HttpStatus.OK);
    }

    @Operation(summary = "Get orders by name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get orders by name", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        }),
        @ApiResponse(responseCode = "404", description = "No order by name founded", content = @Content)
    })
    @GetMapping("/api/orders/{name}")
    public ResponseEntity<Page<Order>> getOrderByName(@PathVariable String name, Pageable page) {
        return new ResponseEntity<>(orderService.findByName(name, page), HttpStatus.OK);
    }
    
    @Operation(summary = "Get orders by date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Get orders by date", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))
        }),
        @ApiResponse(responseCode = "404", description = "No order by date founded", content = @Content)
    })
    @GetMapping("/api/orders/date/{name}")
    public ResponseEntity<Page<Order>> getOrderByDate(@PathVariable String name, Pageable page) {
        return new ResponseEntity<>(orderService.findByDate(name, page), HttpStatus.OK);
    }

    //LOGGED WITH REDDIS

    @GetMapping("/orders/user")
    public ResponseEntity<Page<Order>> getOrdersByUser(HttpServletRequest request, Pageable page) {
        //Optional<User> user = userService.findById(id);
        String username = (String) request.getSession().getAttribute("user");
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            return new ResponseEntity<>(orderService.findByUser(user.get(),page),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<Order>> getOrdersReddis(Pageable page) {
        return new ResponseEntity<>(orderService.findAll(page),HttpStatus.OK);
    }

    @GetMapping("/orders/{name}")
    public ResponseEntity<Page<Order>> getOrderByNameReddis(@PathVariable String name, Pageable page) {
        return new ResponseEntity<>(orderService.findByName(name, page), HttpStatus.OK);
    }
    
    @GetMapping("/orders/date/{name}")
    public ResponseEntity<Page<Order>> getOrderByDateReddis(@PathVariable String name, Pageable page) {
        return new ResponseEntity<>(orderService.findByDate(name, page), HttpStatus.OK);
    }
}   
