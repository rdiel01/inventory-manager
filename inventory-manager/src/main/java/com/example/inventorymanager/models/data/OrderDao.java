package com.example.inventorymanager.models.data;

import com.example.inventorymanager.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderDao extends CrudRepository<Order, Integer> {
}
