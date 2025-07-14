package com.example.webapp1a.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.Order.State;
import com.example.webapp1a.model.User;
import com.example.webapp1a.repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public void save(Order order){
        orderRepo.save(order);
    }

    public Page<Order> findAll(Pageable page) {
        return orderRepo.findAll(page);
    }

    public Optional<Order> findById(Integer id){
        return orderRepo.findById(id);
    }

    public Page<Order> findByUser(User user, Pageable page){
        return orderRepo.findByUser(user, page);
    }

    /*public void update(Integer id, Order newOrder){
        Optional<Order> order = orderRepo.findById(id);

        newOrder.setTotalCost(order.get().getTotalCost());
        newOrder.setCreationDate(order.get().getCreationDate());
        newOrder.setUser(order.get().getUser());
        
        String auxState = newOrder.getAuxState();

        for(State state: State.values()){
            if(state.name().equals(auxState)){
                newOrder.setState(state);
                break;
            }
        }

        newOrder.setId(id);
        orderRepo.save(newOrder);
    }*/
    
}
