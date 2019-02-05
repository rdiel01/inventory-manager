package com.example.inventorymanager.models.data;

import com.example.inventorymanager.models.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderItemDao extends CrudRepository<OrderItem, Integer> {
}
