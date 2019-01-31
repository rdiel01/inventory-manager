package com.example.inventorymanager.controllers;


import com.example.inventorymanager.models.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("inventory")
public class InventoryController {

    @RequestMapping(value="")
    public String index(Model model) {
        model.addAttribute("title", "My inventory");
        return "inventory/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddItemForm(Model model) {
        model.addAttribute("title","Add Item to Inventory");
        model.addAttribute(new Item());
        return "inventory/add";
    }

}
