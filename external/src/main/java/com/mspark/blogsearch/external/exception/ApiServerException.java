package com.mspark.blogsearch.external.exception;

import com.mspark.blogsearch.external.enums.ResultCode;
import lombok.AllArgsConstructor;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 7:00
 */
@AllArgsConstructor
public class ApiServerException extends ExternalException{

    private ResultCode resultCode;

    @Override
    public String getMessage() {
        return "[ApiServerException > code : " + this.resultCode.getCode() +", msg : BlogSearch-Api server exception]";
    }
}
