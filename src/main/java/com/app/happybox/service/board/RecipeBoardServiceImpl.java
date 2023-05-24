package com.app.happybox.service.board;

import com.app.happybox.entity.board.RecipeBoard;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.board.ReviewBoard;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.file.BoardFile;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.BoardNotFoundException;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.repository.board.BoardFileRepository;
import com.app.happybox.repository.board.RecipeBoardRepository;
import com.app.happybox.repository.user.MemberRepository;
import com.app.happybox.type.FileRepresent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier("recipeBoard")
public class RecipeBoardServiceImpl implements RecipeBoardService {
    private final RecipeBoardRepository recipeBoardRepository;
    private final MemberRepository memberRepository;
    private final BoardFileRepository boardFileRepository;

    @Override
    public RecipeBoardDTO getDetail(Long id) {
        RecipeBoard recipeBoard = recipeBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        return recipeBoardToDTO(recipeBoard);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void write(RecipeBoardDTO recipeBoardDTO, Long memberId) {
        List<BoardFileDTO> boardFileDTOS = recipeBoardDTO.getRecipeBoardFiles();
        // 아이디 조회 실패 시 Exception
        Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);

        // 게시판에 setMember
        RecipeBoard recipeBoard = toRecipeBoardEntity(recipeBoardDTO);
        recipeBoard.setMember(member);

        // recipeBoard 영속화
        recipeBoardRepository.save(recipeBoard);

        log.info(boardFileDTOS.toString());

        int index = 0;

        // boardFile recipeBoard set 후 영속화
        for (int i = 0; i < boardFileDTOS.size(); i++) {
            BoardFile boardFile = toBoardFileEntity(boardFileDTOS.get(i));
            if(index < 1) boardFile.setFileRepresent(FileRepresent.REPRESENT);

            boardFile.setRecipeBoard(recipeBoard);
            boardFileRepository.save(boardFile);

            index++;
        }
    }

    @Override @Transactional
    public void update(RecipeBoardDTO recipeBoardDTO, Long memberId) {
        List<BoardFileDTO> boardFileDTOS = recipeBoardDTO.getRecipeBoardFiles();

        RecipeBoard recipeBoard = recipeBoardRepository.findById(recipeBoardDTO.getId()).orElseThrow(BoardNotFoundException::new);

        recipeBoard.setBoardTitle(recipeBoardDTO.getBoardTitle());
        recipeBoard.setBoardContent(recipeBoardDTO.getBoardContent());

        // 기존파일 삭제
        Long deleteCount = boardFileRepository.deleteByRecipeBoardId(recipeBoardDTO.getId());

        log.info("============== {} ============", deleteCount);

//        reviewBoardDTO.getReviewBoardFiles().forEach(file -> {
//            log.info("============ 파일 반복문 ===========");
//            // 새로운 파일
//            BoardFile boardFile = toBoardFileEntity(file);
//
//
//            boardFile.setReviewBoard(reviewBoard);
//            boardFileRepository.save(boardFile);
//        });
        int count = 0;

        for (int i = 0; i < boardFileDTOS.size(); i++) {
            if(boardFileDTOS.get(i) == null) continue;

            if (count == 0) {
                boardFileDTOS.get(i).setFileRepresent(FileRepresent.REPRESENT);
                count++;
            } else {
                boardFileDTOS.get(i).setFileRepresent(FileRepresent.ORDINARY);
            }

            boardFileDTOS.get(i).setRecipeBoardDTO(recipeBoardToDTO(getCurrentSequence()));
            // 엔티티
            BoardFile boardFile = toBoardFileEntity(boardFileDTOS.get(i));

            boardFile.setRecipeBoard(recipeBoard);

            boardFileRepository.save(boardFile);
        }

    }

    @Override
    public void delete(Long id, Long memberId) {
        RecipeBoard recipeBoard = recipeBoardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        recipeBoardRepository.delete(recipeBoard);
    }

    @Override
    public RecipeBoard getCurrentSequence() {
        return recipeBoardRepository.getCurrentSequence_QueryDsl();
    }

    @Override
    public Slice<RecipeBoardDTO> getRecipeBoards(Pageable pageable) {
        Slice<RecipeBoard> recipeBoards =
                recipeBoardRepository.findAllByIdDescWithPaging_QueryDSL(pageable);
        List<RecipeBoardDTO> collect = recipeBoards.get().map(board -> recipeBoardToDTO(board)).collect(Collectors.toList());
        return new SliceImpl<>(collect, pageable, recipeBoards.hasNext());
    }

    @Override
    public Slice<RecipeBoardDTO> getPopularRecipeBoards(Pageable pageable) {
        Slice<RecipeBoard> recipeBoards =
                recipeBoardRepository.findAllByLikeCountDescWithPaging_QueryDSL(pageable);
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
