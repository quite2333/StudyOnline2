package com.studyonline2.www.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.model.domain.request
 * @ClassName=UserRegisterRequest
 * @Description:
 * @date 2024/10/7 14:25
 * @jdk version used: JDK1.8
 **/
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -8656108455757866676L;
    private String username;
    private String password;
    private String checkPassword;
    private String email;

}
