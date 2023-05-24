package com.app.happybox.service.user;

import com.app.happybox.domain.OAuthAttributes;
import com.app.happybox.domain.user.MemberDTO;
import com.app.happybox.entity.user.Member;
import com.app.happybox.entity.user.User;
import com.app.happybox.exception.UserNotFoundException;
import com.app.happybox.provider.UserDetail;
import com.app.happybox.repository.user.MemberRepository;
import com.app.happybox.repository.user.UserRepository;
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

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

//        사용자의 로그인 완료 후의 정보를 담기위한 준비
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

//        로그인된 사용자의 정보 불러오기
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

//        어떤 기업의 OAuth를 사용했는 지의 구분(naver, kakao 등)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // naver, kakao 로그인 구분
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);

//        OAuth를 통해 전달받은 정보를 DTO로 변환하여 session에 저장
//        session에 객체를 저장하기 위해 직렬화 사용(다시 가져올 때에는 역직렬화를 통해 원본 객체 생성)
//        회원 번호를 사용하는 것 보다 OAuth 인증에 작성된 이메일을 사용하는 것이 올바르다.

//        가지고 온 정보 userDetail에다 담아주기
        if (user.getUserId() != null) {
            return UserDetail.builder().id(user.getId())
                    .userId(user.getUserId())
                    .userPassword(user.getUserPassword())
                    .userRole(user.getUserRole())
                    .attributes(attributes.getAttributes())
                    .build();
        }

//        만약 이미 있는 회원이 아니라면 기본 OAuth 유저로 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getUserRole().getSecurityRole())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        Optional<User> foundUserOptional = userRepository.findByUserEmail(attributes.getEmail());
        User foundUser;

//        멤버 DTO에 담아줄 용
        Optional<Member> foundMemberOptional = memberRepository.findByMemberEmail_QueryDSL(attributes.getEmail());
        Member foundMember;

//        OAuth로 가입한 유저가 있다면
        if(foundUserOptional.isPresent()) {
//            그 유저의 정보를 가져오기
            foundUser = foundUserOptional.orElseThrow(UserNotFoundException::new);
        } else {
            foundMember = foundMemberOptional.orElse(attributes.toEntity());
//            정보 없는 사람 memberDTO를 통해 넘겨주기
            session.setAttribute("member", new MemberDTO(foundMember));
//            그냥 아무거나 넣은거
            foundUser = attributes.toEntity();
        }
        return foundUser;
    }
}
