package org.tinkerhub.offgo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tinkerhub.offgo.handlerinterceptor.loginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new loginInterceptor()).addPathPatterns("/user/**");
    }

}
