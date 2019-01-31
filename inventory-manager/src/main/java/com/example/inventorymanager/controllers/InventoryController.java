package com.example.inventorymanager.controllers;


import com.example.inventorymanager.models.Item;
import com.example.inventorymanager.models.data.ItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    private ItemDao itemDao;

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

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String ProcessAddItemForm(@ModelAttribute @Valid Item newItem, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Item to Inventory");
            return "inventory/add";
        }

        itemDao.save(newItem);
        return "redirect:";
    }
}
