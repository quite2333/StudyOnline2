package com.studyonline2.www.utils;

import com.studyonline2.www.dto.UserDTO;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.utils
 * @ClassName=UserHolder
 * @Description:
 * @date 2024/10/7 15:51
 * @jdk version used: JDK1.8
 **/
public class UserHolder {
    public static ThreadLocal<UserDTO> threadLocal;
    private UserHolder(){}

    public static ThreadLocal<UserDTO> getThreadLocal() {
        if (threadLocal==null){
            synchronized (UserHolder.class){
                if (threadLocal==null){
                    threadLocal = new ThreadLocal<>();
                }
            }
        }
        return threadLocal;
    }
    public static void saveUser(UserDTO userDTO){
        ThreadLocal<UserDTO> threadLocal = UserHolder.getThreadLocal();
        threadLocal.set(userDTO);
    }

    public static UserDTO getUser() {
        ThreadLocal<UserDTO> threadLocal = UserHolder.getThreadLocal();
        UserDTO userDTO = threadLocal.get();
        return userDTO;
    }
    public static void removeUser(){
        ThreadLocal<UserDTO> threadLocal = UserHolder.getThreadLocal();
        threadLocal.remove();
    }
}
