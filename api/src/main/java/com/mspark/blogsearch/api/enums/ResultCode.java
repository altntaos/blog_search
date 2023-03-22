package com.mspark.blogsearch.api.enums;

import lombok.Getter;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 19:55
 */
@Getter
public enum ResultCode {

    OK(1000, "OK"),API_ERROR (2000, "API_ERROR"), INVALID_PARAM(3000, "INVALID_PARAM");

    private int code;
    private String description;

    ResultCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
