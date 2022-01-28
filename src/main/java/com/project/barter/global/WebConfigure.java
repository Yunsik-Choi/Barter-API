package com.project.barter.global;

import com.project.barter.global.interceptor.BoardAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BoardAuthenticationInterceptor())
                .addPathPatterns("/board/**");
    }

}
