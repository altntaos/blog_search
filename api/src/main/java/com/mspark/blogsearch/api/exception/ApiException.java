package com.mspark.blogsearch.api.exception;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 19:54
 */

import lombok.Getter;

@Getter
abstract class ApiException extends RuntimeException{

    abstract public String getMessage();
}
