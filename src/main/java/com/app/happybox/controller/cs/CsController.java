package com.app.happybox.controller.cs;

import com.app.happybox.domain.InquiryDTO;
import com.app.happybox.domain.NoticeDTO;
import com.app.happybox.domain.PageDTO;
import com.app.happybox.entity.customer.NoticeSearch;
import com.app.happybox.service.cs.InquiryService;
import com.app.happybox.service.cs.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("cs/*")
@Slf4j
public class CsController {
    @Qualifier("notice")
    private final NoticeService noticeService;
    @Qualifier("inquiry")
    private final InquiryService inquiryService;

    //    공지사항 리스트
    @GetMapping("notice")
    public void getNoticeList() {;}

    //    검색을 통해 가져온 리스트
    @GetMapping("search")
    @ResponseBody
    public Page<NoticeDTO> searchList(@PageableDefault(page = 1) Pageable pageable, NoticeSearch noticeSearch) {
        Page<NoticeDTO> notices = noticeService.getNoticeList(PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()), noticeSearch);
        return notices;
    }

//    공지사항 상세페이지로 이동
    @GetMapping("notice-detail/{id}")
    public String goToNoticeDetail(@PathVariable Long id, Model model){
        model.addAttribute("noticeDetail", noticeService.getNoticeWithId(id));

        return "/CS/notice-detail";
    }

    //    문의 작성 페이지로 이동
    @GetMapping("write-inquiry")
    public void goToInquiry(InquiryDTO inquiryDTO) {;}

    //    문의 작성
    @PostMapping("write")
    public RedirectView writeInquiry(InquiryDTO inquiryDTO) {
        inquiryService.inquiryWrite(inquiryDTO);
    //    나중에 마이페이지 문의 목록으로 이동해야 함
        return new RedirectView("/cs/notice");
    }

    //    FAQ로 이동
    @GetMapping("faq")
    public void goFAQ() {;}
}
