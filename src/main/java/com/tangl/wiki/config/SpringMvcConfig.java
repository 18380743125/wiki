package com.tangl.wiki.config;

//import com.tangl.wiki.interceptor.LogInterceptor;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tangl
 * @description
 * @create 2023-08-26 19:24
 */
@SpringBootConfiguration
public class SpringMvcConfig implements WebMvcConfigurer {
//    @Resource
//    private LogInterceptor logInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(logInterceptor)
//                .addPathPatterns("/**");
    }
}
