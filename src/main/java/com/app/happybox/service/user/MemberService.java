package com.app.happybox.service.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.MemberDTO;

import java.util.Optional;

public interface MemberService {

    //    회원가입
    public Member join(MemberDTO memberDTO);

    //    로그인
    public Optional<Member> login(String memberId, String memberPassword);

    //    아이디 찾기
    public Optional<String> findMemberIdByPhoneNumber(String memberPhoneNumber);

    //    아이디 찾기(memberEmail)
    public Optional<String> findMemberIdByEmail(String memberEmail);

//    MemberDTO -> Member
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

//    Member -> MemberDTO
    default MemberDTO toMemberDTO(Member member){
        return MemberDTO.builder()
                .id(member.getId())
                .memberId(member.getUserId())
                .memberAddress(member.getAddress())
                .memberDeliveryAddress(member.getMemberDeliveryAddress())
                .memberBirth(member.getMemberBirth())
                .memberEmail(member.getUserEmail())
                .memberGender(member.getMemberGender())
                .memberName(member.getMemberName())
                .memberPassword(member.getUserPassword())
                .memberPhoneNumber(member.getUserPhoneNumber())
                .deliveryName(member.getDeliveryName())
                .deliveryPhoneNumber(member.getDeliveryPhoneNumber())
                .memberRole(member.getUserRole())
                .memberStatus(member.getUserStatus())
                .build();
    }

}
