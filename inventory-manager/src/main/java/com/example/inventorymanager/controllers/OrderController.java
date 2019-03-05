package com.example.inventorymanager.controllers;

import com.example.inventorymanager.models.Item;
import com.example.inventorymanager.models.Order;
import com.example.inventorymanager.models.OrderItem;
import com.example.inventorymanager.models.data.ItemDao;
import com.example.inventorymanager.models.data.OrderDao;
import com.example.inventorymanager.models.data.OrderItemDao;
import com.example.inventorymanager.models.forms.AddOrderItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    ItemDao itemDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    OrderDao orderDao;

    @RequestMapping(value="")
    public String index(Model model) {
        //List<Order> orders = (List<Order>) orderDao.findAll();
        ArrayList<Order> orders = new ArrayList<Order>();
        for (Order order:orderDao.findAll()) {
            if (order.getStatus()){
                orders.add(order);
            }
        }

        model.addAttribute("title", "View Orders");
        model.addAttribute("orders",orders);
        //arraylist of items with qty <= minimum
        //model.addAttribut("html item object","that arraylist")
        return "order/index";
    }

    @RequestMapping(value="create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute(new Order());
        model.addAttribute("title", "Create new order");
        return "order/create";
    }

    @RequestMapping(value="create", method = RequestMethod.POST)
    public String create(Model model, @ModelAttribute @Valid Order order, Errors errors) {
        if(errors.hasErrors()){
            model.addAttribute("order", order);
            return "order/create";
        }
        orderDao.save(order);
        return "redirect:view/" + order.getId();
    }

    @RequestMapping(value="view/{orderId}", method = RequestMethod.GET)
    public String viewOrder(Model model, @PathVariable int orderId){
        Order order = orderDao.findOne(orderId);
        model.addAttribute("title", order.getName());
        model.addAttribute("order",order);
        model.addAttribute("orderItems", order.getOrderItems());
        return "order/view";
    }

    @RequestMapping(value ="{orderId}/add-item", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int orderId) {

        Order order = orderDao.findOne(orderId);

        if (order.orderItems.isEmpty()) {
            ArrayList<Item> items = new ArrayList<>();
            for (Item item : itemDao.findAll()) {
                items.add(item);
            }
            AddOrderItemForm form = new AddOrderItemForm(items, order);
            model.addAttribute("title","Add item to order: "+ order.getName());
            model.addAttribute("form", form);
        } else {
            ArrayList<Item> items = new ArrayList<>();
            ArrayList<Integer> itemIds = new ArrayList<>();
            ArrayList<Integer> orderItemIds = new ArrayList<>();
            for (Item item : itemDao.findAll()) {
                itemIds.add(item.getId());
            }
            for (OrderItem orderItem : order.orderItems) {
                orderItemIds.add(orderItem.getItemId());
            }
            for (Integer ItemId : itemIds) {
                if (!orderItemIds.contains(ItemId)) {
                    items.add(itemDao.findOne(ItemId));
                }
            }
            AddOrderItemForm form = new AddOrderItemForm(items, order);
            model.addAttribute("title","Add item to order: "+ order.getName());
            model.addAttribute("form", form);
        }
        return "order/add-item";
    }

    @RequestMapping(value ="add-item", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute @Valid AddOrderItemForm form, Errors errors) {

        if(errors.hasErrors()) {
            model.addAttribute("form", form);
            return "order/add-item";
        }
        Order order = orderDao.findOne(form.getOrderId());
        Item item = itemDao.findOne(form.getItemId());
        Integer qty = form.getOrderQuantity();
        OrderItem orderItem = new OrderItem(order, item, qty);
        order.setOrderItem(orderItem);
        orderItemDao.save(orderItem);
        orderDao.save(order);

        return "redirect:view/" + order.getId();

    }

    @RequestMapping(value = "update-item", method = RequestMethod.POST)
    public String updateOrderItem(@RequestParam(value = "orderItemQty") Integer orderItemQty,
            @RequestParam(value = "orderItemId") int orderItemId,
            @RequestParam(value = "orderId") int orderId) {

        OrderItem orderItem = orderItemDao.findOne(orderItemId);
        orderItem.setOrderQty(orderItemQty);
        orderItemDao.save(orderItem);

        return "redirect:view/" + orderId;
    }

    @RequestMapping(value = "remove-item", method =RequestMethod.POST)
    public String removeOrderItem(@RequestParam(value = "orderItemId") int orderItemId,
                                  @RequestParam(value = "orderId") int orderId) {
        Order order = orderDao.findOne(orderId);
        OrderItem orderItem = orderItemDao.findOne(orderItemId);
        order.removeOrderItem(orderItem);
        orderDao.save(order);
        orderItemDao.delete(orderItemId);

        return "redirect:view/" + orderId;
    }

    @RequestMapping(value = "receive-order", method = RequestMethod.POST)
    public String receiveOrder(@RequestParam(value = "orderId") int orderId) {
        Order order = orderDao.findOne(orderId);
        Item item;
        //should be handled by a model
        for (OrderItem orderItem : order.orderItems) {
            item = orderItem.getItem();
            Integer newQuantity = orderItem.getOrderQty() + item.getQuantity();
            item.setQuantity(newQuantity);
            itemDao.save(item);
        }
        order.deactivate();
        orderDao.save(order);
        return "redirect:/inventory";
    }

    @RequestMapping(value="autofill", method = RequestMethod.POST)
    public String autofillOrder(@RequestParam(value= "orderId") int orderId) {
        Order order = orderDao.findOne(orderId);

        for (Item item : itemDao.findAll()) {
            if (item.getMinimum() >= item.getQuantity()) {
                Integer qty = item.getMaximum() - item.getQuantity();
                OrderItem orderItem = new OrderItem(order, item, qty);
                orderItemDao.save(orderItem);
                order.setOrderItem(orderItem);
                orderDao.save(order);
            }
        }
        return "redirect:view/" + orderId;
    }

}
