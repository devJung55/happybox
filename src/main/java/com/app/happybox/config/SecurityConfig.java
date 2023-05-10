package com.app.happybox.config;

import com.app.happybox.entity.type.Role;
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
    private static final String DISTRIBUTOR_PATH = "/distributors/**";
    private static final String BOARD_PATH = "/boards/**";
    private static final String CS_PATH = "/CS/**";
    private static final String MYPAGE_PATH = "/mypage/**";
    private static final String MYPAGE_MEMBER_PATH = "/mypage/member/**";
    private static final String MYPAGE_WELFARE_PATH = "/mypage/welfare/**";
    private static final String MYPAGE_DISTRIBUTOR_PATH = "/mypage/distributor/**";

//    작성 페이지 경로
    private static final String WRITE_PATH = "/**/write";
    private static final String WELFARE_WRITE_PATH = "/**/welfare/**/write";
    private static final String MEMBER_WRITE_PATH = "/**/MEMBER/**/write";
    private static final String DISTRIBUTOR_WRITE_PATH = "/**/DISTRIBUTOR/**/write";


//    favicon Ignore 경로
    private static final String IGNORE_FAVICON = "/favicon.png";
//    로그인 FORM 경로
    private static final String MEMBER_LOGIN_PAGE = "/members/login";
    private static final String WELFARE_LOGIN_PAGE = "/welfares/login";
    private static final String DISTRIBUTOR_LOGIN_PAGE = "/distributors/login";
//    로그인 ACTION 경로
    private static final String MEMBER_LOGIN_PROCESSING_URL = "/members/login";
    private static final String WELFARE_LOGIN_PROCESSING_URL = "/welfares/login";
    private static final String DISTRIBUTOR_LOGIN_PROCESSING_URL = "/distributors/login";
//    로그아웃 경로
    private static final String MEMBER_LOGOUT_URL = "/members/logout";
    private static final String WELFARE_LOGOUT_URL = "/wefares/logout";
    private static final String DISTRIBUTOR_LOGOUT_URL = "/distributors/logout";
//    로그아웃 성공시 Main으로 이동
    private static final String LOGOUT_SUCCESS_URL = "/main";

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

//    ============================ 필터 체인 설정부  ========================================

    /* 비밀번호 암호화 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* 파비콘은 필터에 거치지 않고 바로 들어오게 해줌 */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .mvcMatchers(IGNORE_FAVICON)
                .antMatchers("/img/**", "/js/**", "/css/**")
                /* 전체 서비스는 모든 회원이 다 접근은 할 수 있음. 작성 페이지는 권한 마다 제한 */
                .antMatchers(MAIN_PATH)
                .antMatchers(MEMBER_PATH)
                .antMatchers(WELFARE_PATH)
                .antMatchers(DISTRIBUTOR_PATH)
                .antMatchers(BOARD_PATH)
                .antMatchers(CS_PATH)
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /* 거쳐야할 필터들을 설정 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeRequests()
//                마이페이지 권한 설정
                .antMatchers(MYPAGE_MEMBER_PATH).hasRole(Role.MEMBER.name())
                .antMatchers(MYPAGE_WELFARE_PATH).hasRole(Role.WELFARE.name())
                .antMatchers(MYPAGE_DISTRIBUTOR_PATH).hasRole(Role.DISTRIBUTOR.name())
                .antMatchers(MYPAGE_PATH).authenticated()
//                관리자 페이지 권한 설정
                .antMatchers(ADMIN_PATH).hasRole(Role.ADMIN.name())
                .antMatchers(ADMIN_PATH).authenticated()
                .and()
                .csrf().disable()
                .exceptionHandling()
                /* 인가, 인증 Exception Handler */
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);

                return http.build();


    }






}
