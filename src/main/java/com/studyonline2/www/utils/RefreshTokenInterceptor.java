package com.studyonline2.www.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.studyonline2.www.dto.UserDTO;
import com.studyonline2.www.model.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.utils
 * @ClassName=RefreshTokenInterceptor
 * @Description:
 * @date 2024/10/7 19:40
 * @jdk version used: JDK1.8
 **/
public class RefreshTokenInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        if (StringUtils.isBlank(token)) {
            return true;
        }

        String tokenKey = RedisConstant.LOGIN_USER_KEY + token;
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(tokenKey);
        UserDTO userDTO = BeanUtil.fillBeanWithMap(entries, new UserDTO(), CopyOptions.create().setIgnoreNullValue(true));
//        存入线程变量
        UserHolder.saveUser(userDTO);
//        更新令牌
        stringRedisTemplate.expire(tokenKey, RedisConstant.LOGIN_USER_TTL, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserHolder.removeUser();
    }
}
