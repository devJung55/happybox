package com.app.happybox.controller.product;

import com.app.happybox.entity.product.ProductDTO;
import com.app.happybox.entity.product.ProductSearch;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.service.product.ProductService;
import com.app.happybox.service.reply.ProductReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Qualifier("product")
    private final ProductService productService;
    @Qualifier("product")
    private final ProductReplyService productReplyService;

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

    @GetMapping("/detail/{id}")
    public String goDetail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "market/market-detail";
    }

    @GetMapping("/detail/reply/{id}")
    @ResponseBody
    public Slice<ReplyDTO> productReplies(int page, int size, @PathVariable Long id) {
        return productReplyService.findAllByRefId(PageRequest.of(page, size), id);
    }
}
