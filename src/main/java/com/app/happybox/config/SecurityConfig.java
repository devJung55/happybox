package com.app.happybox.config;

import com.app.happybox.service.user.MemberOAuthService;
import com.app.happybox.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {


    //    ============================  경로 상수화 ========================================
//    메인 경로
    private static final String MAIN_PATH = "/main/**";

    //    서비스 경로
    private static final String ADMIN_PATH = "/admin/**";
    private static final String MEMBER_PATH = "/member/**";
    private static final String WELFARE_PATH = "/welfares/**";
    private static final String DISTRIBUTOR_PATH = "/product/**";
    private static final String BOARD_PATH = "**/boards/**";
    private static final String CS_PATH = "**/CS/**";
    private static final String MYPAGE_PATH = "/mypage/**";
    private static final String MYPAGE_MEMBER_PATH = "/mypage/member/**";
    private static final String MYPAGE_WELFARE_PATH = "/mypage/welfare/**";
    private static final String MYPAGE_DISTRIBUTOR_PATH = "/mypage/distributor/**";

    //    작성 페이지 경로
    private static final String WRITE_PATH = "/**/write";
    private static final String WELFARE_WRITE_PATH = "/**/welfare/**/write";
    private static final String MEMBER_WRITE_PATH = "/**/MEMBER/**/write";
    private static final String DISTRIBUTOR_WRITE_PATH = "/**/product/**/write";


    //    ignore 경로
    private static final String IGNORE_FAVICON = "/favicon.png";
    //    로그인 FORM 경로
    private static final String LOGIN_PAGE = "/member/login";

    //    로그인 ACTION 경로
    private static final String MEMBER_LOGIN_PROCESSING_URL = "/login";

    //    로그아웃 경로
    private static final String LOGOUT_URL = "/logout";

    //    로그아웃 성공시 Main으로 이동
    private static final String LOGOUT_SUCCESS_URL = "/main/welfare";

    //    자동로그인을 위한 토큰키("happyToken")
    private static final String REMEMBER_ME_TOKEN_KEY = "happyToken";

    //    자동 로그인 토근 유지 기간(2주)
    private static final int REMEMBER_ME_TOKEN_EXPIRED = 86400 * 14;

//    ============================  Handler 선언부 ========================================

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final UserDetailsService userDetailsService;

//    ============================ Service 선언부 =========================================
    private final MemberOAuthService memberOAuthService;

//    ============================ 필터 체인 설정부  ========================================

    /* 비밀번호 암호화 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* 파비콘은 필터에 거치지 않고 바로 들어오게 해줌 */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .mvcMatchers(IGNORE_FAVICON)
//                전체 서비스 filterChain 안거치고 접근
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /* 거쳐야할 필터들을 설정 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                마이페이지 권한 설정
                .antMatchers(MYPAGE_MEMBER_PATH).hasRole(Role.MEMBER.name())
                .antMatchers(MYPAGE_WELFARE_PATH).hasRole(Role.WELFARE.name())
                .antMatchers(MYPAGE_DISTRIBUTOR_PATH).hasRole(Role.DISTRIBUTOR.name())

//                  작성페이지 권한 설정
                .antMatchers(MEMBER_WRITE_PATH).hasRole(Role.MEMBER.name())
                .antMatchers(WELFARE_WRITE_PATH).hasRole(Role.WELFARE.name())
                .antMatchers(DISTRIBUTOR_WRITE_PATH).hasRole(Role.DISTRIBUTOR.name())

//                관리자 페이지 권한 설정
                .antMatchers(ADMIN_PATH).hasRole(Role.ADMIN.name())
                .anyRequest().permitAll()

//                기타 설정
                .and()
                .csrf().disable()
                .exceptionHandling()
                /* 인가, 인증 Exception Handler */
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE)
                .usernameParameter("userId")
                .passwordParameter("userPassword")
                .loginProcessingUrl(MEMBER_LOGIN_PROCESSING_URL)
                .failureUrl("/login?error")
                .successHandler(authenticationSuccessHandler)
//                .failureHandler(authenticationFailureHandler)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
                .logoutSuccessUrl(LOGOUT_SUCCESS_URL)
                .invalidateHttpSession(Boolean.TRUE)
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .key(REMEMBER_ME_TOKEN_KEY)
                .tokenValiditySeconds(REMEMBER_ME_TOKEN_EXPIRED)
                .userDetailsService(userDetailsService)
                .authenticationSuccessHandler(authenticationSuccessHandler)
//                OAuth 로그인 관련
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(memberOAuthService);

        return http.build();
    }


}
