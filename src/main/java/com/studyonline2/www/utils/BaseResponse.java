package com.studyonline2.www.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.utils
 * @ClassName=BaseResponse
 * @Description:
 * @date 2024/10/7 19:02
 * @jdk version used: JDK1.8
 **/
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -5333796063531453110L;
    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }
}
