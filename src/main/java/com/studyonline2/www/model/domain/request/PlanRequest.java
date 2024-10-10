package com.studyonline2.www.model.domain.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.model.domain.request
 * @ClassName=PlanRequest
 * @Description:
 * @date 2024/10/9 16:55
 * @jdk version used: JDK1.8
 **/
@Data
public class PlanRequest implements Serializable {
    private static final long serialVersionUID = -2476683429405132287L;
    private Integer userid;
    private String studyTarget;
    private String studyTime;
    private Integer relaxTimes;
}
