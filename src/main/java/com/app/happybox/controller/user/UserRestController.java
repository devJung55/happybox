package com.app.happybox.controller.user;

import com.app.happybox.domain.user.MailDTO;
import com.app.happybox.entity.user.User;
import com.app.happybox.entity.user.UserRandomKey;
import com.app.happybox.service.user.DistributorService;
import com.app.happybox.service.user.MemberService;
import com.app.happybox.service.user.RandomKeyService;
import com.app.happybox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final DistributorService distributorService;
    private final RandomKeyService randomKeyService;
    private final PasswordEncoder passwordEncoder;

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

    //    전화번호로 아이디 찾기
    @PostMapping("find-id-phone")
    public String findIdByPhone(String userPhone) {
        return userService.findUserIdByUserPhone(userPhone);
    }

    //    이메일로 아이디 찾기
    @PostMapping("find-id-email")
    public String findIdByEmail(String userEmail) {
        return userService.findUserIdByUserEmail(userEmail);
    }

    //    전화번호로 이메일 찾기
    @PostMapping("find-email-phone")
    public String findEmailByPhone(String userPhone) { return userService.findUserEmailByPhone(userPhone).get(); }

    //    이메일 보내기
    @PostMapping("sendMail")
    @Transactional(rollbackFor = Exception.class)
    public Long sendMailforFindPassword(@RequestParam("userEmail") String userEmail) {
        User user = userService.findUser(userEmail).get();

        UserRandomKey randomKey = randomKeyService.getLatestRandomKey(user.getId());

        String randomKeyString = randomKey != null ? randomKey.getRanKey() : randomKeyService.saveRandomKey(user).getRanKey();

        MailDTO mail = new MailDTO();
        mail.setAddress(userEmail);
        mail.setTitle("[해피박스] 새 비밀번호 설정 링크 입니다.");

        String message = "비밀 번호 변경 링크 입니다.\n\n" + "링크: http://happybox.site/member/change-password?userEmail=" + userEmail + "&userRandomKey=" + randomKeyString;
        mail.setMessage(message);

        userService.sendMail(mail);

        return 1L;
    }

    //    비밀번호 변경하기
    @PostMapping("change-password")
    public RedirectView updatePassword(@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword){
        userService.updatePassword(userEmail, userPassword, passwordEncoder);

        return new RedirectView("/member/login");
    }

//    유통업체명 중복검사
    @GetMapping("checkDistributorName")
    public Boolean checkDistributorName(@RequestParam("distributorName")String distributorName){
        return distributorService.existsByDistributorName(distributorName);
    }

}
