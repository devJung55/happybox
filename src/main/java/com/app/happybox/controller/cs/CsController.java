package com.app.happybox.controller.cs;

import com.app.happybox.domain.NoticeDTO;
import com.app.happybox.domain.PageDTO;
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
    @GetMapping("notice-list")
    public String getNoticeList(@RequestParam(value = "page", defaultValue = "1", required = false) int page, @RequestParam(value = "srchType", required = false) String srchType, @RequestParam(value = "keyword", required = false) String keyword, Model model) {
        NoticeSearch noticeSearch = new NoticeSearch();
        if(srchType != null) {
            switch(srchType){
                case "제목" : noticeSearch.setNoticeTitle(keyword);
                    break;
                case "내용" : noticeSearch.setNoticeContent(keyword);
                    break;
                case "전체" : noticeSearch.setNoticeWhole(keyword);
                    break;
            }
        }
        PageRequest getPage = PageRequest.of(page - 1, 10);
        Page<NoticeDTO> lists = noticeService.getNoticeList(getPage, noticeSearch);
        model.addAttribute("pageDTO", new PageDTO(lists));
        model.addAttribute("lists", lists.getContent());

        return "/CS/notice";
    }

//    공지사항 상세페이지로 이동
    @GetMapping("notice-detail/{id}")
    public String goToNoticeDetail(@PathVariable Long id, Model model){
        model.addAttribute("noticeDetail", noticeService.getNoticeWithId(id));

        return "/CS/notice-detail";
    }


}
