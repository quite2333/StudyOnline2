package com.studyonline2.www.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.model.domain.request
 * @ClassName=UserLoginRequest
 * @Description:
 * @date 2024/10/7 15:14
 * @jdk version used: JDK1.8
 **/
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 4700486503646866536L;
    private String username;
    private String password;
}
