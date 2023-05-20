package com.app.happybox.service.user;

import com.app.happybox.domain.OAuthAttributes;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberOAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info(" -------------------- oauth 요청 후 첫 단계 ---------------------- ");
//        사용자의 로그인 완료 후의 정보를 담기위한 준비
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        로그인된 사용자의 정보 불러오기
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

//        어떤 기업의 OAuth를 사용했는 지의 구분(naver, kakao 등)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        log.info(" -------------------- oauth 종류 확인 --------------------------- ");
        // naver, kakao 로그인 구분
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        log.info(attributes.getName());
        log.info(attributes.getEmail());
        log.info(attributes.getPhone());
//        저장 후 이메일을 찾아서 가져오도록 할 예정
        Member member = saveOrUpdate(attributes);
        log.info(" --------------------------- oauth를 통해 정보 가져오기 -------------------------------- ");

//        OAuth를 통해 전달받은 정보를 DTO로 변환하여 session에 저장
//        session에 객체를 저장하기 위해 직렬화 사용(다시 가져올 때에는 역직렬화를 통해 원본 객체 생성)
//        회원 번호를 사용하는 것 보다 OAuth 인증에 작성된 이메일을 사용하는 것이 올바르다.

        log.info(" --------------------------- 정보 넘겨주기 전 --------------------------------------- ");
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getUserRole().getSecurityRole())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuthAttributes attributes) {
        log.info(" ========================= 받은 정보 처리해주기 ================================= ");
        Optional<Member> foundMemberOptional = memberRepository.findByMemberEmail_QueryDSL(attributes.getEmail());
        Member foundMember;
        if (foundMemberOptional.isPresent()) {
            log.info(" ----------------- 유저 존재하는지 체크 여부로 들어오나? ---------------------- ");
            foundMember = foundMemberOptional.get();
            session.setAttribute("member",
                    MemberDTO.builder().id(foundMember.getId())
                            .userId(foundMember.getUserId())
                            .userPassword(foundMember.getUserPassword())
                            .address(foundMember.getAddress())
                            .userEmail(foundMember.getUserEmail())
                            .userPhoneNumber(foundMember.getUserPhoneNumber())
                            .userStatus(foundMember.getUserStatus())
                            .userRole(foundMember.getUserRole())
                            .memberName(foundMember.getMemberName())
                            .memberBirth(foundMember.getMemberBirth())
                            .memberGender(foundMember.getMemberGender())
                            .memberDeliveryAddress(foundMember.getMemberDeliveryAddress())
                            .deliveryName(foundMember.getDeliveryName())
                            .deliveryPhoneNumber(foundMember.getDeliveryPhoneNumber())
                            .build());
            log.info("------------ 찾아온 멤버 -----------------" + foundMember);
        } else {
            log.info("------------------------------ 첫 회원가입 유저 확인 ------------------------------------- ");
            foundMember = memberRepository.findByMemberEmail_QueryDSL(attributes.getEmail())
                    .map(member -> member.update(attributes.getEmail(), attributes.getPhone(), attributes.getName()))
                    .orElse(attributes.toEntity());
            session.setAttribute("member", new MemberDTO(foundMember));
        }
        return foundMember;
    }
}
