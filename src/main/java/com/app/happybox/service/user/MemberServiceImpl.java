package com.app.happybox.service.user;

import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.repository.user.MemberRepository;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Qualifier("member") @Primary
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //    Member 회원가입
    @Override
    public void join(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
        memberDTO.setUserPassword(passwordEncoder.encode(memberDTO.getUserPassword()));
        memberDTO.setUserRole(Role.MEMBER);
        memberDTO.setMemberDeliveryAddress(memberDTO.getAddress());
        memberDTO.setDeliveryName(memberDTO.getMemberName());
        memberDTO.setDeliveryPhoneNumber(memberDTO.getUserPhoneNumber());
        memberDTO.setUserStatus(UserStatus.REGISTERED);
        memberRepository.save(toMemberEntity(memberDTO));
    }

    //    Member 로그인
    @Override
    public Optional<Member> login(String memberId, String memberPassword) {
        Optional<Member> member = memberRepository.logIn(memberId, memberPassword);
        return member;
    }

    //    아이디찾기(PhoneNumber)
    @Override
    public Optional<String> findMemberIdByPhoneNumber(String memberPhoneNumber) {
        Optional<String> memberId = memberRepository.findMemberIdByPhoneNumber(memberPhoneNumber);
        return memberId;
    }

    //    아이디찾기(Email)
    @Override
    public Optional<String> findMemberIdByEmail(String memberEmail) {
        Optional<String> memberId = memberRepository.findMemberIdByEmail(memberEmail);
        return memberId;
    }

    //    비밀번호 찾기(PhoneNumber)
    @Override
    public Optional<String> findMemberPwByPhoneNumber(String memberPhoneNumber) {
        Optional<String> memberPw = memberRepository.findMemberPwByPhoneNumber(memberPhoneNumber);
        return memberPw;
    }

//    아이디 중복 검사
    @Override
    public boolean existsByUserId(String userId) {
        return memberRepository.existsIdByUserId(userId);
    }

//    휴대폰 중복 검사
    @Override
    public boolean existsByUserPhoneNumber(String userPhoneNumber) {
        return memberRepository.existsByUserPhoneNumber(userPhoneNumber);
    }

//    이메일 중복 검사
    @Override
    public boolean existsByUserEmail(String userEmail) {
        return memberRepository.existsByUserEmail(userEmail);
    }


    //    id로 정보 조회(UserDetail 용)
    @Override
    public Optional<Member> findByUserId(String userId) {

        return memberRepository.findByUserId(userId);
    }

    //    인증 문자 날리기 // 실제로 문자가 날라가니까 막아놓기
    @Override
    public void checkSMS(String memberPhone, String code) {
        String api_key = "NCSY4XGIKFBTZHOV";
        String api_secret = "LDKUIHFNTRQ1UU0LSYJRRYVP9P3GAX5I";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", memberPhone);    // 수신전화번호
        params.put("from", "01027570866");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "인증번호는" + "[" + code + "]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

//    배송지정보수정
    @Override
    public MemberDTO findDeliveryInfoById(Long memberId) {
         return toMemberDTO(memberRepository.findDeliveryAddressByMemberId_QueryDSL(memberId).orElseThrow(UserNotFoundException::new));
    }

//    회원정보수정
    @Override @Transactional
    public void updateMemberInfoById(MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberDTO.getId()).orElseThrow(UserNotFoundException::new);

        if(memberDTO.getUserPassword() != null && memberDTO.getUserPassword() == " ,") {
            member.setUserPassword(passwordEncoder.encode(memberDTO.getUserPassword().split(",")[0]));
        }
        member.setMemberName(memberDTO.getMemberName());
        member.setUserPhoneNumber(memberDTO.getUserPhoneNumber());
        member.setAddress(memberDTO.getAddress());
        member.setUserEmail(memberDTO.getUserEmail());
    }

//    배송지정보수정
    @Override
    public void updateMemberDeliveryAddressByMemberId(Member member) {
        memberRepository.setMemberDeliveryAddressByMemberId(member);
    }

    @Override
    public Page<MemberDTO> getList(Pageable pageable) {
        Page<Member> memberList = memberRepository.findAllWithPaging_QueryDSL(pageable);
        List<MemberDTO> memberDTOList = memberList.get().map(this::toMemberDTO).collect(Collectors.toList());

        return new PageImpl<>(memberDTOList, pageable, memberList.getTotalElements());
    }

    @Override
    public MemberDTO getDetail(Long memberId) {
        Member member = memberRepository.findMemberById_QueryDSL(memberId).orElseThrow(UserNotFoundException::new);
        return toMemberDTO(member);
    }
}
