package com.app.happybox.service.reply;

import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.reply.ProductReply;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.ProductNotFoundException;
import com.app.happybox.exception.ReplyNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.product.ProductRepository;
import com.app.happybox.repository.reply.ProductReplyRepository;
import com.app.happybox.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("product")
public class ProductReplyService implements ReplyService {
    private final ProductRepository productRepository;
    private final ProductReplyRepository productReplyRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id, Boolean isOrderByDate) {
        Slice<ProductReply> productReplySlice = null;
        isOrderByDate = isOrderByDate == null ? true : isOrderByDate;
        if(isOrderByDate) {
            productReplySlice = productReplyRepository.findAllByProductIdOrderByCreatedDate(pageable, id);
        } else productReplySlice = productReplyRepository.findAllByProductIdOrderByLikeCount(pageable, id);

        List<ReplyDTO> replyDTOList = productReplySlice
                .get()
                .map(this::replyToDTO)
                .collect(Collectors.toList());
        return new SliceImpl<>(replyDTOList, pageable, productReplySlice.hasNext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReplyDTO saveReply(ReplyDTO replyDTO, Long refId, Long userId) {
        ProductReply reply = productReplyRepository.save(replyToEntity(replyDTO, refId, userId));
        Product product = productRepository.findById(refId).orElseThrow(ProductNotFoundException::new);

        // 상품 댓글 수 컬럼의 값을 증가시켜 준다
        int replyCount = product.getProductReplyCount() + 1;
        product.setProductReplyCount(replyCount);

        return replyToDTO(reply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReply(Long replyId, Long refId, Long userId) {
        productReplyRepository.deleteById(replyId);
        Product product = productRepository.findById(refId).orElseThrow(ProductNotFoundException::new);

        int replyCount = product.getProductReplyCount() - 1;
        product.setProductReplyCount(replyCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReplyDTO updateReply(Long replyId, ReplyDTO replyDTO) {
        ProductReply productReply = productReplyRepository.findById(replyId).orElseThrow(ReplyNotFoundException::new);
        productReply.setReplyContent(replyDTO.getReplyContent());

        return replyToDTO(productReply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductReply replyToEntity(ReplyDTO replyDTO, Long refId, Long userId) {
        Product product = productRepository.findById(refId).orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return ProductReply.builder()
                .product(product)
                .replyContent(replyDTO.getReplyContent())
                .user(user)
                .build();
    }
}
