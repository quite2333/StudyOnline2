package com.studyonline2.www.exception;

import com.studyonline2.www.utils.ErrorCode;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.exception
 * @ClassName=BusinessException
 * @Description:
 * @date 2024/10/7 19:07
 * @jdk version used: JDK1.8
 **/
public class BusinessException extends RuntimeException{
    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }
    public BusinessException(ErrorCode errorCode,String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
