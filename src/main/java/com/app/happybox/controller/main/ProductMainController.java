package com.app.happybox.controller.main;

import com.app.happybox.service.board.BoardService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Controller
@RequiredArgsConstructor
@RequestMapping("/main/product")
@Slf4j
public class ProductMainController {

    @Qualifier("product")
    private final ProductService productService;
    @Qualifier("recipeBoard")
    private final RecipeBoardService recipeBoardService;


    @GetMapping("")
    public String goMain(Model model) {
        model.addAttribute("recent", productService.findTop8Recent());
        model.addAttribute("likeCount", productService.findTop8ReplyCount());
        model.addAttribute("randomProducts", productService.findRandomProducts());
        model.addAttribute("recipie", recipeBoardService.findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL());
        return "index/supply-market";
    }

}
