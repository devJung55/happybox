package com.app.happybox.controller.user;

import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserRestController {

    private final UserService userService;

//    아이디 중복검사
    @GetMapping("checkUserId")
    public Boolean checkUserId(@RequestParam("userId")String userId){
        return userService.existsUserByUserId(userId);
    }

//    휴대폰 중복검사
    @GetMapping("checkUserPhoneNumber")
    public Boolean checkPhoneNumber(@RequestParam("userPhoneNumber")String userPhoneNumber){
        return userService.existsUserByUserPhoneNumber(userPhoneNumber);
    }

//    이메일 중복검사
    @GetMapping("checkUserEmail")
    public Boolean checkUserEmail(@RequestParam("userEmail")String userEmail){
        return userService.existsUserByUserEmail(userEmail);
    }

}
