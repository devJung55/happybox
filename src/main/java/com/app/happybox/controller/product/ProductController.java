package com.app.happybox.controller.product;

import com.app.happybox.domain.product.ProductCartDTO;
import com.app.happybox.domain.product.ProductDTO;
import com.app.happybox.domain.product.ProductSearchDTO;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.service.product.ProductCartService;
import com.app.happybox.service.product.ProductService;
import com.app.happybox.service.reply.ProductReplyService;
import com.app.happybox.service.reply.ReplyLikeService;
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
    @Qualifier("like")
    private final ReplyLikeService replyLikeService;

    @GetMapping("/list")
    public String goSearchForm() {
        return "market/market-list";
    }

    @GetMapping("/search")
    @ResponseBody
    public Page<ProductDTO> searchProducts(@PageableDefault(page = 1, size = 9) Pageable pageable, ProductSearchDTO productSearchDTO) {
        Page<ProductDTO> result = productService.findAllBySearch(
                PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize())
                , productSearchDTO
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
    public Slice<ReplyDTO> productReplies(@PageableDefault(page = 1, size = 5) Pageable pageable, @PathVariable Long id, Boolean isOrderByDate) {

        return productReplyService.findAllByRefId(
                PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()),
                id,
                isOrderByDate // 최신순 or 인기순
        );
    }

    @PostMapping("/detail/reply/write/{productId}")
    @ResponseBody
    public ReplyDTO writeReply(@RequestBody ReplyDTO replyDTO, @PathVariable Long productId) {
        // 임시 session 값 1저장
        return productReplyService.saveReply(replyDTO, productId, 1L);
    }

    @PostMapping("/detail/reply/like/{replyId}")
    @ResponseBody
    public boolean addLike(@PathVariable Long replyId) {
        // 임시 session 값 1
        return replyLikeService.checkOutLike(replyId, 1L);
    }

    @PostMapping("/cart/add/{productId}")
    @ResponseBody
    public Long registerCart(@RequestBody ProductCartDTO productCartDTO, @PathVariable Long productId) {
        // 임시로 회원이디 1L 넣어둠, 추후 변경
        return productCartService.saveCart(productCartDTO, 1L, productId);
    }
}
