package com.suttori.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @GetMapping()
    public String menu() {
        return "menu";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/notEnoughMoney")
    public String notEnoughMoney() {
        return "notEnoughMoney";
    }
}

