package com.studyonline2.www.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.model.domain
 * @ClassName=User
 * @Description:
 * @date 2024/10/7 15:05
 * @jdk version used: JDK1.8
 **/
@TableName(value = "attribute")
@Data
public class User implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 4832310311066751993L;
    @TableId(type = IdType.AUTO)
    private int id;
    private String username;
    private String password;
    private String email;
    private String phonenumber;
    private String description;
    private String headImageUrl;
}
