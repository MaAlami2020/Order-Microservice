package com.example.webapp1a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.Item;
import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.User;
import com.example.webapp1a.repository.UserRepo;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    
    public Optional<User> findByUsername(String name){
        return userRepo.findByUsername(name);
    }

    public Optional<User> findById(Integer id){
        return userRepo.findById(id);
    }

    public void newOrder(Integer id, Order newOrder){
        User newUser = new User();
        Optional<User> user = userRepo.findById(id);
        newUser.setName(user.get().getName());
        newUser.setUsername(user.get().getUsername());
        newUser.setEmail(user.get().getEmail());
        newUser.setPassword(user.get().getPassword());
        newUser.setPasswordConfirmation(user.get().getPasswordConfirmation());
        newUser.setRol("USER");
        newUser.setDirection(user.get().getDirection());
        newUser.setShoppingCart(user.get().getShoppingCart());
        if(!user.get().getOrders().contains(newOrder)){
            newUser.getOrders().add(newOrder);
        }
        for(Item item: user.get().getItems()){
            newUser.getItems().add(item);
        }
        for(Order order: user.get().getOrders()){
            newUser.getOrders().add(order);
        }
        newUser.setId(id);
        userRepo.save(newUser);
    }
}
