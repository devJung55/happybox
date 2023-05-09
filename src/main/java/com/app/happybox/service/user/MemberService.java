package com.app.happybox.service.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.MemberDTO;

import java.util.Optional;

public interface MemberService {

    //    회원가입
    public void join(MemberDTO memberDTO);

    //    로그인
    public Optional<Member> login(String memberId, String memberPassword);

    default Member toMemberEntity(MemberDTO memberDTO){
        return Member.builder().userId(memberDTO.getMemberId())
                .userEmail(memberDTO.getMemberEmail())
                .userPassword(memberDTO.getMemberPassword())
                .userPhoneNumber(memberDTO.getMemberPhoneNumber())
                .deliveryPhoneNumber(memberDTO.getDeliveryPhoneNumber())
                .address(memberDTO.getMemberAddress())
                .deliveryName(memberDTO.getDeliveryName())
                .memberDeliveryAddress(memberDTO.getMemberDeliveryAddress())
                .memberBirth(memberDTO.getMemberBirth())
                .memberName(memberDTO.getMemberName())
                .memberGender(memberDTO.getMemberGender())
                .build();
    }

}
