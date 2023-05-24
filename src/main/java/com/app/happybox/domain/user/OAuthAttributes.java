package com.app.happybox.domain;

import com.app.happybox.entity.user.Member;
import com.app.happybox.type.Role;
import com.app.happybox.type.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Builder
@RequiredArgsConstructor
@Slf4j
public class OAuthAttributes {
    /* OAuth를 통해 받아온 정보가 들어갈 곳 */
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String phone;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        log.info(" ---------------- oauth를 통해 넘겨받은 정보들 ----------------- ");
//      userNameAttributeName은 .yml에서 설정해 놓은 user-name-attribute 값이다.
//        log.info("================={}", userNameAttributeName);

//      registrationId는 네이버 로그인일 경우 naver이고 카카오 로그인일 경우 kakao이다.
//        log.info("================={}", registrationId);

        if("naver".equals(registrationId)) {
            return ofNaver(userNameAttributeName, attributes);
        }
        else {
            return ofKakao(userNameAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttributeName);

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .phone((String) response.get("mobile"))
                .attributes(response)
                /* 동일인 식별 정보 (고유 해쉬값) */
                .nameAttributeKey("id")
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get(userNameAttributeName);

        log.info(" ======================== kakao 계정 정보 추가하기 ========================== ");

        return OAuthAttributes.builder()
                .email((String) kakaoAccount.get("email"))
                /* 동일인 식별 정보 (고유 해쉬값) */
                .nameAttributeKey("id")
                .attributes(attributes)
                .build();

    }

    public Member toEntity() {
        return Member.builder()
                .memberName(name)
                .userEmail(email)
                .userPhoneNumber(phone)
                .userRole(Role.MEMBER)
                .userStatus(UserStatus.REGISTERED)
                .build();
    }
}
