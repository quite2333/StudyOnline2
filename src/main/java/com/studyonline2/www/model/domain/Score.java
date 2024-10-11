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
 * @ClassName=Score
 * @Description:
 * @date 2024/10/11 12:04
 * @jdk version used: JDK1.8
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "score")
public class Score implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 7110658314223716223L;
    private Integer id;
    private Integer score;
}
