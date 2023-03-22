package com.mspark.blogsearch.external.exception;

import com.mspark.blogsearch.external.enums.ResultCode;
import lombok.AllArgsConstructor;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 22:52
 */
@AllArgsConstructor
public class AllExternalApiDownException extends ExternalException{

    private ResultCode resultCode;

    @Override
    public String getMessage() {
        return "[AllExternalApiDownException : code : " + resultCode.getCode() + " msg : " + resultCode.getDescription() + "]";
    }
}
