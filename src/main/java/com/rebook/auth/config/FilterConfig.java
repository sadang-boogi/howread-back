//package com.rebook.auth.config;
//
//import com.rebook.jwt.filter.JwtTokenFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//    @Bean
//    public FilterRegistrationBean<JwtTokenFilter> jwtFilterRegistrationBean(JwtTokenFilter jwtFilter) {
//        FilterRegistrationBean<JwtTokenFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(jwtFilter);
//        registrationBean.addUrlPatterns("/api/v1/*"); // 필터를 적용할 URL 패턴 설정
//        registrationBean.setOrder(1); // 필터의 우선순위
//        return registrationBean;
//    }
//}
