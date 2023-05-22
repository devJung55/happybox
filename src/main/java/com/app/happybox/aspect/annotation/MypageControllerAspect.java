package com.app.happybox.aspect.annotation;

import com.app.happybox.domain.user.UserFileDTO;
import com.app.happybox.entity.file.UserFile;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.service.board.RecipeBoardService;
import com.app.happybox.service.cs.InquiryService;
import com.app.happybox.service.order.MemberOrderProductItemService;
import com.app.happybox.service.order.OrderSubsciptionService;
import com.app.happybox.service.user.UserFileService;
import com.app.happybox.service.user.UserService;
import com.app.happybox.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class MypageControllerAspect {
    private final HttpServletRequest request;
    private final OrderSubsciptionService orderSubsciptionService;
    private final MemberOrderProductItemService memberOrderProductItemService;
    private final RecipeBoardService recipeBoardService;
    private final InquiryService inquiryService;
    private final UserFileService userFileService;
    private final UserService userService;

    @Before("@annotation(com.app.happybox.aspect.annotation.MypageHeaderValues)")
    public void setHeaderInfoValue(JoinPoint joinPoint) throws Throwable {
        Long id = 0L;
        String userName = "";

        Object arg = joinPoint.getArgs()[0];
        if(arg instanceof UserDetail) {
            UserDetail userDetail = ((UserDetail) arg);
            id = userDetail.getId();
            userName = userDetail.getUsername();
        }

        Long subscribeCount = orderSubsciptionService.getMySubscriptionCountByMemberId(id);
        Long orderCount = memberOrderProductItemService.getOrderCountByMemberId(id);
        Long boardCount = recipeBoardService.getCountByMemberId(id);
        Long inquiryCount = inquiryService.getInquiryCountByUserId(id);
        UserFileDTO userFileDTO = userFileService.getDetail(id);
        Role userRole = userService.getDetailByUserId(id).getUserRole();

        request.setAttribute("subscribeCount", subscribeCount); // 내가 구독한 구독 수
        request.setAttribute("orderCount", orderCount);         // 주문 건수
        request.setAttribute("boardCount", boardCount);         // 게시물 건수
        request.setAttribute("inquiryCount", inquiryCount);     // 문의 건수 조회
        request.setAttribute("userName", userName);             // 회원이름
        request.setAttribute("userFile", userFileDTO);          // 회원 프로필사진
        request.setAttribute("userRole", userRole);             // 회원타입
   }
}
