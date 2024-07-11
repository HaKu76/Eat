package com.lph.eat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    //设置静态资源映射
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // super.addResourceHandlers(registry);
        log.info("开始静态资源映射");
        registry.addResourceHandler("/static/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/static/front/**").addResourceLocations("classpath:/front/");
    }
}
