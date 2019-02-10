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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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
        model.addAttribute("title", "View Orders");
        model.addAttribute("orders",orderDao.findAll());
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
        AddOrderItemForm form = new AddOrderItemForm(itemDao.findAll(), order);
        model.addAttribute("title","Add item to order: "+ order.getName());
        model.addAttribute("form", form);
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

}
