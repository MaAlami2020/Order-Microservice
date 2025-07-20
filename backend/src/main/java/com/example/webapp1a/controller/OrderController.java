package com.example.webapp1a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webapp1a.model.Order;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/orders")
public class OrderController {


    
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





    @GetMapping("/{i}/admin")
    public String getOrderDetailAdmin(Model model, @PathVariable Integer id){
        model.addAttribute("updatedOrder","");
        return "orderAdmin";
    }

    @PostMapping("/{ident}/admin/update")
    public String updateOrderState(Model model, @PathVariable Integer ident, Order order) {
        //orderService.update(ident,order);
        model.addAttribute("updatedOrder","order successfully updated");
        return "orderAdmin";
    }
    
}
