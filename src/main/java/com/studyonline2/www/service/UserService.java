package com.studyonline2.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyonline2.www.dto.UserDTO;
import com.studyonline2.www.model.domain.User;
import com.studyonline2.www.model.domain.request.UserRegisterRequest;

import java.util.List;

public interface UserService extends IService<User> {

    Long register(String userName, String password, String checkPassword, String email);

    Boolean updatePassword(String userAccount, String password, String checkPassword);

    String login(String userName, String password);

    List<User> searchUsers(String username);

    User searchUser(String userid);

    Boolean delete(Integer id);

    Boolean changeInfo(UserDTO userDTO);

    Boolean userLogout(String token);
}
