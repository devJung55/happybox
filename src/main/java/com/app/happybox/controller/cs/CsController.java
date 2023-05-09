package com.app.happybox.controller.cs;

import com.app.happybox.service.cs.CsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("cs/*")
@Slf4j
public class CsController {
    private final CsService csService;

//    공지사항 상세페이지로 이동
    @GetMapping("notice-detail/{id}")
    public String goToNoticeDetail(@PathVariable Long id, Model model){
        model.addAttribute("detail", csService.getNoticeWithId(id));

        return "/CS/notice-detail";
    }

}
