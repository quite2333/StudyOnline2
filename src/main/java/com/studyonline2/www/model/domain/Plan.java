package com.studyonline2.www.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.studyonline2.www.model.domain.request.PlanRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.model.domain
 * @ClassName=Plan
 * @Description:
 * @date 2024/10/9 16:39
 * @jdk version used: JDK1.8
 **/
@Data
@TableName(value = "studyplan")
@AllArgsConstructor
@NoArgsConstructor
public class Plan implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = -3972486792070729703L;
    @TableField(value = "id")
    private Integer userid;
    private String studyTarget;
    private String studyTime;
    private Integer relaxTimes;
    private Date createTime;

    public Plan(PlanRequest planRequest) {
        userid=planRequest.getUserid();
        studyTarget=planRequest.getStudyTarget();
        studyTime=planRequest.getStudyTime();
        relaxTimes=planRequest.getRelaxTimes();
        createTime= new Date();
    }
}
