package com.mspark.blogsearch.api.exception;

import com.mspark.blogsearch.api.enums.ResultCode;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 11:18
 */
public class ApiRedisException extends ApiException{

    private ResultCode resultCode;
    private String msg;
    private String key;

    public ApiRedisException(ResultCode resultCode, String msg, String key) {
        this.resultCode = resultCode;
        this.msg = msg;
        this.key = key;
    }

    @Override
    public String getMessage() {
        return "[ApiDatabaseException > code : " + resultCode.getCode()+", targetKey : " + key + ", msg : " + msg +"]";
    }
}
