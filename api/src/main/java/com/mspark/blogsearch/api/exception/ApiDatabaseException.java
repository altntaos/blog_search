package com.mspark.blogsearch.api.exception;

import com.mspark.blogsearch.api.enums.ResultCode;
import lombok.AllArgsConstructor;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 11:09
 */
@AllArgsConstructor
public class ApiDatabaseException extends ApiException{

    private ResultCode resultCode;
    private String msg;

    @Override
    public String getMessage() {
        return "[ApiDatabaseException > code : " + resultCode.getCode() + ", msg : " + msg +"]";
    }
}
