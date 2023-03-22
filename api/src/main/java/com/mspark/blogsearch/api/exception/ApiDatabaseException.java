package com.mspark.blogsearch.api.exception;

import com.mspark.blogsearch.api.enums.ResultCode;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 11:09
 */
public class ApiDatabaseException extends ApiException{

    private ResultCode resultCode;
    private String msg;

    public ApiDatabaseException(ResultCode resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return "[ApiDatabaseException > code : " + resultCode.getCode() + ", msg : " + msg +"]";
    }
}
