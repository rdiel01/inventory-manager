package com.example.inventorymanager.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue
    public int id;

    //name is a place holder for creation date
    @NotNull
    public String name;

//    @GeneratedValue
//    private Date createDate;
//
//    private Date placedDate;
//
//    private Date receivedDate;

    @OneToMany
    public List<OrderItem> orderItems;

    public Order() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getItems(){ return orderItems;}

    public void addItem(OrderItem item){ orderItems.add(item);}
}
