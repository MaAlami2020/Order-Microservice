package com.example.webapp1a.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private final SimpMessagingTemplate messagingTemplate;

    public OrderController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    
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

    @GetMapping("/{id}/info")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        Optional<Order> order = orderService.findById(id);

        if(order.isPresent()) {
            return new ResponseEntity<>(order.get(),HttpStatus.OK);
        } 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/timeout")
    public String getTimedoutOrderById(@PathVariable Integer id) {
        orderService.viewOrder(id);
        return "order";
    }

    /**
     * @param model
     * @return page in which the details of an order of a particular user is shown
     */
    @GetMapping("/{id}/detail")
    public String getOrderDetail(Model model, @PathVariable Integer id){
        return "order";
    }

    @GetMapping("/{id}/state")
    public String getOrderPage(Model model, @PathVariable Integer id) {
        model.addAttribute("states", State.values());
        return "orderStatus";
    }

    @GetMapping("/{id}/admin")
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

            messagingTemplate.convertAndSend(
                "/topic/pedidos/" + id, order
            );

            orderService.save(oldOrder.get());
            model.addAttribute("states", State.values());
            return "orderStatus";
        } else {
            return "error";
        }
    } 
}
