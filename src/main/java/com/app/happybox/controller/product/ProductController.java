package com.app.happybox.controller.product;

import com.app.happybox.entity.product.ProductCartDTO;
import com.app.happybox.entity.product.ProductDTO;
import com.app.happybox.entity.product.ProductSearch;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.service.product.ProductCartService;
import com.app.happybox.service.product.ProductService;
import com.app.happybox.service.reply.ProductReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Qualifier("product")
    private final ProductService productService;
    @Qualifier("product")
    private final ProductReplyService productReplyService;
    @Qualifier("product")
    private final ProductCartService productCartService;

    @GetMapping("/list")
    public String goSearchForm() {
        return "market/market-list";
    }

    @GetMapping("/search")
    @ResponseBody
    public Page<ProductDTO> searchProducts(@PageableDefault(size = 9) Pageable pageable, ProductSearch productSearch) {
        Page<ProductDTO> result = productService.findAllBySearch(
                PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize())
                , productSearch
        );
        return result;
    }

    @GetMapping("/detail/{id}")
    public String goDetail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "market/market-detail";
    }

    @GetMapping("/detail/reply/{id}")
    @ResponseBody
    public Slice<ReplyDTO> productReplies(@PageableDefault(size = 5) Pageable pageable, @PathVariable Long id) {
        return productReplyService.findAllByRefId(
                PageRequest.of(pageable.getPageNumber() - 1,
                        pageable.getPageSize()), id
        );
    }

    @PostMapping("/cart/add/{productId}")
    @ResponseBody
    public Long registerCart(@RequestBody ProductCartDTO productCartDTO, @PathVariable Long productId) {
        // 임시로 회원아이디 1L 넣어둠, 추후 변경
        return productCartService.saveCart(productCartDTO, 1L, productId);
    }
}
