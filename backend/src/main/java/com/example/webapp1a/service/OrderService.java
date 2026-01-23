package com.example.webapp1a.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.User;
import com.example.webapp1a.repository.OrderRepo;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final Logger log =
            LoggerFactory.getLogger(OrderService.class);

    //@Retry(name = "orderRetry")//“Se implementó un mecanismo de reintento controlado para gestionar fallos transitorios antes de abrir el circuito.”
    //@Bulkhead(name = "orderBulkhead", type = Bulkhead.Type.SEMAPHORE)
    @CircuitBreaker(name = "orderIdService", fallbackMethod = "fallbackOrder")
    public Order viewOrder(Integer orderId) {

        return restTemplate.getForObject(
            "https://localhost:8442/api/orders/" + orderId + "/timeout",
            Order.class
        );
    }

    // Fallback
    public Order fallbackOrder(Integer orderId, Throwable ex) {
        log.warn("Fallback actived for order {} - {}", orderId, ex.toString());
        throw new ResponseStatusException(
            HttpStatus.SERVICE_UNAVAILABLE,
            "Order service timeout",
            ex
        );
    }

    public void save(Order order){
        orderRepo.save(order);
    }

    public Page<Order> findAll(Pageable page) {
        return orderRepo.findAll(page);
    }

    public Optional<Order> findById(Integer id){
        return orderRepo.findById(id);
    }

    public Page<Order> findByName(String name, Pageable page){
        return orderRepo.findByName(name, page);
    }

    public Page<Order> findByDate(String date, Pageable page){
        return orderRepo.findByDate(date, page);
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
