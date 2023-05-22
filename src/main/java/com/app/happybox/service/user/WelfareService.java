package com.app.happybox.service.user;

import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.domain.user.SubscriptionWelFareDTO;
import com.app.happybox.domain.user.WelfareDTO;
import com.app.happybox.entity.subscript.Subscription;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.Welfare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface WelfareService {
//    회원정보수정
    public void updateWelfareInfoById(WelfareDTO welfareDTO);

//    회원탈퇴
    public void updateUserStatusById(Long welfareId);

//    관리자 복지관회원 목록
    public Page<WelfareDTO> getList(Pageable pageable);

//    관리자 복지관회원 조회
    public WelfareDTO getDetail(Long welfareId);

    //    회원가입
    public void join(WelfareDTO welfareDTO, SubscriptionWelFareDTO subscriptionWelFareDTO, PasswordEncoder passwordEncoder);

    //    아이디 중복검사
    public boolean existsByUserId(String userId);

    //    휴대폰 중복검사
    public boolean existsByUserPhoneNumber(String userPhoneNumber);

    //    이메일 중복검사
    public boolean existsByUserEmail(String userEmail);

    //    아이디로 전체 정보 조회(MemberDetailService)
    public Optional<Welfare> findByUserId(String userId);

    default Welfare toWelfareEntity(WelfareDTO welfareDTO){
        return Welfare.builder()
                .address(welfareDTO.getAddress())
                .userEmail(welfareDTO.getUserEmail())
                .userId(welfareDTO.getUserId())
                .userPassword(welfareDTO.getUserPassword())
                .userPhoneNumber(welfareDTO.getUserPhoneNumber())
                .userRole(welfareDTO.getUserRole())
                .userStatus(welfareDTO.getUserStatus())
                .welfareName(welfareDTO.getWelfareName())
                .welfarePointTotal(welfareDTO.getWelfarePointTotal())
                .build();
    }

    default Welfare toWelfareJoinEntity(WelfareDTO welfareDTO){
        return Welfare.builder()
                .address(welfareDTO.getAddress())
                .userEmail(welfareDTO.getUserEmail())
                .userId(welfareDTO.getUserId())
                .userPassword(welfareDTO.getUserPassword())
                .userPhoneNumber(welfareDTO.getUserPhoneNumber())
                .userRole(welfareDTO.getUserRole())
                .userStatus(welfareDTO.getUserStatus())
                .welfareName(welfareDTO.getWelfareName())
                .build();
    }

    default Subscription toSubscriptionEntity(SubscriptionWelFareDTO subscriptionWelFareDTO){
        return Subscription.builder()
                .subscriptionContent(subscriptionWelFareDTO.getSubscriptionContent())
                .subscriptionPrice(subscriptionWelFareDTO.getSubscriptionPrice())
                .subscriptionTitle(subscriptionWelFareDTO.getSubscriptionTitle())
                .build();
    }

    default WelfareDTO toWelfareDTO(Welfare welfare) {
        return WelfareDTO.builder()
                .address(welfare.getAddress())
                .createdDate(welfare.getCreatedDate())
                .id(welfare.getId())
                .userEmail(welfare.getUserEmail())
                .userId(welfare.getUserId())
                .userPhoneNumber(welfare.getUserPhoneNumber())
                .userRole(welfare.getUserRole())
                .userStatus(welfare.getUserStatus())
                .welfareName(welfare.getWelfareName())
                .welfarePointTotal(welfare.getWelfarePointTotal())
                .build();
    }
}
