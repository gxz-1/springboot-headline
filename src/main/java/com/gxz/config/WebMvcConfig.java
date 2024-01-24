package com.gxz.config;

import com.gxz.interceptor.TokenValidInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//springMvc配置，添加拦截器
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired //由于已经将拦截器加入ioc，因此这里直接注入
    private TokenValidInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器并配置拦截路径
        registry.addInterceptor(interceptor).addPathPatterns("/headline/**");
    }
}
