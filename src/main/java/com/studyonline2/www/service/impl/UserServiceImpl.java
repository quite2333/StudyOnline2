package com.studyonline2.www.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.studyonline2.www.dto.UserDTO;
import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.mapper.UserMapper;
import com.studyonline2.www.model.domain.User;
import com.studyonline2.www.service.UserService;
import com.studyonline2.www.utils.ErrorCode;
import com.studyonline2.www.utils.RedisConstant;
import com.studyonline2.www.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.service.impl
 * @ClassName=UserServiceImpl
 * @Description:
 * @date 2024/10/7 16:02
 * @jdk version used: JDK1.8
 **/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final String SALT = "quite";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Long register(String username, String password, String checkPassword, String email) {
        if (StringUtils.isAnyBlank(username, password, checkPassword, email)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (username.length() < 4 || password.length() < 6 || checkPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //数字、字母、下划线验证
        String validPattern = "[^\\w]";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //账户重复验证
        Long count = lambdaQuery().eq(User::getUsername, username).count();
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptPassword);
        user.setEmail(email);
        boolean ans = this.save(user);
        if (!ans) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return Long.valueOf(user.getId());
    }

    @Override
    public Boolean updatePassword(String username, String password, String checkPassword) {
        if (StringUtils.isAnyBlank(username,password,checkPassword)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (password.length() < 6 || checkPassword.length() < 6){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!password.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        boolean ans = lambdaUpdate()
                .set(User::getPassword, encryptPassword)
                .eq(User::getUsername, username)
                .update();

        return ans;
    }

    @Override
    public String login(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (username.length() < 4 || password.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //数字、字母、下划线验证
        String validPattern = "[^\\w]";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);

        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        User user = lambdaQuery()
                .eq(User::getUsername, username)
                .eq(User::getPassword, encryptPassword)
                .one();
        if (user == null) {
            log.info("wrong userAccount or userPassword");
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
//        生成token
        String token = UUID.randomUUID().toString();
        UserDTO userDTO = UserDTO.getSafeUser(user);
//        生成键值对
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(), false, true);
        Map<String, String> map = userMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().toString()));

//        记录用户到redis
        String tokenKey = RedisConstant.LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey,map);
        stringRedisTemplate.expire(tokenKey,RedisConstant.LOGIN_USER_TTL, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public List<User> searchUsers(String username) {
        List<User> list = lambdaQuery().like(User::getUsername, username).list();
        return list;
    }

    @Override
    public User searchUser(String userid) {
        User user = lambdaQuery().eq(User::getId, userid).one();
        return user;
    }

    @Override
    public Boolean delete(Integer id) {
        Boolean ans = this.delete(id);
        return ans;
    }

    @Override
    public Boolean changeInfo(UserDTO userDTO) {
        UserDTO user = UserHolder.getUser();
        int id = user.getId();
        String username = user.getUsername();

        String description = userDTO.getDescription();
        String email = userDTO.getEmail();
        String headImageUrl = userDTO.getHeadImageUrl();
        String phonenumber = userDTO.getPhonenumber();
        boolean ans = lambdaUpdate()
                .set(User::getDescription, description)
                .set(User::getEmail, email)
                .set(User::getHeadImageUrl, headImageUrl)
                .set(User::getPhonenumber, phonenumber)
                .eq(User::getId,id)
                .eq(User::getUsername,username)
                .update();
        return ans;
    }

    @Override
    public Boolean userLogout(String token) {
        String tokenKey = RedisConstant.LOGIN_USER_KEY + token;
        Boolean ans = stringRedisTemplate.delete(tokenKey);
        return ans;

    }
}
