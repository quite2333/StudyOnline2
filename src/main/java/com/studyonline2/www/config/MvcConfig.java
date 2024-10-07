package com.studyonline2.www.config;

import com.studyonline2.www.utils.LoginInterceptor;
import com.studyonline2.www.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.config
 * @ClassName=MvcConfig
 * @Description:
 * @date 2024/10/7 19:39
 * @jdk version used: JDK1.8
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns("/user","/user/login").order(1);
    }
}
