package com.app.happybox.controller.admin;

import com.app.happybox.domain.NoticeDTO;
import com.app.happybox.domain.OrderSubscriptionDTO;
import com.app.happybox.domain.PageDTO;
import com.app.happybox.domain.product.ProductDTO;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.domain.user.UserFileDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.board.RecipeBoardDTO;
import com.app.happybox.entity.board.ReviewBoardDTO;
import com.app.happybox.entity.file.BoardFileDTO;
import com.app.happybox.entity.file.UserFile;
import com.app.happybox.entity.product.Product;
import com.app.happybox.entity.user.Distributor;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import com.app.happybox.service.board.BoardService;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.board.ReviewBoardService;
import com.app.happybox.service.cs.NoticeService;
import com.app.happybox.service.order.OrderSubsciptionService;
import com.app.happybox.service.product.ProductService;
import com.app.happybox.service.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final UserService userService;
    private final MemberService memberService;
    private final UserFileService userFileService;
    private final DistributorService distributorService;
    private final WelfareService welfareService;
    private final ProductService productService;
    private final OrderSubsciptionService orderSubsciptionService;
    private final NoticeService noticeService;
    private final RecipeBoardService recipeBoardService;
    private final BoardService boardService;
    private final ReviewBoardService reviewBoardService;

//    리뷰 게시물 목록
    @GetMapping("reviewBoard-list")
    public String getReviewBoardList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Model model) {
        Page<ReviewBoardDTO> list = reviewBoardService.getList(PageRequest.of(page - 1, 10));
        model.addAttribute("reviewBoards", list.getContent());
        model.addAttribute("pageDTO", new PageDTO(list));

        return "/admin/admin-reviewBoardList";
    }

//    후기기 게시물 조회
    @ResponseBody
    @GetMapping("reviewBoard-detail")
    public ReviewBoardDTO getReviewBoardDetail(@RequestParam("reviewBoardId") Long reviewBoardId) {
        ReviewBoardDTO reviewBoardDTO = reviewBoardService.getReviewBoardDetailById(reviewBoardId).get();
        return reviewBoardDTO;
    }

//    레시피 게시물 목록
    @GetMapping("recipeBoard-list")
    public String getRecipeBoardList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Model model) {
        Page<RecipeBoardDTO> list = recipeBoardService.getList(PageRequest.of(page - 1, 10));

        model.addAttribute("recipeBoards", list.getContent());
        model.addAttribute("pageDTO", new PageDTO(list));

        return "/admin/admin-recipeBoardList";
    }

//    레시피 게시물 조회
    @ResponseBody
    @GetMapping("recipeBoard-detail")
    public RecipeBoardDTO getRecipeBoardDetail(@RequestParam("recipeBoardId") Long recipeBoardId) {
        RecipeBoardDTO recipeBoardDTO = recipeBoardService.getRecipeBoardDetailById(recipeBoardId).get();
        return recipeBoardDTO;
    }

//    레시피 게시물 삭제
    @ResponseBody
    @GetMapping("board-remove")
    public void removeBoard(@RequestParam("boardId") Long boardId) {
        boardService.removeByBoardId(boardId);
    }

//    회원 목록
    @GetMapping("member-list")
    public String getMemberList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Model model) {
        Page<MemberDTO> list = memberService.getList(PageRequest.of(page - 1, 10));
        model.addAttribute("members", list.getContent());
        model.addAttribute("pageDTO", new PageDTO(list));
        return "/admin/admin-memberList";
    }

//    회원 삭제
    @ResponseBody
    @GetMapping("user-remove")
    public void removeMember(@RequestParam("userId") Long userId) {
        userService.deleteByMemberId(userId);
    }

//    회원 조회
    @ResponseBody
    @GetMapping("member-detail")
    public String[] getMemberDetail(@RequestParam("memberId") Long memberId, Model model) {
        MemberDTO memberInfo = memberService.getDetail(memberId);
        UserFileDTO userFile = userFileService.getDetail(memberId);
        String filePath = "";
        String fileUuid = "";
        String fileOrgName = "";

        if(userFile == null) {
            filePath = null;
            fileUuid = null;
            fileOrgName = null;
        } else {
            filePath = userFile.getFilePath();
            fileUuid = userFile.getFileUuid();
            fileOrgName = userFile.getFileOrgName();
        }

        String[] member = {
                filePath,
                fileUuid,
                fileOrgName,
                memberInfo.getMemberName(),
                memberInfo.getUserPhoneNumber(),
                memberInfo.getUserEmail(),
                String.valueOf(memberInfo.getMemberBirth()).replaceAll("-", "."),
                String.valueOf(memberInfo.getMemberGender())
        };
        return member;
    }

//    유통회원 목록
    @GetMapping("distributor-list")
    public String getDistributorList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Model model) {
        Page<Distributor> list = distributorService.getList(PageRequest.of(0, 5));
        model.addAttribute("distributors", list.getContent());
        model.addAttribute("pageDTO", new PageDTO(list));
        return "/admin/admin-distributorList";
    }

//    유통회원 조회
    @GetMapping("distributor-detail/{distributorId}")
    public String getDistributorDetail(@PathVariable Long distributorId, Model model) {
        Page<ProductDTO> list = productService.getListByDistributorId(PageRequest.of(0, 10), distributorId);
        model.addAttribute("distributor", distributorService.getDetail(distributorId));
        model.addAttribute("distributorId", distributorId);
        model.addAttribute("userFile", userFileService.getDetail(distributorId));
        model.addAttribute("products", list.getContent());
        model.addAttribute("pageDTO", new PageDTO(list));
        return "/admin/admin-distributorDetail";
    }

//    상품 상세보기
    @ResponseBody
    @GetMapping("product-detail")
    public String[] getProductDetail(@RequestParam("productId") Long productId) {
        Product productInfo = productService.getDetailById(productId).get();
        String filePath = "";
        String fileUuid = "";
        String fileOrgName = "";

        if(productInfo.getProductFiles() == null || productInfo.getProductFiles().isEmpty()) {
            filePath = null;
            fileUuid = null;
            fileOrgName = null;
        } else {
            filePath = productInfo.getProductFiles().get(0).getFilePath();
            fileUuid = productInfo.getProductFiles().get(0).getFileUuid();
            fileOrgName = productInfo.getProductFiles().get(0).getFileOrgName();
        }

        String[] product = {
                filePath,
                fileUuid,
                fileOrgName,
                productInfo.getProductName(),
                String.valueOf(productInfo.getProductPrice()),
                String.valueOf(productInfo.getProductStock())
        };
        return product;
    }

//    복지관회원 목록
    @GetMapping("welfare-list")
    public String getWelfareList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, Model model) {
        Page<WelfareDTO> list = welfareService.getList(PageRequest.of(0, 5));
        model.addAttribute("welfares", list.getContent());
        model.addAttribute("pageDTO", new PageDTO(list));
        return "/admin/admin-welfareList";
    }

//    복지관회원 조회
    @GetMapping("welfare-detail/{welfareId}")
    public String getWelfareDetail(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @PathVariable Long welfareId, Model model) {
        String subsciberName = null;
        Page<OrderSubscriptionDTO> list = orderSubsciptionService.getListByWelfareId(PageRequest.of(0, 5), welfareId, subsciberName);
        model.addAttribute("welfare",welfareService.getDetail(welfareId));
        model.addAttribute("subscribers", list.getContent());
        model.addAttribute("pageDTO", new PageDTO(list));
        model.addAttribute("userFile", userFileService.getDetail(welfareId));
        return "/admin/admin-welfareDetail";
    }

    /* =================================== 공지사항 ======================================== */

    //    공지사항 목록 조회
    @GetMapping("admin-noticeList")
    public void  goNoticeList() {;}

    //    공지사항 목록 페이징
    @ResponseBody
    @GetMapping("admin-noticeList/{page}")
    public Page<NoticeDTO> showNoticeList(@PathVariable("page") Integer page) {
        PageRequest pages = PageRequest.of(page - 1, 1);
        Page<NoticeDTO> lists = noticeService.getAdminNoticeList(pages);
        return lists;
    }

    //    공지사항 상세 조회
    @ResponseBody
    @GetMapping("notice-detail")
    public NoticeDTO showNoticeDetail(@RequestParam("id") Long id) {
        return noticeService.getNoticeWithId(id);
    }

    //    공지사항 작성
    @ResponseBody
    @PostMapping("notice-write")
    public void writeNotice(@RequestBody NoticeDTO noticeDTO) { noticeService.noticeWrite(noticeDTO); }

    //    공지사항 수정

    //    공지사항 삭제
    @ResponseBody
    @DeleteMapping("notice-delete")
    public void removeNotice(@RequestParam("id") Long id) {
        noticeService.deleteById(id);
    }
}
