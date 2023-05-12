package com.app.happybox.service.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.domain.user.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface MemberService extends UserDetailsService {

    //    회원가입
    public Member join(MemberDTO memberDTO, PasswordEncoder passwordEncoder);

    //    로그인
    public Optional<Member> login(String memberId, String memberPassword);

    //    아이디 찾기
    public Optional<String> findMemberIdByPhoneNumber(String memberPhoneNumber);

    //    아이디 찾기(memberEmail)
    public Optional<String> findMemberIdByEmail(String memberEmail);

    //    아이디 중복검사
    public boolean existsByUserId(String userId);

    //    휴대폰 중복검사
    public boolean existsByUserPhoneNumber(String userPhoneNumber);

    //    이메일 중복검사
    public boolean existsByUserEmail(String userEmail);

    //    아이디로 전체 정보 조회(MemberDetailService)
    public Optional<Member> findByUserId(String userId);

    //    마이페이지 배송지정보조회
    public Optional<Member> findDeliveryInfoById(Long memberId);

//    회원정보수정
    public void updateMemberInfoById(Member member);

//    회원탈퇴
    public void updateUserStatusById(Long memberId);

//    관리자 회원 목록
    public Page<Member> getList(Pageable pageable);

//    회원 조회
    public Optional<Member> getDetail(Long memberId);

    //    MemberDTO -> Member
    default Member toMemberEntity(MemberDTO memberDTO){
        return Member.builder().userId(memberDTO.getUserId())
                .userEmail(memberDTO.getUserEmail())
                .userPassword(memberDTO.getUserPassword())
                .userPhoneNumber(memberDTO.getUserPhoneNumber())
                .deliveryPhoneNumber(memberDTO.getDeliveryPhoneNumber())
                .address(memberDTO.getUserAddress())
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
                .userId(member.getUserId())
                .userAddress(member.getAddress())
                .memberDeliveryAddress(member.getMemberDeliveryAddress())
                .memberBirth(member.getMemberBirth())
                .userEmail(member.getUserEmail())
                .memberGender(member.getMemberGender())
                .memberName(member.getMemberName())
                .userPassword(member.getUserPassword())
                .userPhoneNumber(member.getUserPhoneNumber())
                .deliveryName(member.getDeliveryName())
                .deliveryPhoneNumber(member.getDeliveryPhoneNumber())
                .userRole(member.getUserRole())
                .userStatus(member.getUserStatus())
                .build();
    }

}
