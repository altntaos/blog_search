package com.mspark.blogsearch.external.enums;

import lombok.Getter;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 23:17
 */
@Getter
public enum ResultCode {

    OK(1000, "OK"), EXTERNAL_API_ERROR(2000, "EXTERNAL_API_ERROR"),
    API_ERROR(3000, "API_ERROR"), INVALID_PARAM(4000, "INVALID_PARAMETER");

    private int code;
    private String description;

    ResultCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
