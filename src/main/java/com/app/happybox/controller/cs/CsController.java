package com.app.happybox.controller.cs;

import com.app.happybox.domain.NoticeDTO;
import com.app.happybox.entity.customer.NoticeSearch;
import com.app.happybox.service.cs.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("cs/*")
@Slf4j
public class CsController {
    @Qualifier("notice")
    private final NoticeService noticeService;

//    공지사항 리스트 및 검색조건
    @GetMapping("/list")
    @ResponseBody
    public Page<NoticeDTO> getNoticeList(@RequestParam(value = "page", required = false) int page, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "search", required = false) String search) {
        NoticeSearch noticeSearch = new NoticeSearch();
        if(type != null) {
            switch(type){
                case "title" : noticeSearch.setNoticeTitle(search);
                    break;
                case "content" : noticeSearch.setNoticeContent(search);
                    break;
            }
        }
        log.info(noticeSearch.toString());
        PageRequest getPage = PageRequest.of(page - 1, 5);
        return noticeService.getNoticeList(getPage, noticeSearch);
    }

//    공지사항 상세페이지로 이동
    @GetMapping("notice-detail/{id}")
    public String goToNoticeDetail(@PathVariable Long id, Model model){
        model.addAttribute("detail", noticeService.getNoticeWithId(id));

        return "/CS/notice-detail";
    }


}
