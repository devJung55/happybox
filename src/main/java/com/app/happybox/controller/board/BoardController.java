package com.app.happybox.controller.board;

import com.app.happybox.entity.board.*;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.file.QBoardFile;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.repository.board.DonationBoardRepository;
import com.app.happybox.service.board.DonationBoardService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.board.ReviewBoardService;
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

    @GetMapping("review-board-list")
    public String goRecentList(@PageableDefault(page=1, size=5) Pageable pageable, Model model){
        model.addAttribute("reviewList",
                reviewBoardService.getReviewBoards(PageRequest.of(
                        pageable.getPageNumber() - 1,
                        pageable.getPageSize())));
        return "user-board/review-board-list";
    }

    //    리뷰 게시판 리스트(인기순)
    @GetMapping("review-board-list/popular")
    @ResponseBody
    public Slice<ReviewBoardDTO> goPopularList(@PageableDefault(page=1, size=5) Pageable pageable) {
        return reviewBoardService.getPopularReviewBoards(PageRequest.of(pageable.getPageNumber() - 1,
                        pageable.getPageSize()));
    }

    //    리뷰 게시판 리스트(최신순)
    @GetMapping("review-board-list/recent")
    @ResponseBody
    public Slice<ReviewBoardDTO> goRecentList(@PageableDefault(page=1, size=5) Pageable pageable) {
        return reviewBoardService
                .getReviewBoards(PageRequest.of(pageable.getPageNumber() - 1,
                        pageable.getPageSize()));
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
