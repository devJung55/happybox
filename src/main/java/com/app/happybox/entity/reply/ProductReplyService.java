package com.app.happybox.entity.reply;

import com.app.happybox.repository.reply.ProductReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("product")
public class ProductReplyService implements ReplyService {
    private final ProductReplyRepository productReplyRepository;

    @Override
    public List<ReplyDTO> findAllByRefId(Pageable pageable, Long id) {
        productReplyRepository.findAllByProductId(pageable, id);

        return null;
    }
}
