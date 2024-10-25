package com.example.webapp1a.controller;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webapp1a.model.ItemToBuy;
import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.Order.State;
import com.example.webapp1a.model.User;
import com.example.webapp1a.service.ItemToBuyService;
import com.example.webapp1a.service.OrderService;
import com.example.webapp1a.service.UserService;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemToBuyService itemToBuyService;

    @Autowired
    private OrderService orderService;
    
    @GetMapping("/user/{username}")
    public String getUserOrders(Model model, @PathVariable String username){
        model.addAttribute("username",username);
        return "index";
        //return "adminIndex";
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
    
    @GetMapping("/{ident}")
    public String getOrderDetail(Model model, @PathVariable Integer ident){

        model.addAttribute("ident",ident);
        return "order";
    }

    @GetMapping("/{ident}/admin")
    public String getOrderDetailAdmin(Model model, @PathVariable Integer ident){
        model.addAttribute("ident",ident);
        model.addAttribute("updatedOrder","");
        return "orderAdmin";
    }

    @PostMapping("/{ident}/admin/update")
    public String updateOrderState(Model model, @PathVariable Integer ident, Order order) {
        orderService.update(ident,order);
        model.addAttribute("updatedOrder","order successfully updated");
        return "orderAdmin";
    }
    

    /**
     * @param model
     * @param request
     * @return generate order
     * @throws IOException
     */
    @GetMapping("/new/users/{username}")
    public String generate(Model model, @PathVariable String username) throws IOException {
        Order order = new Order();

        Optional<User> user = userService.findByUsername(username);

        if(user.isPresent()) {
            List<ItemToBuy> itemsToBuy = itemToBuyService.findByShoppingCart(user.get().getShoppingCart());
       
            double cost=0;
            for(ItemToBuy item: itemsToBuy){
                cost += item.getItem().getPrice();
                item.setOrder(order);
                item.setShoppingCart(null);
            }

            order.setTotalCost(cost);
            //order.setState("PENDING");
            order.setState(State.PENDING);
            order.setCreationDate(LocalDate.now());
            order.setUser(user.get());
            orderService.save(order);
            //userService.newOrder(user.get().getId(), order);
            
           
            //model.addAttribute("neworder",true);
            //model.addAttribute("order","order successfully created");

            return "index";
        } 
        return "error";
    }
    
}
