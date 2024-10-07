package com.studyonline2.www.utils;

/**
 * @author quite2333
 * @version v1.0
 * @PACKAGE_NAME=com.studyonline2.www.utils
 * @ClassName=ResultUtils
 * @Description:
 * @date 2024/10/7 19:03
 * @jdk version used: JDK1.8
 **/
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }
    public static <T> BaseResponse<T> error(int code, String message, String description){
        return new BaseResponse<>(code,null,message,description);
    }
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String message, String description){
        return new BaseResponse<>(errorCode.getCode(),null,message,description);
    }
    public static <T> BaseResponse<T> error(ErrorCode errorCode,  String description){
        return new BaseResponse<>(errorCode.getCode(),null,errorCode.getMessage(),description);
    }
    public static <T> BaseResponse<T> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }
}
