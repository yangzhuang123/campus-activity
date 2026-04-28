package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.interceptor.AuthorizationInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Bean
    public AuthorizationInterceptor getAuthorizationInterceptor() {
        return new AuthorizationInterceptor();
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 只拦截API请求，不拦截静态资源
        registry.addInterceptor(getAuthorizationInterceptor())
                .addPathPatterns("/api/**", "/shezhang/**", "/xuesheng/**", "/users/**", "/file/**", "/discuss/**", "/tag/**", "/shetuanhuodong/**")
                .excludePathPatterns("/static/**", "/admin/**", "/front/**", "/upload/**");
	}
}
