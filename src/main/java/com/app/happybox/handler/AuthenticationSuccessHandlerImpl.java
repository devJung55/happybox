package com.app.happybox.handler;

import com.app.happybox.provider.UserDetail;
import com.app.happybox.type.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private static final String REDIRECT_URL = "/main/welfare";
    private static final String REDIRECT_URL_FOR_MEMBER = "/main/welfare";
    private static final String REDIRECT_URL_FOR_WELFARE = "/main/welfare";
    private static final String REDIRECT_URL_FOR_DISTRIBUTOR = "/main/product";
    private static final String REDIRECT_URL_FOR_ADMIN = "admin/member-list";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("-=======================================");
        if(((UserDetail)authentication.getPrincipal()).getUserRole().equals(Role.ADMIN)){
            log.info("ADMIN_SUCCESS");
            log.info(authentication.getPrincipal().toString());
            response.sendRedirect(REDIRECT_URL_FOR_ADMIN);
        }else if(((UserDetail)authentication.getPrincipal()).getUserRole().equals(Role.MEMBER)) {
            log.info("MEMBER_SUCCESS");
            log.info(authentication.getPrincipal().toString());
            response.sendRedirect(REDIRECT_URL_FOR_MEMBER);
        }else if(((UserDetail)authentication.getPrincipal()).getUserRole().equals(Role.WELFARE)){
            log.info("WELFARE_SUCCESS");
            log.info(authentication.getPrincipal().toString());
            response.sendRedirect(REDIRECT_URL_FOR_WELFARE);
        }else if(((UserDetail)authentication.getPrincipal()).getUserRole().equals(Role.DISTRIBUTOR)){
            log.info("DISTRIBUTOR_SUCCESS");
            log.info(authentication.getPrincipal().toString());
            response.sendRedirect(REDIRECT_URL_FOR_DISTRIBUTOR);
        }
    }

}
