package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Web MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        // 获取项目根目录
        String projectPath = System.getProperty("user.dir");
        File uploadPath = new File(projectPath, "upload");
        
        // 如果目录不存在，创建目录
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        
        // 配置上传文件访问路径
        // 访问路径: /upload/filename.jpg
        // 实际路径: {projectPath}/upload/filename.jpg
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadPath.getAbsolutePath() + "/")
                .setCachePeriod(0);
        
        // 配置前端静态资源
        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/front/")
                .setCachePeriod(3600);
        
        // 配置管理后台静态资源
        registry.addResourceHandler("/admin/**")
                .addResourceLocations("classpath:/admin/")
                .setCachePeriod(3600);
        
        // 配置其他静态资源
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
    }
    
    /**
     * 配置默认视图
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }
}
