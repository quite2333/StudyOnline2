package com.studyonline2.www.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.model.domain
 * @ClassName=Message
 * @Description:
 * @date 2024/10/9 18:33
 * @jdk version used: JDK1.8
 **/
@Data
@TableName(value = "chat")
public class Message implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 2776088674908192784L;
    @TableId(type = IdType.AUTO)
    Integer msgid;
    Date createtime;
    @TableField(value = "fromuserid")
    Integer fromUserid;
    @TableField(value = "touserid")
    Integer toUserid;
    String content;
    @TableField(value = "unread")
    Integer unreadFlag;
}
