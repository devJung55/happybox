package com.app.happybox.controller.exceptionHandler;

import com.app.happybox.exception.CustomAuthenticationException;
import com.app.happybox.exception.LoginFailedException;
import com.app.happybox.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    protected RedirectView handleLoginFailedException(ProductNotFoundException e){
        return new RedirectView("/product/list?failed=true");
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    protected RedirectView handleCustomAuthenticationException(CustomAuthenticationException e, HttpSession session){
        session.removeAttribute("id");
        session.removeAttribute("userId");
        session.removeAttribute("userRole");
        session.invalidate();
        return new RedirectView("/login");
    }

    @ExceptionHandler(LoginFailedException.class)
    protected RedirectView handleLoginFailedException(LoginFailedException e){
        return new RedirectView("/member/login?check=false");
    }



}
