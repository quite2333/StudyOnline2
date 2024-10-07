package com.studyonline2.www.dto;

import cn.hutool.core.bean.BeanUtil;
import com.studyonline2.www.model.domain.User;
import lombok.Data;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.dto
 * @ClassName=UserDTO
 * @Description:
 * @date 2024/10/7 15:43
 * @jdk version used: JDK1.8
 **/
@Data
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private String phonenumber;
    private String description;
    private String headImageUrl;

    /**
     * 信息过滤
     * @param user
     * @return
     */
    public static UserDTO getSafeUser(User user){
        if (user == null){
            return null;
        }
        UserDTO userDTO = BeanUtil.copyProperties(user,UserDTO.class);
        return userDTO;
    }
}
