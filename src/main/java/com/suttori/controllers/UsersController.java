package com.suttori.controllers;

import com.suttori.dao.UserDAO;
import com.suttori.models.Buy;
import com.suttori.models.User;
import com.suttori.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDAO userDAO;
    private final UserValidator userValidator;

    @Autowired
    public UsersController(UserDAO userDAO, UserValidator userValidator) {
        this.userDAO = userDAO;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userDAO.index());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDAO.show(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        userDAO.save(user);
        return "redirect:/menu/success/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userDAO.delete(id);
        return "redirect:/menu/success/";
    }

    @GetMapping("/buy")
    public String buyProduct(@ModelAttribute("buy") Buy buy) {
        return "users/buy";
    }

    @GetMapping("/display")
    public String display(@ModelAttribute("buy") Buy buy) {
        return "users/display";
    }

    @PostMapping("/display")
    public String showProduct(@ModelAttribute("buy") @Valid Buy buy,
                              BindingResult bindingResult, Model model) {
        userValidator.validate(buy, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/display";
        }
        ArrayList<String> listProducts = userDAO.display(buy.getUserId());
        model.addAttribute("listProducts", listProducts);
        return "users/showProduct";
    }

    @PostMapping("/showProduct/")
    public String displayShow(@ModelAttribute("buy") Buy buy) {
        return "users/showProduct";
    }
}
