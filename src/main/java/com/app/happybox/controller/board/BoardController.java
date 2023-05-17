package com.app.happybox.controller.board;

import com.app.happybox.entity.board.*;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.file.QBoardFile;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.repository.board.DonationBoardRepository;
import com.app.happybox.service.board.DonationBoardService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.board.ReviewBoardService;
import com.app.happybox.service.reply.ReplyLikeService;
import com.app.happybox.service.reply.ReviewBoardReplyService;
import com.app.happybox.service.subscript.SubscriptionService;
import com.app.happybox.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user-board/*")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    @Qualifier
    private final ReviewBoardService reviewBoardService;
    @Qualifier
    private final RecipeBoardService recipeBoardService;
    @Qualifier
    private final DonationBoardService donationBoardService;
    @Qualifier
    private final MemberService memberService;
    @Qualifier
    private final SubscriptionService subscriptionService;
    @Qualifier
    private final ReviewBoardReplyService reviewBoardReplyService;
    @Qualifier
    private final ReplyLikeService replyLikeService;

    @GetMapping("review-board-list")
    public String goRecentList(){
        return "user-board/review-board-list";
    }

    //    리뷰 게시판 리스트(인기순)
    @GetMapping("review-board-list/popular")
    @ResponseBody
    public Slice<ReviewBoardDTO> goPopularList(@PageableDefault(page=1, size=5) Pageable pageable) {
        Slice<ReviewBoardDTO> reviewBoardDTOS = reviewBoardService.getPopularReviewBoards(PageRequest.of(pageable.getPageNumber() - 1,
                        pageable.getPageSize()));
        return reviewBoardDTOS;
    }

    //    리뷰 게시판 리스트(최신순)
    @GetMapping("review-board-list/recent")
    @ResponseBody
    public Slice<ReviewBoardDTO> goRecentList(@PageableDefault(page=1, size=5) Pageable pageable) {
        Slice<ReviewBoardDTO> reviewBoardDTOS =
                reviewBoardService
                .getReviewBoards(PageRequest.of(pageable.getPageNumber() - 1,
                        pageable.getPageSize()));
        return reviewBoardDTOS;
    }


    //    리뷰 게시판 상세보기
    @GetMapping("review-board-detail/{id}")
    public String goReviewDetail(@PathVariable Long id, Model model){
        model.addAttribute("review", reviewBoardService.getDetail(id));
        return "user-board/review-board-detail";
    }

    //    리뷰 게시판 작성하기
    @GetMapping("review-board-insert")
    public void goToReviewWrite(Model model) {
        model.addAttribute("reviewBoard", new ReviewBoardDTO());
    }

    @PostMapping("review-board-insert")
    @ResponseBody
    public void ReviewWrite(@RequestBody ReviewBoardDTO reviewBoardDTO) {
        Long memberId = 1L;
        reviewBoardService.write(reviewBoardDTO, memberId);

        log.info("=====================" + reviewBoardDTO);
    }

    //    리뷰 게시판 수정하기
    @GetMapping("review-board-modify/{id}")
    public String goReviewModify(Model model, @PathVariable Long id){
        model.addAttribute("reviewBoardDTO", reviewBoardService.getDetail(id));
        return "user-board/review-board-modify";
    }



    //    댓글 목록
    @GetMapping("review-board-detail/reply/{id}")
    @ResponseBody
    public Slice<ReplyDTO> reviewReplies(@PageableDefault(page = 1, size = 5) Pageable pageable, @PathVariable Long id, Boolean isReviewByDate) {
        log.info(reviewBoardReplyService.findAllByRefId(
                PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()),
                id,
                isReviewByDate
        ).getContent().toString());
        return reviewBoardReplyService.findAllByRefId(
                PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()),
                id,
                isReviewByDate // 최신순 or 인기순
        );
    }


    //    댓글 작성
    @PostMapping("review-board-detail/reply/write/{reviewBoardId}")
    @ResponseBody
    public ReplyDTO writeReply(@RequestBody ReplyDTO replyDTO, @PathVariable Long reviewBoardId) {
        // 임시 session 값 1저장
        return reviewBoardReplyService.saveReply(replyDTO, reviewBoardId, 1L);
    }

    //    댓글 삭제
    @DeleteMapping("review-board-detail/reply/delete/{reviewBoardId}/{replyId}")
    public String deleteReply(@PathVariable Long replyId, @PathVariable Long reviewBoardId) {
        // 임시 session 값 1저장
         reviewBoardReplyService.deleteReply(replyId, reviewBoardId, 1L);
         log.info("===============들어옴");
         return "user-board/review-board-detail";
    }
    //  댓글 좋아요
    @PostMapping("review-board-detail/reply/like/{replyId}")
    @ResponseBody
    public boolean checkLike(@PathVariable Long replyId) {
        log.info("================== 들어옴 ===============");
        // 임시 session 값 1
        return replyLikeService.checkOutLike(replyId, 1L);
    }

    /* ==================================================================== */

    //    레시피 게시판 리스트 (최신순)
    @GetMapping("recipe-board-list/recent")
    public Slice<RecipeBoardDTO> getRecipeBoardList(@PageableDefault(page=1, size=5) Pageable pageable) {
        return recipeBoardService.getRecipeBoards(PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize()));
    }

    //    레시피 게시판 상세보기
    @GetMapping("recipe-board-detail/{id}")
    public String goRecipeDetail(@PathVariable Long id, Model model){
        model.addAttribute("recipe", recipeBoardService.getRecipeBoardDetailById(id));
        return "user-board/recipe-board-detail";
    }

    //    레시피 게시판 작성하기
    @GetMapping("recipe-board-insert")
    public void goToRecipeWrite(ReviewBoardDTO reviewBoardDTO) { }

    @PostMapping("recipe-board-insert")
    public RedirectView RecipeWrite(@ModelAttribute("recipeBoardDTO") RecipeBoardDTO recipeBoardDTO/*, @AuthenticationPrincipal UserDetail userDetail*/) {
//        Long memberId = userDetail.getId();
        Long memberId = 1L;
//        recipeBoardService.write(recipeBoardDTO, memberId);
        log.info(recipeBoardDTO.toString());
        return new RedirectView("/user-board/recipe-board-list");
    }

    //    기부 게시판 리스트
    @GetMapping("donate-list")
    public Page<DonationBoardDTO> getDonateBoardList(int page, int size) {
        return donationBoardService.getList(PageRequest.of(page, size));
    }

}
