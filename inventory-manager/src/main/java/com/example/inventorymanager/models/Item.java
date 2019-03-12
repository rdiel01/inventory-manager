package com.example.inventorymanager.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=50, message = "Must be 3 to 5 characters")
    private String name;

    @NotNull(message = "Quantity required")
    @Min(value=0, message = "Must be a positive number")
    private Integer quantity;

    @NotNull(message = "Minimum Threshold required")
    @Min(value=0, message = "Must be a positive number")
    private Integer minimum;

    @NotNull(message = "Maximum Threshold required")
    @Min(value=0, message = "Must be a positive number")
    private Integer maximum;

    private String description;

    @OneToMany
    private List<OrderItem> orderItems;

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
