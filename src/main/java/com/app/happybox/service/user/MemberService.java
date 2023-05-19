package com.app.happybox.service.user;

import com.app.happybox.entity.user.Member;
import com.app.happybox.domain.user.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberService{

    //    회원가입
    public void join(MemberDTO memberDTO, PasswordEncoder passwordEncoder);

    //    로그인
    public Optional<Member> login(String memberId, String memberPassword);

    //    아이디 찾기
    public Optional<String> findMemberIdByPhoneNumber(String memberPhoneNumber);

    //    아이디 찾기(memberEmail)
    public Optional<String> findMemberIdByEmail(String memberEmail);

    //    비밀번호 찾기
    public Optional<String> findMemberPwByPhoneNumber(String memberPhoneNumber);

    //    아이디 중복검사
    public boolean existsByUserId(String userId);

    //    휴대폰 중복검사
    public boolean existsByUserPhoneNumber(String userPhoneNumber);

    //    이메일 중복검사
    public boolean existsByUserEmail(String userEmail);

    //    아이디로 전체 정보 조회(MemberDetailService)
    public Optional<Member> findByUserId(String userId);

    //    마이페이지 배송지정보조회
    public MemberDTO findDeliveryInfoById(Long memberId);

    //    배송지정보수정
    public void updateMemberDeliveryAddressByMemberId(Member member);

//    회원정보수정
    public void updateMemberInfoById(MemberDTO memberDTO);

//    관리자 회원 목록
    public Page<MemberDTO> getList(Pageable pageable);

//    회원 조회
    public MemberDTO getDetail(Long memberId);

    //    인증 번호 발급
    public void checkSMS(String memberPhone, String code);

    //    MemberDTO -> Member
    default Member toMemberEntity(MemberDTO memberDTO){
        return Member.builder()
                .id(memberDTO.getId())
                .address(memberDTO.getAddress())
                .memberGender(memberDTO.getMemberGender())
                .memberName(memberDTO.getMemberName())
                .memberBirth(memberDTO.getMemberBirth())
                .memberDeliveryAddress(memberDTO.getMemberDeliveryAddress())
                .deliveryName(memberDTO.getDeliveryName())
                .deliveryPhoneNumber(memberDTO.getDeliveryPhoneNumber())
                .userPhoneNumber(memberDTO.getUserPhoneNumber())
                .userPassword(memberDTO.getUserPassword())
                .userEmail(memberDTO.getUserEmail())
                .userId(memberDTO.getUserId())
                .userRole(memberDTO.getUserRole())
                .userStatus(memberDTO.getUserStatus())
                .build();
    }

//        Member -> MemberDTO
    default MemberDTO toMemberDTO(Member member){
        return MemberDTO.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .address(member.getAddress())
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

//    default MemberDTO toMemberDTO(Member member) {
//        return MemberDTO.builder()
//                .id(member.getId())
//                .memberName(member.getMemberName())
//                .memberBirth(member.getMemberBirth())
//                .userId(member.getUserId())
//                .userEmail(member.getUserEmail())
//                .userPassword(member.getUserPassword())
//                .userPhoneNumber(member.getUserPhoneNumber())
//                .address(member.getAddress())
//                .memberGender(member.getMemberGender())
//                .build();
//    }

}
