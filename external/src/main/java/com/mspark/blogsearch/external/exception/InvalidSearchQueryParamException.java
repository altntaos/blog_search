package com.mspark.blogsearch.external.exception;

import com.mspark.blogsearch.external.enums.ResultCode;

/**

 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 22:37
 */
public class InvalidSearchQueryParamException extends ExternalException{

    private ResultCode resultCode;
    private String paramName;
    private String msg;

    public InvalidSearchQueryParamException(ResultCode resultCode, String paramName, String msg) {
        this.resultCode = resultCode;
        this.paramName = paramName;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return "[InvalidSearchQueryParamException : code > " + this.resultCode.getCode() +", paramName : " + this.paramName + ", msg : " + this.msg;
    }
}
