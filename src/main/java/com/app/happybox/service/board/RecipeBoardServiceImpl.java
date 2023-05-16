package com.app.happybox.service.board;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.repository.board.RecipeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("recipeBoard")
public class RecipeBoardServiceImpl implements RecipeBoardService {
    private final RecipeBoardRepository recipeBoardRepository;

    @Override
    public Slice<RecipeBoardDTO> getRecipeBoards(Pageable pageable) {
        Slice<RecipeBoard> recipeBoards =
                recipeBoardRepository.findAllByIdDescWithPaging_QueryDSL(PageRequest.of(0, 10));
        List<RecipeBoardDTO> collect = recipeBoards.get().map(board -> recipeBoardToDTO(board)).collect(Collectors.toList());
        return new SliceImpl<>(collect, pageable, recipeBoards.hasNext());
    }

    @Override
    public Slice<RecipeBoardDTO> getPopularRecipeBoards(Pageable pageable) {
        Slice<RecipeBoard> recipeBoards =
                recipeBoardRepository.findAllByLikeCountDescWithPaging_QueryDSL(PageRequest.of(0, 10));
        List<RecipeBoardDTO> collect = recipeBoards.get().map(board -> recipeBoardToDTO(board)).collect(Collectors.toList());

        return new SliceImpl<>(collect, pageable, recipeBoards.hasNext());
    }

    @Override
    public Page<RecipeBoardDTO> getListByMemberId(Pageable pageable, Long memberId) {
        Page<RecipeBoard> recipeBoardList = recipeBoardRepository.findRecipeBoardListByMemberIdWithPaging_QueryDSL(pageable, memberId);
        List<RecipeBoardDTO> recipeBoardDTOList = recipeBoardList.get().map(this::mypageRecipeBoardToDTO).collect(Collectors.toList());

        return new PageImpl<>(recipeBoardDTOList, pageable, recipeBoardList.getTotalElements());
    }

    @Override
    public List<RecipeBoard> findRecipeBoardReplyCountByMemberId_QueryDSL(Long memberId) {
        return null;
    }

    @Override
    public List<RecipeBoardDTO> findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL() {
        return recipeBoardRepository.findTop5ByLikeCountWithRepresentFileOrderByLikeCount_QueryDSL()
                .stream()
                .map(this::recipeBoardToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long getCountByMemberId(Long memberId) {
        Long count = recipeBoardRepository.findRecipeBoardCountByIdMemberId_QueryDSL(memberId);
        return count;
    }

    @Override
    public Page<RecipeBoardDTO> getList(Pageable pageable) {
        Page<RecipeBoard> recipeBoardList = recipeBoardRepository.findRecipeBoardListDescWithPaging_QueryDSL(pageable);
        List<RecipeBoardDTO> recipeBoardDTOList = recipeBoardList.get().map(this::mypageRecipeBoardToDTO).collect(Collectors.toList());

        return new PageImpl<>(recipeBoardDTOList, pageable, recipeBoardList.getTotalElements());
    }

    @Override
    public Optional<RecipeBoardDTO> getRecipeBoardDetailById(Long recipeBoardId) {
        Optional<RecipeBoardDTO> recipeBoardDTO = recipeBoardRepository.findById_QueryDSL(recipeBoardId).map(this::mypageRecipeBoardToDTO);
        return recipeBoardDTO;
    }
}
