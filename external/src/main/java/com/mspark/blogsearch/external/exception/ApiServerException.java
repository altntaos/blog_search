package com.mspark.blogsearch.external.exception;

import com.mspark.blogsearch.external.enums.ResultCode;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 7:00
 */
public class ApiServerException extends ExternalException{

    private ResultCode resultCode;

    public ApiServerException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String getMessage() {
        return "[ApiServerException > code : " + this.resultCode.getCode() +", msg : BlogSearch-Api server exception]";
    }
}
