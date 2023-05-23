package com.app.happybox.controller.board;

import com.app.happybox.entity.board.*;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.reply.ReplyDTO;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.repository.board.DonationBoardRepository;
import com.app.happybox.service.board.*;
import com.app.happybox.service.reply.RecipeBoardReplyService;
import com.app.happybox.service.reply.ReplyLikeService;
import com.app.happybox.service.reply.ReviewBoardReplyService;
import com.app.happybox.service.subscript.SubscriptionService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.type.Role;
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

    private final ReviewBoardService reviewBoardService;
    private final RecipeBoardService recipeBoardService;
    private final DonationBoardService donationBoardService;
    private final MemberService memberService;
    private final SubscriptionService subscriptionService;
    private final ReviewBoardReplyService reviewBoardReplyService;
    private final ReplyLikeService replyLikeService;
    private final RecipeBoardReplyService recipeBoardReplyService;
    private final ReviewBoardLikeService reviewBoardLikeService;
    private final RecipeBoardLikeService recipeBoardLikeService;

    //    리뷰 게시판 이동
    @GetMapping("review-board-list")
    public String goReviewList(Model model, @AuthenticationPrincipal UserDetail userDetail){
        model.addAttribute("userId", userDetail.getId());
        return "user-board/review-board-list";
    }

    //    리뷰 게시판 리스트(인기순)
    @GetMapping("review-board-list/popular")
    @ResponseBody
    public Slice<ReviewBoardDTO> goReviewPopularList(@PageableDefault(page=1, size=5) Pageable pageable) {
        Slice<ReviewBoardDTO> reviewBoardDTOS = reviewBoardService.getPopularReviewBoards(PageRequest.of(pageable.getPageNumber() - 1,
                        pageable.getPageSize()));
        return reviewBoardDTOS;
    }

    //    리뷰 게시판 리스트(최신순)
    @GetMapping("review-board-list/recent")
    @ResponseBody
    public Slice<ReviewBoardDTO> goReviewRecentList(@PageableDefault(page=1, size=5) Pageable pageable) {
        Slice<ReviewBoardDTO> reviewBoardDTOS =
                reviewBoardService
                .getReviewBoards(PageRequest.of(pageable.getPageNumber() - 1,
                        pageable.getPageSize()));
        return reviewBoardDTOS;
    }


    //    리뷰 게시판 상세보기
    @GetMapping("review-board-detail/{id}")
    public String goReviewDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetail userDetail){
        model.addAttribute("review", reviewBoardService.getDetail(id));

        // 좋아요 이미 눌렀는지 검사
        model.addAttribute("isLike", reviewBoardLikeService.checkLike(id, userDetail.getId()));
        if(userDetail != null) {
            model.addAttribute("userId", userDetail.getUserId());
            model.addAttribute("userRole", userDetail.getUserRole());
        }

        log.info(userDetail.getId().toString());
        return "user-board/review-board-detail";
    }

    //    리뷰 게시판 작성하기
    @GetMapping("review-board-insert")
    public void goReviewWrite(Model model) {
        model.addAttribute("reviewBoard", new ReviewBoardDTO());
    }

    @PostMapping("review-board-insert")
    @ResponseBody
    public void reviewWrite(@RequestBody ReviewBoardDTO reviewBoardDTO, @AuthenticationPrincipal UserDetail userDetail) {

        Long userId = userDetail.getId();
        reviewBoardService.write(reviewBoardDTO, userId);

        log.info("=====================" + reviewBoardDTO);
    }

    //    리뷰 게시판 수정하기
    @GetMapping("review-board-modify/{id}")
    public String goReviewModify(Model model, @PathVariable Long id){
        model.addAttribute("reviewBoardDTO", reviewBoardService.getDetail(id));
        return "user-board/review-board-modify";
    }

    @PostMapping("review-board-modify")
    @ResponseBody
    public String goReviewModify(@RequestBody ReviewBoardDTO reviewBoardDTO, @AuthenticationPrincipal UserDetail userDetail) {

        log.info(reviewBoardDTO.toString());

        reviewBoardService.update(reviewBoardDTO, userDetail.getId());
        log.info(reviewBoardDTO.getId().toString());
        return "/user-board/review-board-detail/" + reviewBoardDTO.getId();
    }

    //    리뷰 게시글 삭제
    @DeleteMapping("review-board-detail/delete/{id}")
    public String deleteReviewBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetail userDetail) {
        // 임시 session 값 1저장
        reviewBoardService.delete(id, userDetail.getId());
        log.info("===============들어옴");
        return "user-board/review-board-list";
    }

    //    리뷰 게시글 좋아요
    @PostMapping("review-board-detail/like/{id}")
    @ResponseBody
    public boolean checkLike(@PathVariable Long id, @AuthenticationPrincipal UserDetail userDetail) {
        return reviewBoardLikeService.checkOutLike(id, userDetail.getId());
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


    //    리뷰 댓글 작성
    @PostMapping("review-board-detail/reply/write/{reviewBoardId}")
    @ResponseBody
    public ReplyDTO writeReviewReply(@RequestBody ReplyDTO replyDTO, @PathVariable Long reviewBoardId, @AuthenticationPrincipal UserDetail userDetail) {
        if(userDetail != null) {
            // 유통업자 제외
            if(userDetail.getUserRole() == Role.DISTRIBUTOR) {
                return null;
            }
            reviewBoardReplyService.saveReply(replyDTO, reviewBoardId, userDetail.getId());
        }
        // 비로그인 제외
        return null;
    }

    //    리뷰 댓글 수정
    @PatchMapping("review-board-detail/reply/modify/{replyId}")
    @ResponseBody
    public ReplyDTO modifyReply(@RequestBody ReplyDTO replyDTO, @PathVariable Long replyId) {
        ReplyDTO updatedReply = reviewBoardReplyService.updateReply(replyId, replyDTO);
        return updatedReply;
    }

    //    리뷰 댓글 삭제
    @DeleteMapping("review-board-detail/reply/delete/{reviewBoardId}/{replyId}")
    public String deleteReviewReply(@PathVariable Long replyId, @PathVariable Long reviewBoardId,  @AuthenticationPrincipal UserDetail userDetail) {
        Long userId = userDetail.getId();
        // 임시 session 값 1저장
         reviewBoardReplyService.deleteReply(replyId, reviewBoardId, userId);
         log.info("===============들어옴");
         return "user-board/review-board-detail";
    }

    //  리뷰 댓글 좋아요
    @PostMapping("review-board-detail/reply/like/{replyId}")
    @ResponseBody
    public boolean checkReviewReplyLike(@PathVariable Long replyId,  @AuthenticationPrincipal UserDetail userDetail) {
        Long userId = userDetail.getId();
        log.info("================== 들어옴 ===============");
        // 임시 session 값 1
        return replyLikeService.checkOutLike(replyId, userId);
    }

    /* ==================================================================== */

    //    레시피 게시판 이동
    @GetMapping("recipe-board-list")
    public String goRecipeList(Model model, @AuthenticationPrincipal UserDetail userDetail){
        model.addAttribute("userId", userDetail.getId());
        return "user-board/recipe-board-list";
    }

    //    레시피 게시판 리스트(인기순)
    @GetMapping("recipe-board-list/popular")
    @ResponseBody
    public Slice<RecipeBoardDTO> goRecipePopularList(@PageableDefault(page=1, size=5) Pageable pageable) {
        Slice<RecipeBoardDTO> recipeBoardDTOS = recipeBoardService.getPopularRecipeBoards(PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize()));
        return recipeBoardDTOS;
    }

    //    레시피 게시판 리스트(최신순)
    @GetMapping("recipe-board-list/recent")
    @ResponseBody
    public Slice<RecipeBoardDTO> goRecipeRecentList(@PageableDefault(page=1, size=5) Pageable pageable) {
        Slice<RecipeBoardDTO> recipeBoardDTOS =
                recipeBoardService
                        .getRecipeBoards(PageRequest.of(pageable.getPageNumber() - 1,
                                pageable.getPageSize()));
        return recipeBoardDTOS;
    }

    //    레시피 게시판 상세보기
    @GetMapping("recipe-board-detail/{id}")
    public String goRecipeDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetail userDetail){
        model.addAttribute("recipe", recipeBoardService.getDetail(id));
        // 좋아요 이미 눌렀는지 검사
        model.addAttribute("isLike", recipeBoardLikeService.checkLike(id, userDetail.getId()));
        return "user-board/recipe-board-detail";
    }

    //    레시피 게시글 좋아요
    @PostMapping("recipe-board-detail/like/{id}")
    @ResponseBody
    public boolean checkRecipeLike(@PathVariable Long id, @AuthenticationPrincipal UserDetail userDetail) {
        return recipeBoardLikeService.checkOutLike(id, userDetail.getId());
    }

    //    레시피 게시판 작성하기
    @GetMapping("recipe-board-insert")
    public void goRecipeWrite(Model model) {
        model.addAttribute("recipeBoard", new RecipeBoardDTO());
    }

    @PostMapping("recipe-board-insert")
    @ResponseBody
    public void recipeWrite(@RequestBody RecipeBoardDTO recipeBoardDTO, @AuthenticationPrincipal UserDetail userDetail) {
        Long userId = userDetail.getId();
        recipeBoardService.write(recipeBoardDTO, userId);

        log.info("=====================" + recipeBoardDTO);
    }

    //    레시피 게시판 수정하기
    @GetMapping("recipe-board-modify/{id}")
    public String goRecipeModify(Model model, @PathVariable Long id){
        model.addAttribute("recipeBoardDTO", recipeBoardService.getDetail(id));
        return "user-board/recipe-board-modify";
    }

    @PostMapping("recipe-board-modify")
    @ResponseBody
    public String goRecipeModify(@RequestBody RecipeBoardDTO recipeBoardDTO, @AuthenticationPrincipal UserDetail userDetail) {
        Long userId = userDetail.getId();

        log.info(recipeBoardDTO.toString());
        recipeBoardService.update(recipeBoardDTO, userId);
        log.info(recipeBoardDTO.getId().toString());
        return "/user-board/recipe-board-detail/" + recipeBoardDTO.getId();
    }

    //    댓글 목록
    @GetMapping("recipe-board-detail/reply/{id}")
    @ResponseBody
    public Slice<ReplyDTO> recipeReplies(@PageableDefault(page = 1, size = 5) Pageable pageable, @PathVariable Long id, Boolean isRecipeByDate) {
        log.info(recipeBoardReplyService.findAllByRefId(
                PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()),
                id,
                isRecipeByDate
        ).getContent().toString());
        return recipeBoardReplyService.findAllByRefId(
                PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()),
                id,
                isRecipeByDate // 최신순 or 인기순
        );
    }


    //    레시피 댓글 작성
    @PostMapping("recipe-board-detail/reply/write/{recipeBoardId}")
    @ResponseBody
    public ReplyDTO writeRecipeReply(@RequestBody ReplyDTO replyDTO, @PathVariable Long recipeBoardId, @AuthenticationPrincipal UserDetail userDetail) {
        // 임시 session 값 1저장
        return recipeBoardReplyService.saveReply(replyDTO, recipeBoardId, userDetail.getId());
    }

    //    레시피 댓글 삭제
    @DeleteMapping("recipe-board-detail/reply/delete/{recipeBoardId}/{replyId}")
    public String deleteRecipeReply(@PathVariable Long replyId, @PathVariable Long recipeBoardId, @AuthenticationPrincipal UserDetail userDetail) {
        // 임시 session 값 1저장
        Long userId = userDetail.getId();
        recipeBoardReplyService.deleteReply(replyId, recipeBoardId, userId);
        log.info("===============들어옴");
        return "user-board/recipe-board-detail";
    }
    //  레시피 댓글 좋아요
    @PostMapping("recipe-board-detail/reply/like/{replyId}")
    @ResponseBody
    public boolean checkRecipeReplyLike(@PathVariable Long replyId, @AuthenticationPrincipal UserDetail userDetail) {
        log.info("================== 들어옴 ===============");
        // 임시 session 값 1
        return replyLikeService.checkOutLike(replyId, userDetail.getId());
    }

    /* ==================================================== */

    //    기부 게시판 리스트
    @GetMapping("donate-list")
    public String goDonateList(Model model, @AuthenticationPrincipal UserDetail userDetail){
        model.addAttribute("userId", userDetail.getId());
        return "user-board/donate-list";
    }
    //    기부 게시판 최신순
    @GetMapping("donate-list/recent")
    @ResponseBody
    public Slice<DonationBoardDTO> getRecentDonateBoardList(@PageableDefault(page=1, size=5) Pageable pageable) {
        Slice<DonationBoardDTO> donationBoardDTOS = donationBoardService.getRecentList(PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize()));
        return donationBoardDTOS;
    }

    //    기부 게시판 인기순
    @GetMapping("donate-list/popular")
    @ResponseBody
    public Slice<DonationBoardDTO> getPopularDonateBoardList(@PageableDefault(page=1, size=5) Pageable pageable) {
        Slice<DonationBoardDTO> donationBoardDTOS = donationBoardService.getPopularList(PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize()));
        return donationBoardDTOS;
    }

//    기부 게시판 상세보기
    @GetMapping("donate-detail/{id}")
    public String goDonateDetail(Model model, @PathVariable Long id){
        model.addAttribute("donate", donationBoardService.getDetail(id));
        return "user-board/donate-detail";
    }

//    기부 게시판 작성
    @GetMapping("donate-insert")
    public void goDonateWrite(Model model) {
        model.addAttribute("donationBoard", new DonationBoardDTO());
}

    @PostMapping("donate-insert")
    @ResponseBody
    public void donateWrite(@RequestBody DonationBoardDTO donationBoardDTO, @AuthenticationPrincipal UserDetail userDetail) {
        log.info("=====================" + donationBoardDTO);

        Long userId = userDetail.getId();
        donationBoardService.write(donationBoardDTO, userId);

    }

//    기부 게시판 수정
    @GetMapping("donate-modify/{id}")
    public String goDonateModify(Model model, @PathVariable Long id){
        model.addAttribute("donationBoardDTO", donationBoardService.getDetail(id));
        return "user-board/donate-modify";
    }

    @PostMapping("donate-modify")
    @ResponseBody
    public String goDonateModify(@RequestBody DonationBoardDTO donationBoardDTO, @AuthenticationPrincipal UserDetail userDetail) {
        log.info(userDetail.getId().toString());
        Long userId = userDetail.getId();

        log.info(donationBoardDTO.toString());
        donationBoardService.update(donationBoardDTO, userId);
        log.info(donationBoardDTO.getId().toString());
        return "/user-board/donate-detail/" + donationBoardDTO.getId();
    }

//    기부 게시판 삭제
    @DeleteMapping("donate-detail/delete/{id}")
    @ResponseBody
    public void deleteDonate(@PathVariable Long id, @AuthenticationPrincipal UserDetail userDetail) {
        Long userId = userDetail.getId();

        donationBoardService.delete(id, userId);
        log.info("===============들어옴");
    }

}
