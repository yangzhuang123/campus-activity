package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.interceptor.AuthorizationInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport{
	
	@Bean
    public AuthorizationInterceptor getAuthorizationInterceptor() {
        return new AuthorizationInterceptor();
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 只拦截API请求，不拦截静态资源
        registry.addInterceptor(getAuthorizationInterceptor())
                .addPathPatterns("/api/**", "/shezhang/**", "/xuesheng/**", "/users/**", "/file/**")
                .excludePathPatterns("/static/**", "/admin/**", "/front/**", "/upload/**");
        super.addInterceptors(registry);
	}
	
	/**
	 * springboot 2.0配置WebMvcConfigurationSupport之后，会导致默认配置被覆盖，要访问静态资源需要重写addResourceHandlers方法
	 */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/admin/**")
        .addResourceLocations("classpath:/admin/");
        registry.addResourceHandler("/front/**")
        .addResourceLocations("classpath:/front/front/");
        registry.addResourceHandler("/upload/**")
        .addResourceLocations("classpath:/static/upload/");
		super.addResourceHandlers(registry);
    }
}
