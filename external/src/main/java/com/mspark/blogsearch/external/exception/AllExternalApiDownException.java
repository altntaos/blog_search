package com.mspark.blogsearch.external.exception;

import com.mspark.blogsearch.external.enums.ResultCode;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 22:52
 */
public class AllExternalApiDownException extends ExternalException{

    private ResultCode resultCode;

    public AllExternalApiDownException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String getMessage() {
        return "[AllExternalApiDownException : code : " + resultCode.getCode() + " msg : " + resultCode.getDescription() + "]";
    }
}
