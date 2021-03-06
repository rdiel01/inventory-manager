package com.example.inventorymanager.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue
    public int id;

    //name is a place holder for creation date
    @NotNull
    @Size(min=3, max=50, message = "Order name must be 3 to 50 characters.")
    public String name;

    public Boolean status = Boolean.TRUE;

    public Boolean deleted = Boolean.FALSE;

//    @GeneratedValue
//    private Date createDate;
//
//    private Date placedDate;
//
//    private Date receivedDate;

    @OneToMany
    public List<OrderItem> orderItems;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getOrderItems() {
        return orderItems;
    }

    public void setOrderItem(OrderItem item) {
        orderItems.add(item);
    }

    public void removeOrderItem(OrderItem item) { orderItems.remove(item); }

    public void deactivate() {
        this.status = Boolean.FALSE;

    }

    public Boolean getStatus() {
        return this.status;
    }

    public void delete() {
        this.deactivate();
        this.deleted = Boolean.TRUE;
    }
}