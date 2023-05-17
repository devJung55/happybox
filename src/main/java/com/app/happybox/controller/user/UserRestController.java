package com.app.happybox.controller.user;

import com.app.happybox.service.user.DistributorService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final DistributorService distributorService;

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

//    유통업체명 중복검사
    @GetMapping("checkDistributorName")
    public Boolean checkDistributorName(@RequestParam("distributorName")String distributorName){
        return distributorService.existsByDistributorName(distributorName);
    }

}
