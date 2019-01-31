package com.example.inventorymanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class RerouteController {

    @RequestMapping("")
    public String reroute() { return "redirect:/inventory";}
}
