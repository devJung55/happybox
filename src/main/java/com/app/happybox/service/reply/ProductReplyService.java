package com.app.happybox.service.reply;

import com.app.happybox.entity.reply.ProductReply;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.repository.reply.ProductReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("product")
public class ProductReplyService implements ReplyService {
    private final ProductReplyRepository productReplyRepository;

    @Override
    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id) {
        Slice<ProductReply> productReplySlice = productReplyRepository.findAllByProductId(pageable, id);
        List<ReplyDTO> replyDTOList = productReplySlice
                .get()
                .map(this::replyToDTO)
                .collect(Collectors.toList());
        return new SliceImpl<>(replyDTOList, pageable, productReplySlice.hasNext());
    }
}
