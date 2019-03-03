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
        model.addAttribute("items", itemDao.findAll());
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
    public String processAddItemForm(@ModelAttribute @Valid Item newItem, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Item to Inventory");
            return "inventory/add";
        }

        itemDao.save(newItem);
        return "redirect:";
    }

    @RequestMapping(value="update", method = RequestMethod.GET)
    public String update(Model model) {
        model.addAttribute("items", itemDao.findAll());
        model.addAttribute("title", "My inventory");
        return "inventory/update";
    }

    @RequestMapping(value="update", method = RequestMethod.POST)
    public String updateItem(@RequestParam (value= "itemId") int itemId,
                             @RequestParam (value= "itemName") String itemName,
                             @RequestParam (value= "itemQty") Integer itemQty,
                             @RequestParam (value= "itemMin") Integer itemMin,
                             @RequestParam (value= "itemMax") Integer itemMax,
                             Model model) {

        Item item = itemDao.findOne(itemId);
        item.setName(itemName);
        item.setQuantity(itemQty);
        item.setMinimum(itemMin);
        item.setMaximum(itemMax);
        itemDao.save(item);

        return "redirect:/inventory/update";
    }
}
