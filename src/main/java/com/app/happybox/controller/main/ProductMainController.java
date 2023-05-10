package com.app.happybox.controller.main;

import com.app.happybox.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main/product")
@Slf4j
public class ProductMainController {

    @Qualifier("product")
    private final ProductService productService;

    @GetMapping("")
    public String goMain(Model model) {
        model.addAttribute("recent", productService.findTop8Recent());
        return "index/supply-market";
    }
}
