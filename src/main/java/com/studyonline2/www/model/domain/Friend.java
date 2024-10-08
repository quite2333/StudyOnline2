package com.studyonline2.www.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.model
 * @ClassName=Friend
 * @Description:
 * @date 2024/10/8 16:08
 * @jdk version used: JDK1.8
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "apply")
public class Friend implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = -1116097605026565054L;
    private Integer id1;
    private Integer id2;
    private Integer flag;
}
