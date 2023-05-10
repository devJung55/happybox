package com.app.happybox.controller.exceptionHandler;

import com.app.happybox.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    protected RedirectView handleLoginFailedException(ProductNotFoundException e){
        return new RedirectView("/product/list?failed=true");
    }
}
