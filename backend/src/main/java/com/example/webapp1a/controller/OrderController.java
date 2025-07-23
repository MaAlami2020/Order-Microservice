package com.example.webapp1a.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.Order.State;
import com.example.webapp1a.service.OrderService;

import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @GetMapping("/users/{id}")
    public String getUserOrders(Model model, @PathVariable Integer id){
        return "index";
    }
    
    /**
     * @param model
     * @return admin orders page --> sum of orders
     */
    @GetMapping("/admin")
    public String getAdminOrders(Model model){
        return "adminIndex";
    }

    /**
     * @param model
     * @return page in which the details of an order of a particular user is shown
     */
    
    @GetMapping("/{id}")
    public String getOrderDetail(Model model, @PathVariable Integer id){
        return "order";
    }

    @GetMapping("/{id}/state")
    public String getOrderPage(Model model, @PathVariable Integer id) {
        model.addAttribute("states", State.values());
        return "orderStatus";
    }


    @GetMapping("/{i}/admin")
    public String getOrderDetailAdmin(Model model, @PathVariable Integer id){
        model.addAttribute("updatedOrder","");
        return "orderAdmin";
    }

    @PostMapping("/{id}/state/update")
    public String updateOrderState(Model model, @PathVariable Integer id, Order order) {
        Optional<Order> oldOrder = orderService.findById(id);
        if(oldOrder.isPresent()){
            if(order.getState() != null){
                oldOrder.get().setState(order.getState());
            }
            orderService.save(oldOrder.get());
            model.addAttribute("states", State.values());
            return "orderStatus";
        } else {
            return "//localhost:8443/error";
        }
    }
    
}
