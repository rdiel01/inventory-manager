package com.example.inventorymanager.models.forms;


import com.example.inventorymanager.models.Item;
import com.example.inventorymanager.models.Order;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class AddOrderItemForm {

    private Order order;

    private ArrayList<Item> items;

    @NotNull
    private int orderId;

    @NotNull
    private int itemId;

    @NotNull
    private Integer orderQuantity;

    public AddOrderItemForm() {}

    public AddOrderItemForm(ArrayList<Item> items, Order order) {
        this.order = order;
        this.items = items;
    }

    public void setOrderId(int orderId) { this.orderId = orderId;}

    public int getOrderId() { return orderId; }

    public void setItemId(int itemId) { this.itemId = itemId;}

    public int getItemId() { return itemId; }

    public Iterable<Item> getItems() { return items; }

    public Order getOrder() { return order;}

    public void setOrderQuantity(Integer orderQuantity) { this.orderQuantity = orderQuantity;}

    public Integer getOrderQuantity() { return orderQuantity;}
}
