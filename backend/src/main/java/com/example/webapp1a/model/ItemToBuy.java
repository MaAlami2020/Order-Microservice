package com.example.webapp1a.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_itemToBuy")
public class ItemToBuy {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String code;

    @ManyToMany
    private List<Item> items = new ArrayList<>();

    private String size;

    private Integer count;

    @ManyToOne
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JsonIgnore
    private ShoppingCart shoppingCart;

    public ItemToBuy() {}

    public void setId(Integer id){
        this.id=id;
    }

    public Integer getId(){
        return id;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public String getSize(){
        return size;
    }

    public void setSize(String size){
        this.size = size;
    }

    public List<Item> getItems(){
        return items;
    }

    public void setOrder(Order order){
        this.order=order;
    }

    public Order getOrder(){
        return order;
    }

    public void setCount(Integer count){
        this.count=count;
    }

    public Integer getCount(){
        return count;
    }

    public void setShoppingCart(ShoppingCart shoppingCart){
        this.shoppingCart= shoppingCart;
    }

    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }
   
}