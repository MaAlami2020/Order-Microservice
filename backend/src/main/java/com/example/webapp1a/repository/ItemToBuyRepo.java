package com.example.webapp1a.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp1a.model.ItemToBuy;
import com.example.webapp1a.model.Order;
import com.example.webapp1a.model.ShoppingCart;
import java.util.List;

public interface ItemToBuyRepo extends JpaRepository<ItemToBuy, Integer>{

    List<ItemToBuy> findByShoppingCart(ShoppingCart shoppingCart);
    
    List<ItemToBuy> findByOrder(Order order);
}
