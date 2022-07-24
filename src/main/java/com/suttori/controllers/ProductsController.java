package com.suttori.controllers;

import com.suttori.dao.ProductDAO;
import com.suttori.dao.UserDAO;
import com.suttori.models.Buy;
import com.suttori.models.Product;
import com.suttori.util.ProductValidator;
import com.suttori.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private final UserValidator userValidator;
    private final ProductValidator productValidator;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;

    @Autowired
    public ProductsController(UserValidator userValidator, ProductValidator productValidator, ProductDAO productDAO, UserDAO userDAO) {
        this.userValidator = userValidator;
        this.productValidator = productValidator;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    @GetMapping()
    public String indexProduct(Model model) {
        model.addAttribute("products", productDAO.indexProduct());
        return "products/index";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productDAO.showProduct(id));
        return "products/show";
    }

    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Product product) {
        return "products/new";
    }

    @PostMapping()
    public String createProduct(@ModelAttribute("product") @Valid Product product,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "products/new";
        }
        productDAO.saveProduct(product);
        return "redirect:/menu/success/";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productDAO.deleteProduct(id);
        return "redirect:/menu/success/";
    }

    @PostMapping("/buy")
    public String buy(@ModelAttribute("buy") @Valid Buy buy, BindingResult bindingResult)  {
        userValidator.validate(buy, bindingResult);
        productValidator.validate(buy, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/buy";
        }
        if (userDAO.takeAmountOfMoney(buy.getUserId()) > productDAO.takePrice(buy.getProductId())) {
            int result = userDAO.takeAmountOfMoney(buy.getUserId()) - productDAO.takePrice(buy.getProductId());
            userDAO.update(buy.getUserId(), result);
            productDAO.buy(buy);
            return "redirect:/menu/";
        } else {
            throw new RuntimeException();
        }
    }
    @ExceptionHandler(RuntimeException.class)
    public String databaseError() {
        return "redirect:/menu/notEnoughMoney";
    }

    @GetMapping("/display")
    public String display(@ModelAttribute("buy") Buy buy) {
        return "products/display";
    }

    @PostMapping("/display")
    public String showProduct(@ModelAttribute("buy") @Valid Buy buy,
                              BindingResult bindingResult, Model model) {
        productValidator.validate(buy, bindingResult);
        if (bindingResult.hasErrors()) {
            return "products/display";
        }
        ArrayList<String> listUsers = productDAO.display(buy.getProductId());
        model.addAttribute("listUsers", listUsers);
        return "products/showUser";
    }

    @PostMapping("/showUser/")
    public String displayShow(@ModelAttribute("buy") Buy buy) {
        return "products/showUser";
    }
}
