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
 * @PACKAGE_NAME=com.studyonline2.www.model.domain
 * @ClassName=Match
 * @Description:
 * @date 2024/10/11 11:42
 * @jdk version used: JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "matchlist")
public class Match implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = -8900206931435390113L;
    private Integer id;
    private Integer score;
    private Integer time;
    private Integer targetid;
}
