package com.example.inventorymanager.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=50)
    private String name;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer minimum;

    @NotNull
    private Integer maximum;

    private String description;

    @OneToMany
    @JoinColumn (name = "item_id")
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    public Item(String name, String description, Integer quantity, Integer minimum, Integer maximum) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Item() { }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }
}
