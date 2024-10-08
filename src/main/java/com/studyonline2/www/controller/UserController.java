package com.studyonline2.www.controller;

import com.studyonline2.www.dto.UserDTO;
import com.studyonline2.www.exception.BusinessException;
import com.studyonline2.www.model.domain.User;
import com.studyonline2.www.model.domain.request.UserLoginRequest;
import com.studyonline2.www.model.domain.request.UserRegisterRequest;
import com.studyonline2.www.service.UserService;
import com.studyonline2.www.utils.BaseResponse;
import com.studyonline2.www.utils.ErrorCode;
import com.studyonline2.www.utils.ResultUtils;
import com.studyonline2.www.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.controller
 * @ClassName=UserController
 * @Description:
 * @date 2024/10/7 14:19
 * @jdk version used: JDK1.8
 **/
@RestController
@ResponseBody
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * @param userRegisterRequest
     * @return
     */
    @PutMapping
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        //基础校验
        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String s = userRegisterRequest.toString();
        System.out.println(s);
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String email = userRegisterRequest.getEmail();
        Long l = userService.register(username,password,checkPassword,email);
        return ResultUtils.success(l);
    }

    /**
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/updatePassword")
    public BaseResponse<Boolean> userUpdatePassword(@RequestBody UserRegisterRequest userRegisterRequest){
        //基础校验
        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        Boolean ans = userService.updatePassword(username,password,checkPassword);
        return ResultUtils.success(ans);
    }

    /**
     * @param userLoginRequest
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<String> userLogin(@RequestBody UserLoginRequest userLoginRequest){
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        String token = userService.login(username,password);
        return ResultUtils.success(token);
    }

    /**
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);

        }
        String token = request.getHeader("authorization");
        Boolean ans = userService.userLogout(token);
        return ResultUtils.success(ans);
    }

    /**
     * @param username
     * @param request
     * @return
     */
    @GetMapping("/searchByname")
    public BaseResponse<List<UserDTO>> searchByname(String username, HttpServletRequest request){
        if (StringUtils.isBlank(username)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        System.out.println(username);
        List<User> users = userService.searchUsers(username);
        List<UserDTO> result = users.stream().map(UserDTO::getSafeUser).collect(Collectors.toList());
        return ResultUtils.success(result);
    }

    /**
     * @param userid
     * @param request
     * @return
     */
    @GetMapping("/searchById")
    public BaseResponse<UserDTO> searchById(String userid, HttpServletRequest request){
        if (StringUtils.isBlank(userid)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        System.out.println(userid);
        User user = userService.searchUser(userid);
        UserDTO safeUser = UserDTO.getSafeUser(user);
        return ResultUtils.success(safeUser);
    }

    /**
     * 功能暂未开放
     * @param params
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(@RequestBody HashMap params, HttpServletRequest request){
        String strId = (String) params.get("id");
        Integer id = Integer.parseInt(strId);
        if (id<=0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean ans = userService.delete(id);
        return ResultUtils.success(ans);
    }

    /**
     * @param userDTO
     * @param request
     * @return
     */
    @PostMapping("/changeInfo")
    public BaseResponse<Boolean> changeInfo(@RequestBody UserDTO userDTO, HttpServletRequest request){
        if (userDTO == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //        省略将头像照片上传至云服务获取云服务地址的过程
        Boolean ans = userService.changeInfo(userDTO);
        return ResultUtils.success(ans);
    }

    @GetMapping("/current")
    public BaseResponse<UserDTO> getCurrentUser(HttpServletRequest request){
        UserDTO user = UserHolder.getUser();
        return ResultUtils.success(user);
    }

}
