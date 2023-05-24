package com.app.happybox.service.reply;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.reply.RecipeBoardReply;
import com.app.happybox.entity.reply.Reply;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.reply.ReviewBoardReply;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.ProductNotFoundException;
import com.app.happybox.exception.ReplyNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.board.RecipeBoardRepository;
import com.app.happybox.repository.reply.RecipeBoardReplyRepository;
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
@Qualifier("RecipeBoard")
public class RecipeBoardReplyService implements ReplyService {
    private final RecipeBoardReplyRepository recipeBoardReplyRepository;
    private final RecipeBoardRepository recipeBoardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Slice<ReplyDTO> findAllByRefId(Pageable pageable, Long id, Boolean isOrderByDate) {
        // 최신순 인기순 정렬 메소드 따로 만들기 (Query이용)
        Slice<RecipeBoardReply> recipeBoardReplySlice = null;
        isOrderByDate = isOrderByDate == null ? true : isOrderByDate;

        if(isOrderByDate) {
            recipeBoardReplySlice = recipeBoardReplyRepository.findAllByRecipeBoardId(pageable, id);
        } else recipeBoardReplySlice = recipeBoardReplyRepository.findAllByRecipeBoardIdOrderByLikeCount(pageable, id);

        List<ReplyDTO> replyDTOList = recipeBoardReplySlice
                .get()
                .map(this::replyToDTO)
                .collect(Collectors.toList());
        return new SliceImpl<>(replyDTOList, pageable, recipeBoardReplySlice.hasNext());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReplyDTO saveReply(ReplyDTO replyDTO, Long refId, Long userId) {
        RecipeBoardReply reply = recipeBoardReplyRepository.save(replyToEntity(replyDTO, refId, userId));
        RecipeBoard recipeBoard = recipeBoardRepository.findById(refId).orElseThrow(ProductNotFoundException::new);

        // 상품 댓글 수 컬럼의 값을 증가시켜 준다
        int replyCount = recipeBoard.getRecipeBoardReplyCount() + 1;
        recipeBoard.setRecipeBoardReplyCount(replyCount);

        return replyToDTO(reply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteReply(Long replyId, Long refId, Long userId) {
        recipeBoardReplyRepository.deleteById(replyId);
        RecipeBoard recipeBoard = recipeBoardRepository.findById(refId).orElseThrow(ProductNotFoundException::new);

        int replyCount = recipeBoard.getRecipeBoardReplyCount() - 1;
        recipeBoard.setRecipeBoardReplyCount(replyCount);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReplyDTO updateReply(Long replyId, ReplyDTO replyDTO) {
        RecipeBoardReply recipeBoardReply = recipeBoardReplyRepository.findById(replyId).orElseThrow(ReplyNotFoundException::new);
        recipeBoardReply.setReplyContent(replyDTO.getReplyContent());

        return replyToDTO(recipeBoardReply);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RecipeBoardReply replyToEntity(ReplyDTO replyDTO, Long refId, Long userId) {
        RecipeBoard recipeBoard = recipeBoardRepository.findById(refId).orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return RecipeBoardReply.builder()
                .recipeBoard(recipeBoard)
                .replyContent(replyDTO.getReplyContent())
                .user(user)
                .build();
    }
}
