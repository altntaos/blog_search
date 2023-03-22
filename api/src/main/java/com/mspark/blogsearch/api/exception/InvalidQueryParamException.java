package com.mspark.blogsearch.api.exception;

import com.mspark.blogsearch.api.enums.ResultCode;
import lombok.AllArgsConstructor;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 18:59
 */
@AllArgsConstructor
public class InvalidQueryParamException extends ApiException{

    private ResultCode resultCode;
    private String msg;

    @Override
    public String getMessage() {
        return "[InvalidQueryParamException > code : " + resultCode.getCode() + ", msg : " +msg +"]";
    }
}
