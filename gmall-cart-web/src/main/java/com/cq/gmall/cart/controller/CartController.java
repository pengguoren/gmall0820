package com.cq.gmall.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 彭国仁
 * @data 2019/9/11 17:20
 */
@Controller
public class CartController {

    @RequestMapping("addToCart")
    public String addToCart() {
        return "redirect://success.html";
    }
}
