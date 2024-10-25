package com.example.webapp1a.model;

import java.time.LocalDate;
//import java.util.List;
//import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    //@OneToMany
    //private List<ItemToBuy> itemsToBuy = new ArrayList<>();

    @Column(name="totalCost")
    private Double totalCost;

    @Column(name="date")
    private LocalDate creationDate;

    @Column(name="state")
    private State state;

    private String auxState;

    public Order(){}

    public enum State {
        PENDING, CONFIRMED, DELIVERED, CANCELLED
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
    
    /*no cascade
    public void setItemsToBuy(List<ItemToBuy> items){
        this.itemsToBuy = items;
    }

    public List<ItemToBuy> getItemsToBuy(){
        return itemsToBuy;
    }*/

    /*bidirectional relationship with cascade
    public void addItemToBuy(ItemToBuy itemToBuy){
        itemsToBuy.add(itemToBuy);
        itemToBuy.setOrder(this);
    }

    public void removeItemToBuy(ItemToBuy itemToBuy){
        itemsToBuy.remove(itemToBuy);
        itemToBuy.setOrder(null);
    }*/

    public void setTotalCost(Double totalCost){
        this.totalCost = totalCost;
    }

    public Double getTotalCost(){
        return totalCost;
    }

    public void setCreationDate(LocalDate creationDate){
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }

    public void setAuxState(String state){
        this.auxState = state;
    }

    public String getAuxState(){
        return auxState;
    }
}
