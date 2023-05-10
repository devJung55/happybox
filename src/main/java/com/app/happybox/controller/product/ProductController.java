package com.app.happybox.controller.product;

import com.app.happybox.entity.product.ProductDTO;
import com.app.happybox.entity.product.ProductSearch;
import com.app.happybox.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Qualifier("product")
    private final ProductService productService;

    @GetMapping("/list")
    public String goSearchForm() {
        return "market/market-list";
    }

    @GetMapping("/search")
    @ResponseBody
    public Page<ProductDTO> searchProducts(int page, int size, ProductSearch productSearch) {
        Page<ProductDTO> result = productService.findAllBySearch(PageRequest.of(page - 1, size), productSearch);
        return result;
    }
}
