//package com.rebook.auth.aspect;
//
//import com.rebook.common.exception.BaseException;
//import jakarta.servlet.http.HttpServletRequest;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import java.util.Objects;
//
//import static com.rebook.common.exception.ExceptionCode.LOGIN_REQUIRED;
//
//@Aspect
//@Component
//public class LoginAspect {
//
//    //로그인이 필요한 메소드에 적용
//    @Before("@annotation(com.rebook.auth.annotation.LoginRequired)")
//    public void checkLoginForMethod(JoinPoint joinPoint) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        if (request.getAttribute("loggedInUser") == null) {
//            //추후에 error가 아닌 로그인 페이지로 리다이렉트 구현 예정
//            throw new BaseException(LOGIN_REQUIRED);
//        }
//    }
//
//    //로그인이 필요한 컨트롤러 전체에 적용
//    @Before("within(@com.rebook.auth.annotation.LoginRequiredForController *)")
//    public void checkLoginForController(JoinPoint joinPoint) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        if (request.getAttribute("loggedInUser") == null) {
//            // 추후에 error가 아닌 로그인 페이지로 리다이렉트 구현 예정
//            throw new BaseException(LOGIN_REQUIRED);
//        }
//    }
//
//}
