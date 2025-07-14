package com.example.webapp1a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.ItemToBuy;
import com.example.webapp1a.model.ShoppingCart;
import com.example.webapp1a.repository.ItemToBuyRepo;

import java.util.List;

@Service
public class ItemToBuyService {

    @Autowired
    private ItemToBuyRepo itemToBuyRepo;

    public List<ItemToBuy> findByShoppingCart(ShoppingCart shoppingCart){
        return itemToBuyRepo.findByShoppingCart(shoppingCart);
    }
    
    
}
